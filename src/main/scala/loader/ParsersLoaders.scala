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
    (fn.absName, fn.parentAbsName, WorkbookFactory.create(fn.fFile).getSheetAt(0).iterator.asScala)

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

  private def ParseFinProvisMonitor(fn :FileNode) :Seq[FinancialProvisionMonitor] =
    Function.tupled(rowsToSeqFinProvisMonitor _)(fnToParserParams(fn))

  private def ParseFinProvisVolume(fn :FileNode) :Seq[FinancialProvisionVolume] =
    Function.tupled(rowsToSeqFinProvisVolume _)(fnToParserParams(fn))

  private def ParseMethodCalc(fn :FileNode) :Seq[MethodCalc] =
    Function.tupled(rowsToSeqMethodCalc _)(fnToParserParams(fn))

  private def ParseMethodCalcCode(fn :FileNode) :Seq[MethodCalcCode] =
    Function.tupled(rowsToSeqMethodCalcCode _)(fnToParserParams(fn))

  private def ParseTasks(fn :FileNode) :Seq[Tasks] =
    Function.tupled(rowsToSeqTasks _)(fnToParserParams(fn))

  private def ParseGovernment(fn :FileNode) :Seq[Government] =
    Function.tupled(rowsToSeqGovernment _)(fnToParserParams(fn))

  private def ParseNPDict(fn :FileNode) :Seq[NPDict] =
    Function.tupled(rowsToSeqNPDict _)(fnToParserParams(fn))

  private def ParseAdditInfo(fn :FileNode) :Seq[AdditInfo] =
    Function.tupled(rowsToSeqAdditInfo _)(fnToParserParams(fn))

  private def ParseResults(fn :FileNode) :Seq[Results] =
    Function.tupled(rowsToSeqResults _)(fnToParserParams(fn))

  private def ParseAssessmentTaskIndic(fn :FileNode) :Seq[AssessmentTaskIndic] =
    Function.tupled(rowsToSeqAssessmentTaskIndic _)(fnToParserParams(fn))

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
    case class FileLoadMeta[T <: CommCCTrait](entityName :String,
                                              isEnabled :Int = 1,
                                              parseFunc: (FileNode) => Seq[T] ,
                                              saveIntoDbFunc: (Seq[T]) => Unit,
                                              additionFilter :Option[String] = None
                                             ){
     def load = {
       val tBegin = System.currentTimeMillis
       val foundFiles = files.filter(_.absName.contains(entityName))
         //.filter(fn => !fn.parentAbsName.contains("Цифровая экономика"))
         //.filter(fn => fn.parentAbsName.contains("Здравоохранение"))
         //.filter(fn => fn.parentAbsName.contains("Транспортная часть комплексного"))
         .filter(fn => fn.absName.contains(entityName))
         .filter(fn => additionFilter match {  //Additional filter - using if exists (not None)
           case Some(s) => fn.absName.contains(s)
           case None => true
         })

       log.info(s"$entityName :${foundFiles.size} files")
       val seqEntity :Seq[T] = foundFiles.flatMap(parseFunc)
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

    //todo: maybe add target table here
    val seqLoads :Seq[FileLoadMeta[_ <: CommCCTrait]] = Seq(
      FileLoadMeta[NPDict]("Национальные проекты.xlsx", 0, ParseNPDict, saveNPDict),
      FileLoadMeta[CoExecutor]("Соисполнители", 0, ParseCoex, saveCoExecutors),
      FileLoadMeta[InterestedFoiv]("Заинтересованные ФОИВ", 0, ParseInteresFoiv, saveInterestedFoiv),
      FileLoadMeta[TargetIndic]("Цели и показатели.xlsx", 0, ParseTargetIndic, saveTargetIndic),
      FileLoadMeta[TargetIndicCode]("Цели и показатели-Код", 0, ParseTargetIndicCode, saveTargetIndicCode),
      FileLoadMeta[ProjStruct]("Структура проекта.xlsx", 0, ParsePrjStruct, saveProjStruct),
      FileLoadMeta[Tasks]("Задачи.xlsx", 0, ParseTasks, saveTasks),
      FileLoadMeta[TaskCode]("Задачи-Код", 0, ParseTaskCode, saveTaskCode),
      //todo: ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      FileLoadMeta[Results]("Результаты", 1, ParseResults, saveResults, Some(").xlsx")),
      FileLoadMeta[FinancialProvision]("Финансовое обеспечение-ФБ", 0, ParseFinProvis, saveFinProvis),
      FileLoadMeta[FinancialProvisionMonitor]("Финансовое обеспечение-Мониторинг исполнения ФБ.xlsx", 0, ParseFinProvisMonitor, saveFinProvisMonitor),
      FileLoadMeta[FinancialProvisionVolume]("Финансовое обеспечение-Объем финансового обеспечения НП.xlsx", 0, ParseFinProvisVolume, saveFinProvisVolume),
      FileLoadMeta[AdditInfo]("Дополнительная информация.xlsx", 0, ParseAdditInfo, saveAdditInfo),
      FileLoadMeta[MethodCalc]("Методика расчета показателей.xlsx", 0, ParseMethodCalc, saveMethodCalc),
      FileLoadMeta[MethodCalcCode]("Методика расчета показателей-Код", 0, ParseMethodCalcCode, saveMethodCalcCode),
      FileLoadMeta[AssessmentTaskIndic]("Оценка обеспеченности целей и целевых показателей национального проекта.xlsx", 0, ParseAssessmentTaskIndic, saveAssessmentTaskIndic),
      FileLoadMeta[Government]("Орган управления.xlsx", 0, ParseGovernment, saveGovernment)
    )

      seqLoads.collect{
      case flm if flm.isEnabled == 1 => flm.load
    } //collect instead of filter + map
    log.info(s"~~~~~~~~~~~~~ End load all files. Duration ${System.currentTimeMillis - t1} ms. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  }

}
