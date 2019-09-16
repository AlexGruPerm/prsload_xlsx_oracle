package loader

import java.sql.Connection

import loader.CommonCCStructs._
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.slf4j.LoggerFactory
import DBSaver._

import scala.jdk.CollectionConverters._

/**
 * https://stackoverflow.com/questions/48811192/reading-excel-file-with-scala
*/

class ParsersLoaders(files :Seq[FileNode], conn :Connection) {
  private val log = LoggerFactory.getLogger(getClass.getName)

  private def ParseCoex(fn :FileNode) :Seq[CoExecutor]=
    rowsToSeqCoExecutor(fn.absName,fn.parentAbsName,
      WorkbookFactory.create(fn.fFile).getSheetAt(0).iterator().asScala.iterator)

  private def ParseInteresFoiv(fn :FileNode) :Seq[InterestedFoiv]=
    rowsToSeqInteresFoiv(fn.absName,fn.parentAbsName,
      WorkbookFactory.create(fn.fFile).getSheetAt(0).iterator().asScala.iterator)

  private def ParseTargetIndic(fn :FileNode) :Seq[TargetIndic]=
    rowsToSeqTargetIndic(fn.absName,fn.parentAbsName,
      WorkbookFactory.create(fn.fFile).getSheetAt(0).iterator().asScala.iterator)


  private def ParsePrjStruct(fn :FileNode) :Seq[ProjStruct] =
    rowsToSeqProjStruct(fn.absName,fn.parentAbsName,
      WorkbookFactory.create(fn.fFile).getSheetAt(0).iterator().asScala.iterator)

  private def ParseTargetIndicCode(fn :FileNode) :Seq[TargetIndicCode] = {
    rowsToSeqTargetIndicCode(fn.absName,fn.parentAbsName,
      WorkbookFactory.create(fn.fFile).getSheetAt(0).iterator().asScala.iterator)
  }

  private def ParseTaskCode(fn :FileNode) :Seq[TaskCode] = {
    val workbook = WorkbookFactory.create(fn.fFile)
    //val rowsTotal :Int = workbook.getSheetAt(0).iterator().asScala.iterator.size
    val seq :Seq[TaskCode] = rowsToSeqTaskCode(fn.absName,fn.parentAbsName,
      workbook.getSheetAt(0).iterator().asScala.iterator)
    //seq.map(e => log.info(e.toString))
    seq
  }



  def parseLoadAll = {
    val t1 = System.currentTimeMillis
    log.info("~~~~~~~~~~~~~ Begin load all files ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    implicit val dbConn :Connection = conn

    /**
     * Class describe meta for each source file structure.
     * entityName is a file name part that unique identify this file from all files.
     * isEnabled: 0,1 is this entity use in loading (mainly for debug purpose)
     * parseFunc - function that read files found by pattern (entityName) and for each file iterate it by rows and cells.
     * saveIntoDbFunc - function that using for save Seq of [Case class] into DB.
     * For parseFunc and saveIntoDbFunc it's better to look internal realization.
     *
    */
    case class FileLoadMeta[T](entityName :String, isEnabled :Int = 1,
                               parseFunc: (FileNode) => Seq[T] ,
                               saveIntoDbFunc: (Seq[T]) => Unit){
     def load = {
       val tBegin = System.currentTimeMillis
       val foundFilesCount = files.count(_.absName.contains(entityName))
       log.info(s"$entityName :$foundFilesCount files")
       val seqEntity :Seq[T] = files.filter(fn => fn.absName.contains(entityName)).flatMap(parseFunc)
       saveIntoDbFunc(seqEntity)
       log.info(s"  $entityName. Dur: ${(System.currentTimeMillis - tBegin)} ms. rows = ${seqEntity.size}")
     }
    }

    val seqLoads :Seq[FileLoadMeta[_]] = Seq(
      FileLoadMeta[CoExecutor]("Соисполнители",1, ParseCoex, saveCoExecutors),
      FileLoadMeta[InterestedFoiv]("Заинтересованные ФОИВ",1, ParseInteresFoiv, saveInterestedFoiv),
      FileLoadMeta[TargetIndic]("Цели и показатели.xlsx",1, ParseTargetIndic, saveTargetIndic),
      FileLoadMeta[ProjStruct]("Структура проекта.xlsx",1, ParsePrjStruct, saveProjStruct),
      FileLoadMeta[TargetIndicCode]("Цели и показатели-Код",1, ParseTargetIndicCode, saveTargetIndicCode),
      FileLoadMeta[TaskCode]("Задачи-Код",1,ParseTaskCode, saveTaskCode)
      //,FileLoadMeta[FinancialProvision]("Финансовое обеспечение-ФБ",1,XXX)
    )

    seqLoads.map(_.load)

    val durMs = System.currentTimeMillis - t1
    log.info(s"~~~~~~~~~~~~~ End load all files. Duration $durMs ms. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  }

}
