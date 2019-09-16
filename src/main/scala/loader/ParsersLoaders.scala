package loader

import java.sql.Connection

import loader.CommonCCStructs._
import loader.DBSaver._
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.slf4j.LoggerFactory

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





  //todo: removit
  /*
    val interestedMeta :String = "Заинтересованные ФОИВ"
    log.info(s"Заинтересованные ФОИВ :${files.count(_.absName.contains(interestedMeta))} files")
    val t1IntFoiv = System.currentTimeMillis
    val seqAllInteresFoiv :Seq[InterestedFoiv] = files
      //.filter(fn => fn.parentAbsName.contains("Здравоохранение"))
      .filter(fn => fn.absName.contains(interestedMeta))
      .flatMap(ParseInteresFoiv)
    //seqAllCoEx.foreach(coex => log.info(coex.np_name))
    saveInterestedFoiv(seqAllInteresFoiv)
    log.info(s"Loaded ${seqAllInteresFoiv.size} rows")
    log.info(s"----------------- $interestedMeta. Duration ${(System.currentTimeMillis - t1IntFoiv)} ms.")
  */



  def parseLoadAll = {
    val t1 = System.currentTimeMillis
    log.info("~~~~~~~~~~~~~ Begin load all files ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    implicit val dbConn :Connection = conn

    //#1. Соисполнители
    val coexMeta :String = "Соисполнители"
    log.info(s"$coexMeta :${files.count(_.absName.contains(coexMeta))} files")
    val t1CoEx = System.currentTimeMillis
    val seqAllCoEx :Seq[CoExecutor] = files.filter(fn => fn.absName.contains(coexMeta)).flatMap(ParseCoex)
    DBSaver.saveCoExecutors(seqAllCoEx)
    log.info(s"--------- $coexMeta. Dur: ${(System.currentTimeMillis - t1CoEx)} ms. rows = ${seqAllCoEx.size}")


    //#2. Заинтересованные ФОИВ
    val interestedMeta :String = "Заинтересованные ФОИВ"
    log.info(s"$interestedMeta :${files.count(_.absName.contains(interestedMeta))} files")
    val t1IntFoiv = System.currentTimeMillis
    val seqAllInteresFoiv :Seq[InterestedFoiv] = files.filter(fn => fn.absName.contains(interestedMeta)).flatMap(ParseInteresFoiv)
    saveInterestedFoiv(seqAllInteresFoiv)
    log.info(s"--------- $interestedMeta. Dur: ${(System.currentTimeMillis - t1IntFoiv)} ms. rows = ${seqAllInteresFoiv.size} ")


    //#3. Цели и показатели.xlsx (Без пунктов или кодов, перед .xlsx)
    val targetIndicMeta :String = "Цели и показатели.xlsx"
    log.info(s"$targetIndicMeta :${files.count(_.absName.contains(targetIndicMeta))} files")
    val t1TarInd = System.currentTimeMillis
    val seqAllTargetIndic :Seq[TargetIndic] = files.filter(fn => fn.absName.contains(targetIndicMeta)).flatMap(ParseTargetIndic)
    saveTargetIndic(seqAllTargetIndic)
    log.info(s"--------- $targetIndicMeta. Dur: ${(System.currentTimeMillis - t1TarInd)} ms. rows = ${seqAllTargetIndic.size}")


    //#4. Структура проекта.xlsx
    val projStructMeta :String = "Структура проекта.xlsx"
    log.info(s"$projStructMeta :${files.count(_.absName.contains(projStructMeta))} files")
    val t1PrjStruct = System.currentTimeMillis
    val seqAllPrjStruct :Seq[ProjStruct] = files
      .filter(fn => fn.absName.contains(projStructMeta)).flatMap(ParsePrjStruct)
    saveProjStruct(seqAllPrjStruct)
    log.info(s"--------- $projStructMeta. Dur: ${(System.currentTimeMillis - t1PrjStruct)} ms. rows = ${seqAllPrjStruct.size}")


    //#5. X-Y. Цели и показатели-Код N.xlsx
    val targetIndicCodeMeta :String = "Цели и показатели-Код"
    log.info(s"$targetIndicCodeMeta :${files.count(_.absName.contains(targetIndicCodeMeta))} files")
    val t1targetIndicCode = System.currentTimeMillis
    val seqAllTargetIndicCode :Seq[TargetIndicCode] = files
      .filter(fn => fn.absName.contains(targetIndicCodeMeta)).flatMap(ParseTargetIndicCode)
    saveTargetIndicCode(seqAllTargetIndicCode)
    log.info(s"--------- $targetIndicCodeMeta. Dur: ${(System.currentTimeMillis - t1targetIndicCode)} ms. rows = ${seqAllTargetIndicCode.size}")


    //#6. X-Y. Задачи-Код Z.xlsx
    val taskCodeMeta :String = "Задачи-Код"
    log.info(s"$taskCodeMeta :${files.count(_.absName.contains(taskCodeMeta))} files")
    val t1taskCode = System.currentTimeMillis
    val seqAllTaskCode :Seq[TaskCode] = files
      .filter(fn => fn.absName.contains(taskCodeMeta)).flatMap(ParseTaskCode)
    saveTaskCode(seqAllTaskCode)
    log.info(s"--------- $taskCodeMeta. Dur: ${(System.currentTimeMillis - t1taskCode)} ms. rows = ${seqAllTaskCode.size}")


    //#7. X. Финансовое обеспечение-ФБ.xlsx
    val FinProvisionMeta :String = "Финансовое обеспечение-ФБ"



    val durMs = System.currentTimeMillis - t1
    log.info(s"~~~~~~~~~~~~~ End load all files. Duration $durMs ms. ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  }

}
