package loader

import java.sql.Connection
import loader.CommonCCStructs._
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.slf4j.LoggerFactory
import DBSaver._
import scala.jdk.CollectionConverters._
import org.apache.poi.ss.usermodel.Row

class ParsersLoaders(files :Seq[FileNode], conn :Connection) {
  private val log = LoggerFactory.getLogger(getClass.getName)

  /**
   * This function using for eliminate boilerplate, it convert single parameter fn :FileNode
   * into Tuple3. Next function fnToParserParams using as input parameter in rowToXXX.
   * But RowToXXX need 3 input parameters.
   * For this purpose we use Function.tupled that change 3 input parameters into one parameter of type Tuple3.
   */
  def fnToParserParams(fn :FileNode) : (String, String, Iterator[Row]) =
    (fn.absName, fn.parentAbsName, WorkbookFactory.create(fn.fFile).getSheetAt(0).iterator().asScala.iterator)

  private def ParseCoex(fn :FileNode) :Seq[CoExecutor] =
    Function.tupled(rowsToSeqCoExecutor _)(fnToParserParams(fn))

  private def ParseInteresFoiv(fn :FileNode) :Seq[InterestedFoiv] =
    Function.tupled(rowsToSeqInteresFoiv _)(fnToParserParams(fn))

  private def ParseTargetIndic(fn :FileNode) :Seq[TargetIndic] =
    Function.tupled(rowsToSeqTargetIndic _)(fnToParserParams(fn))

  private def ParsePrjStruct(fn :FileNode) :Seq[ProjStruct] =
    Function.tupled(rowsToSeqProjStruct _)(fnToParserParams(fn))

  private def ParseTargetIndicCode(fn :FileNode) :Seq[TargetIndicCode] =
    Function.tupled(rowsToSeqTargetIndicCode _)(fnToParserParams(fn))

  private def ParseTaskCode(fn :FileNode) :Seq[TaskCode] =
    Function.tupled(rowsToSeqTaskCode _)(fnToParserParams(fn))

  private def ParseFinProvis(fn :FileNode) :Seq[FinancialProvision] =
    Function.tupled(rowsToSeqFinProvis _)(fnToParserParams(fn))
  /*
  {
    log.info(s"PARENT : ${fn.parentAbsName}  file =  ${fn.fFile}")
    Function.tupled(rowsToSeqFinProvis _)(fnToParserParams(fn))
  }
*/

  def parseLoadAll = {
    val t1 = System.currentTimeMillis
    log.info("~~~~~~~~~~~~~ Begin load all files ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    implicit val dbConn :Connection = conn

    /**
     * Class describe meta for each source file structure.
     * entityName is a file name part that unique identify this file from all files.
     * isEnabled: 0,1 is this entity use in loading (mainly for debug purpose)
     * parseFunc - function that read one file found by pattern (entityName)
     * (in code files.filter(fn => fn.absName.contains(entityName))
     * and for each file iterate it by rows and cells. Convert row in CaseClass instances.
     * saveIntoDbFunc - function that using for save Seq of [Case class] into DB.
     * For parseFunc and saveIntoDbFunc it's better to look internal realization.
     *
     * Also there is "upper type bounds" used to restrict parameter data type T <: CommCCTrait
     * and for using common methods like this: seqEntity.head.getFileName
     *
    */
    case class FileLoadMeta[T <: CommCCTrait](entityName :String, isEnabled :Int = 1,
                               parseFunc: (FileNode) => Seq[T] ,
                               saveIntoDbFunc: (Seq[T]) => Unit){
     def load = {
       val tBegin = System.currentTimeMillis
       val foundFilesCount = files.count(_.absName.contains(entityName))
       log.info(s"$entityName :$foundFilesCount files")
       val seqEntity :Seq[T] = files
         //.filter(fn => !fn.parentAbsName.contains("Цифровая экономика"))
         //.filter(fn => !fn.parentAbsName.contains("Культура"))
         //.filter(fn => fn.parentAbsName.contains("Транспортная часть комплексного"))
         .filter(fn => fn.absName.contains(entityName)).flatMap(parseFunc)
       try {
         saveIntoDbFunc(seqEntity)
       } catch {
         case ex: Throwable with CustomException =>
           log.error(s"Exception cause: ${ex.getCause} message: ${ex.getMessage}")
           throw ex
       }
       log.info(s"  $entityName. Dur: ${(System.currentTimeMillis - tBegin)} ms. rows = ${seqEntity.size}")
     }
    }

    val seqLoads :Seq[FileLoadMeta[_ <: CommCCTrait]] = Seq(
      FileLoadMeta[CoExecutor]("Соисполнители",0, ParseCoex, saveCoExecutors),
      FileLoadMeta[InterestedFoiv]("Заинтересованные ФОИВ",0, ParseInteresFoiv, saveInterestedFoiv),
      FileLoadMeta[TargetIndic]("Цели и показатели.xlsx",0, ParseTargetIndic, saveTargetIndic),
      FileLoadMeta[ProjStruct]("Структура проекта.xlsx",0, ParsePrjStruct, saveProjStruct),
      FileLoadMeta[TargetIndicCode]("Цели и показатели-Код",0, ParseTargetIndicCode, saveTargetIndicCode),
      FileLoadMeta[TaskCode]("Задачи-Код",0, ParseTaskCode, saveTaskCode),
      FileLoadMeta[FinancialProvision]("Финансовое обеспечение-ФБ",1, ParseFinProvis, saveFinProvis)
    )

    seqLoads.collect{
      case flm if flm.isEnabled == 1 => flm.load
    } //collect instead of filter + map
    log.info(s"~~~~~~~~~~~~~ End load all files. Duration ${System.currentTimeMillis - t1} ms. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  }

}
