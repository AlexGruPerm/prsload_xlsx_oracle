package loader

import org.apache.poi.ss.usermodel.{Cell, CellType, Row}
import scala.jdk.CollectionConverters._

/**
 *
*/
sealed trait CommCCTrait {
  def getFileName :String
}

/**
 * Case class for data from file like "Z-X.Y. Соисполнители.xlsx"
*/
case class CoExecutor(filename :String, np_name:String, code :String, name :String, budget_stages_cycle :String)
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like "Z-X.Y. Заинтересованные ФОИВ.xlsx"
*/
case class InterestedFoiv(filename :String, np_name:String, id_project_version :String, code :String, name :String)
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like "X-Y. Цели и показатели.xlsx"
 */
case class TargetIndic(filename :String, np_name:String,
                       npp  :String,         // № п/п
                       code :String,         // Код
                       noDopInidc :String ,  // Отсутствуют доп.показатели
                       project_vers :String, // Версия проекта.Идентификатор
                       npp_useless :String,  // № п/п (не исп)
                       ti_type :String,       // Тип
                       indic_name :String, //Наименование показателя
                       measure :String, //Единица измерения
                       veha_indic :String, //Веха/показатель
                       base_value :String, //Базовое значение
                       date_calc :String, //Дата расчета
                       del_val_2018 :String,// DEL 2018
                       del_val_2019 :String,//
                       del_val_2020 :String,//
                       del_val_2021 :String,//
                       del_val_2022 :String,//
                       del_val_2023 :String,//
                       del_val_2024 :String,//
                       val_2018: String, //
                       val_2019: String, //
                       val_2020: String, //
                       val_2021: String, //
                       val_2022: String, //
                       val_2023: String, //
                       val_2024: String, //
                       //---------------
                       target_value: String, //Целевое значение
                       control_level: String, //Уровень контроля
                       predict_inidc_val_without_rel: String, // Прогнозное значение показателя без реализации проекта (программы)
                       description: String, //Описание
                       way_achiv_indic: String, // Ход достижения показателя
                       predict_achiv_date: String, // Оценка сроков достижения целевого значения показателя
                       ident: String, // Идентификатор
                       decree: String, //Указ
                       num: String, //Индекс номера
                       is_using_in_fp: String //Используется в ФП
) extends CommCCTrait {
  override def toString: String =
    "["+filename+"] "+" "+  npp +" "+  code +" "+  noDopInidc  +" "+ project_vers  +" "+  npp_useless +" "+  ti_type+
     /* " "+ indic_name +" "+ measure*/ " "+veha_indic+" ["+ base_value + "] "+  date_calc +" "+val_2018+" val_22="+val_2022+" "+
      ident+" - "+is_using_in_fp

    override def getFileName :String = filename
}

/**
 * Case class for data from file like "X-Y. Структура проекта.xlsx"
 */
case class ProjStruct(filename :String, np_name:String,
                      ident :String,
                      code  :String,
                      code_bk  :String,
                      s_name  :String,
                      curator :String,
                      supervisor :String,
                      stages :String,
                      begin_impl_date :String,
                      end_impl_date :String,
                      is_related_np_taks :String)
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like "X-Y. Цели и показатели-Код Z.xlsx"
 */
case class TargetIndicCode(filename :String, np_name:String,
                           ww_np: String,
                           measure: String, //Единица измерения
                           vtype: String,
                           val_2018: String,
                           val_2019: String,
                           val_2020: String,
                           val_2021: String,
                           val_2022: String,
                           val_2023: String,
                           val_2024: String,
                           targetIndicId: String)
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like "X-Y. Задачи-Код Z.xlsx"
 */
case class TaskCode(filename :String, np_name:String,
                    code :String,
                    s_name :String,
                    percent_impact :String,
                    fp_id :String,
                    targetIndicId :String,
                    taskId :String
                   )
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like "X-Y. Задачи.xlsx"
 */
case class Tasks(filename :String, np_name:String,
                 npp: String,
                 code: String,
                 prj_vers: String,
                 prj_name: String,
                 task_name: String,
                 respons: String,
                 impl_period: String,
                 fp_name: String,
                 decree: String,
                 code_name: String
                )
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like "X-Y. Орган управления.xlsx"
 */
case class Government(filename :String, np_name:String,
                      participant: String,
                      login: String,
                      position: String,
                      org1: String,
                      org2: String,
                      role_in_team: String,
                      group_of_roles: String,
                      basis: String,
                      period_partic_from: String,
                      period_partic_to: String,
                      prj_id: String,
                      user_id: String,
                      role_in_team_id: String,
                      role_in_team_prior: String,
                      role_in_team_syscode: String,
                      user_fio_posit: String,
                      id: String,
                      percent_partic: String
                     )
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like "Дополнительная информация.xlsx"
*/
case class AdditInfo(filename :String, np_name:String,
                     s_name :String,
                     npp :String,
                     section :String,
                     add_info :String
                    )
  extends CommCCTrait {
  override def getFileName :String = filename
}

case class Results(filename :String, np_name:String,
                   fp_name: String,
                   task_of_np: String,
                   code: String,
                   s_name: String,
                   result_type: String,
                   measure: String,
                   respons: String,
                   control_level: String,
                   value: String,
                   achiev_date: String,
                   task_vers_proj_id: String,
                   task_id: String,
                   is_money: String,
                   realize_regions: String,
                   without_fb: String)
  extends CommCCTrait {
  override def getFileName :String = filename
}

case class AssessmentTaskIndic(filename :String, np_name:String,
                               cell_num :Int,
                               top_header_name :String,
                               row_num :Int,
                               left_header_name :String,
                               cell_value :String
                              )
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like  X. Финансовое обеспечение-ФБ.xlsx
*/
case class FinancialProvision(filename :String, np_name:String,
                              fp_code :String,
                              fp_name :String,
                              kbk     :String,
                              project_np :String,
                              s_name :String,
                              grbs_code :String,
                              grbs_name :String,
                              natp_name :String,
                              vr_name :String,
                              cs_id :String,
                              //------------
                              budget_assign_2019 :String,
                              approved_lbo_2019 :String,
                              locked_elbo_2019 :String,
                              brought_pno_2019 :String,
                              pno_wconditions_2019 :String,
                              //------------
                              budget_assign_2020 :String,
                              approved_lbo_2020 :String,
                              locked_elbo_2020 :String,
                              brought_pno_2020 :String,
                              pno_wconditions_2020 :String,
                              //------------
                              budget_assign_2021 :String,
                              approved_lbo_2021 :String,
                              locked_elbo_2021 :String,
                              brought_pno_2021 :String,
                              pno_wconditions_2021 :String,
                              sbt_row_type :String)
  extends CommCCTrait {
  override def getFileName :String = filename
}


/**
 * Case class for data from file like  X. Методика расчета показателей.xlsx
 */
case class MethodCalc(filename :String, np_name:String,
                      code: String,
                      name_basic_indic: String,
                      measure: String,
                      methodcalc: String,
                      agr_level_1: String,
                      agr_level_2: String,
                      asses_period: String,
                      provision_date: String,
                      methodcalc_id: String,
                      descr: String)
  extends CommCCTrait {
  override def getFileName :String = filename
}

/**
 * Case class for data from file like  X. Методика расчета показателей-Код 3.xlsx
 */
case class MethodCalcCode(filename :String, np_name:String,
                          method: String,
                          method_code: String,
                          method_name: String,
                          measure: String,
                          data_source: String,
                          source_respons: String,
                          agr_level: String,
                          asses_period: String,
                          provision_date: String,
                          var_description: String
                         )
  extends CommCCTrait {
  override def getFileName :String = filename
}




object CommonCCStructs {

  private implicit def getCellasStr(c :Cell): String  =
    try {
      c.getCellTypeEnum match {
        case CellType.STRING => c.getStringCellValue
        case CellType.NUMERIC => c.getNumericCellValue.toString
        case CellType.BOOLEAN => " " //c.getBooleanCellValue.toString
        case CellType.BLANK => " " //null
      }
    } catch {
      case e: Throwable => throw new CustomException(msg = s"EXCEPTION [getCellasStr] getCellTypeEnum = " + c.getCellTypeEnum+" "+e.getMessage)
      case e :NullPointerException =>  throw new CustomException(msg = s"EXCEPTION [getCellasStr] getCellTypeEnum = " + c.getCellTypeEnum+" "+e.getMessage)
    }



  /**
   * Parse XLSX row of Data into InterestedFoiv
   */
  private def cellToTargetIndic(filename: String, np_name: String, row: Row): TargetIndic = {
    //println("getRowNum = "+row.getRowNum)
    //println("  base_value = "+" celltype="+row.getCell(9).getCellTypeEnum())
    //val cell_0 = row.getCell(0)
    /**
     *  Here we use implicit conversion from Cell Type of different CellType.X into String with  - getCellasStr
    */
    TargetIndic(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5), //Тип
      row.getCell(6),
      row.getCell(7),
      row.getCell(8),
      row.getCell(9), //Базовое значение/Значение
      row.getCell(10),
      row.getCell(11),
      row.getCell(12),
      row.getCell(13),
      row.getCell(14),
      row.getCell(15),
      row.getCell(16),
      row.getCell(17),
      row.getCell(18),
      row.getCell(19),
      row.getCell(20),
      row.getCell(21),
      row.getCell(22),
      row.getCell(23),
      row.getCell(24),
      row.getCell(25),//целевое значение
      row.getCell(26),
      row.getCell(27),
      row.getCell(28),
      row.getCell(29),
      row.getCell(30),
      row.getCell(31),
      row.getCell(32),
      row.getCell(33),
      row.getCell(34)
    )
  }

  private def cellToProjStruct(filename: String, np_name: String, row: Row): ProjStruct =
    ProjStruct(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5),
      row.getCell(6),
      row.getCell(7),
      row.getCell(8),
      row.getCell(9)
    )

  private def cellToTargetIndicCode(filename: String, np_name: String, row: Row): TargetIndicCode =
    TargetIndicCode(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5),
      row.getCell(6),
      row.getCell(7),
      row.getCell(8),
      row.getCell(9),
      row.getCell(10)
    )

  private def cellToTaskCode(filename: String, np_name: String, row: Row) :TaskCode =
    TaskCode(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5)
    )

  private def cellToFinProvis(filename: String, np_name: String, row: Row) :FinancialProvision = //{
    //try {
      FinancialProvision(
        filename, np_name,
        row.getCell(0),
        row.getCell(1),
        row.getCell(2),
        row.getCell(3),
        row.getCell(4),
        row.getCell(5),
        row.getCell(6),
        row.getCell(7),
        row.getCell(8),
        row.getCell(9), //Целевые статьи
        //2019
        row.getCell(10),
        row.getCell(11),
        row.getCell(12),
        row.getCell(13),
        row.getCell(14),
        //2020
        row.getCell(15),
        row.getCell(16),
        row.getCell(17),
        row.getCell(18),
        row.getCell(19),
        //2021
        row.getCell(20),
        row.getCell(21),
        row.getCell(22),
        row.getCell(23),
        row.getCell(24),
        //
        row.getCell(25) //Тип строки СБР
      )
    /*
    } catch {
      case e: Throwable => throw
        new CustomException(msg = s"EXCEPTION [cellToFinProvis] RowNum = ${row.getRowNum} c11=${row.getCell(11)} c12=${row.getCell(12)} c13=${row.getCell(13)}")
      case e: NullPointerException => throw new CustomException(msg = s"EXCEPTION [cellToFinProvis] getRowNum = " + row.getRowNum)
    }
  */



  /**
   * Parse XLSX row of Data into Seq of case class
   * Ex: getCell(0,MissingCellPolicy.RETURN_BLANK_AS_NULL)
  */
  private def cellsToCoExecutor(filename :String, np_name:String, row: Row): CoExecutor =
    CoExecutor(
      filename, np_name,
      row.getCell(0).getStringCellValue,
      row.getCell(1).getStringCellValue,
      row.getCell(2).getStringCellValue
    )

  /**
   * Parse XLSX row of Data into Seq of case class
   */
  private def cellsToInteresFoiv(filename :String, np_name :String, row: Row): InterestedFoiv =
      InterestedFoiv(
        filename,
        np_name,
        row.getCell(0).getStringCellValue,
        row.getCell(1).getStringCellValue,
        row.getCell(2).getStringCellValue
      )

  private def cellsToMethodCalc(filename: String, np_name: String, row: Row) :MethodCalc =
    MethodCalc(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5),
      row.getCell(6),
      row.getCell(7),
      row.getCell(8),
      row.getCell(9)
    )

  private def cellsToMethodCalcCode(filename: String, np_name: String, row: Row) :MethodCalcCode =
    MethodCalcCode(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5),
      row.getCell(6),
      row.getCell(7),
      row.getCell(8),
      row.getCell(9)
    )

  private def cellsToTasks(filename: String, np_name: String, row: Row) :Tasks =
    Tasks(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5),
      row.getCell(6),
      row.getCell(7),
      row.getCell(8),
      row.getCell(9)
    )

  private def cellsToGovernment(filename: String, np_name: String, row: Row) :Government =
    Government(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5),
      row.getCell(6),
      row.getCell(7),
      row.getCell(8),
      row.getCell(9),
      row.getCell(10),
      row.getCell(11),
      row.getCell(12),
      row.getCell(13),
      row.getCell(14),
      row.getCell(15),
      row.getCell(16),
      row.getCell(17)
    )

  private def cellsToAdditInfo(filename: String, np_name: String, row: Row) =
    AdditInfo(
      filename, np_name,
      row.getCell(0),
      row.getCell(1),
      row.getCell(2),
      row.getCell(3)
    )

  private def cellsToResults(filename: String, np_name: String, row: Row) =
    Results(
      filename, np_name,
      row.getCell(1),
      row.getCell(2),
      row.getCell(3),
      row.getCell(4),
      row.getCell(5),
      row.getCell(6),
      row.getCell(7),
      row.getCell(8),
      row.getCell(9),
      row.getCell(10),
      row.getCell(11),
      row.getCell(12),
      row.getCell(13),
      row.getCell(14),
      row.getCell(15)
    )

  /**
   * Move row pointer from top of File. To separate header rows from data rows.
   * Different files contains different rows count in header.
   */
  def movePointer(rowIter :Iterator[Row], moveRowCount :Int) :Iterator[Row] ={
    (1 to moveRowCount).foreach(_ => rowIter.next())
    rowIter
  }

  /**
   * Block of methods that get Iterator of Row and loop by rows with map, where used individual CellToXXX
   * transformers.
   * Parse XLSX rows of Data into Seq of case class.
   */
  def rowsToSeqCoExecutor(filename :String, np_name :String, rowIter :Iterator[Row]) :Seq[CoExecutor] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellsToCoExecutor(filename,np_name,r)).toSeq
  }

  def rowsToSeqInteresFoiv(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[InterestedFoiv] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellsToInteresFoiv(filename,np_name,r)).toSeq
  }

  def rowsToSeqTargetIndic(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[TargetIndic] = {
    movePointer(rowIter,2)
    rowIter.map(r => cellToTargetIndic(filename,np_name,r)).toSeq
  }

  def rowsToSeqProjStruct(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[ProjStruct] = {
    movePointer(rowIter,5)
    rowIter.map(r => cellToProjStruct(filename,np_name,r)).toSeq
  }

  def rowsToSeqTargetIndicCode(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[TargetIndicCode] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellToTargetIndicCode(filename,np_name,r)).toSeq
  }

  def rowsToSeqTaskCode(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[TaskCode] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellToTaskCode(filename,np_name,r)).toSeq
  }

  def rowsToSeqFinProvis(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[FinancialProvision] = {
    movePointer(rowIter,2)
    rowIter.map(r => cellToFinProvis(filename,np_name,r)).toSeq
  }

  def rowsToSeqMethodCalc(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[MethodCalc] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellsToMethodCalc(filename,np_name,r)).toSeq
  }

  def rowsToSeqMethodCalcCode(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[MethodCalcCode] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellsToMethodCalcCode(filename,np_name,r)).toSeq
  }

  def rowsToSeqTasks(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[Tasks] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellsToTasks(filename,np_name,r)).toSeq
  }

  def rowsToSeqGovernment(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[Government] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellsToGovernment(filename,np_name,r)).toSeq
  }

  def rowsToSeqAdditInfo(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[AdditInfo] = {
    movePointer(rowIter,4)
    rowIter.map(r => cellsToAdditInfo(filename,np_name,r)).toSeq
  }

  def rowsToSeqResults(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[Results] = {
    movePointer(rowIter,1)
    rowIter.map(r => cellsToResults(filename,np_name,r)).toSeq
  }

  /**
   * Special case, convert top (1 row) header into column,
   * because files "X-Y.Z. Оценка обеспеченности целей и целевых показателей национального проекта.xlsx"
   * have different top header structures, different columns count.
  */
  def rowsToSeqAssessmentTaskIndic(filename :String, np_name:String, rowIter :Iterator[Row]) :Seq[AssessmentTaskIndic] = {
    //Contains first row, first next() return first row
    val iterHeaderCells :List[(Cell,Int)] =  rowIter.next().cellIterator().asScala.iterator.zipWithIndex.toList

    //iteration by any rows, excluding header
    val seq :Seq[AssessmentTaskIndic] =
      rowIter.flatMap(currDataRow =>
        iterHeaderCells.map(currCell => AssessmentTaskIndic(filename, np_name, currCell._2, currCell._1,
          currDataRow.getRowNum, currDataRow.getCell(0), currDataRow.getCell(currCell._2))
        )).toSeq.filter(elm => (elm.left_header_name != "0.0" && elm.top_header_name != "0.0")) //todo: empty row,cell!

    seq
  }

}
