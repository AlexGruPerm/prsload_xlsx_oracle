package loader

import java.sql.{Connection, PreparedStatement}

/**
 * Class for saving data sets (Seq of Case Classes) into DB.
 * Each method has implicit parameter conn.
*/
object DBSaver {

   def saveCoExecutors(seq :Seq[CoExecutor])(implicit conn :Connection) ={
    val insertSql =
      """
        |insert into COEXECUTOR(filename,np_name,code,name,budget_stages_cycle)
        |values(?,?,?,?,?)"""
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString (1, r.filename)
        preparedStmt.setString (2, r.np_name)
        preparedStmt.setString (3, r.code)
        preparedStmt.setString (4, r.name)
        preparedStmt.setString (5, r.budget_stages_cycle)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

   def saveInterestedFoiv(seq :Seq[InterestedFoiv])(implicit conn :Connection) ={
    val insertSql =
      """
        |insert into interested_foiv(filename,np_name,id_project_version,code,name)
        |values(?,?,?,?,?)"""
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString (1, r.filename)
        preparedStmt.setString (2, r.np_name)
        preparedStmt.setString (3, r.id_project_version)
        preparedStmt.setString (4, r.code)
        preparedStmt.setString (5, r.name)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

   def saveTargetIndic(seq :Seq[TargetIndic])(implicit conn :Connection) ={
    val insertSql =
      """ insert into target_indics(FILENAME,NP_NAME,NPP,CODE,NODOPINIDC,PROJECT_VERS,NPP_USELESS,TI_TYPE,INDIC_NAME,MEASURE,VEHA_INDIC,BASE_VALUE,DATE_CALC,
        |DEL_VAL_2018,DEL_VAL_2019,DEL_VAL_2020,DEL_VAL_2021,DEL_VAL_2022,DEL_VAL_2023,DEL_VAL_2024,
        |VAL_2018,VAL_2019,VAL_2020,VAL_2021,VAL_2022,VAL_2023,VAL_2024,TARGET_VALUE,CONTROL_LEVEL,PREDICT_INIDC_VAL_WITHOUT_REL,
        |DESCRIPTION,WAY_ACHIV_INDIC,PREDICT_ACHIV_DATE,IDENT,DECREE,NUM,IS_USING_IN_FP)
        |values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) """.stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.npp)
        preparedStmt.setString(4, r.code)
        preparedStmt.setString(5, r.noDopInidc)
        preparedStmt.setString(6, r.project_vers)
        preparedStmt.setString(7, r.npp_useless)
        preparedStmt.setString(8, r.ti_type)
        preparedStmt.setString(9, r.indic_name)
        preparedStmt.setString(10, r.measure)
        preparedStmt.setString(11, r.veha_indic)
        preparedStmt.setString(12, r.base_value)
        preparedStmt.setString(13, r.date_calc)
        preparedStmt.setString(14, r.del_val_2018)
        preparedStmt.setString(15, r.del_val_2019)
        preparedStmt.setString(16, r.del_val_2020)
        preparedStmt.setString(17, r.del_val_2021)
        preparedStmt.setString(18, r.del_val_2022)
        preparedStmt.setString(19, r.del_val_2023)
        preparedStmt.setString(20, r.del_val_2024)
        preparedStmt.setString(21, r.val_2018)
        preparedStmt.setString(22, r.val_2019)
        preparedStmt.setString(23, r.val_2020)
        preparedStmt.setString(24, r.val_2021)
        preparedStmt.setString(25, r.val_2022)
        preparedStmt.setString(26, r.val_2023)
        preparedStmt.setString(27, r.val_2024)
        preparedStmt.setString(28, r.target_value)
        preparedStmt.setString(29, r.control_level)
        preparedStmt.setString(30, r.predict_inidc_val_without_rel)
        preparedStmt.setString(31, r.description)
        preparedStmt.setString(32, r.way_achiv_indic)
        preparedStmt.setString(33, r.predict_achiv_date)
        preparedStmt.setString(34, r.ident)
        preparedStmt.setString(35, r.decree)
        preparedStmt.setString(36, r.num)
        preparedStmt.setString(37, r.is_using_in_fp)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

   def saveProjStruct(seq :Seq[ProjStruct])(implicit conn :Connection) ={
    val insertSql =
      """ insert into proj_struct(filename,np_name,ident,code,code_bk,s_name,curator,supervisor,stages,begin_impl_date,end_impl_date,is_related_np_taks)
        |values(?,?,?,?,?,?,?,?,?,?,?,?) """
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString (1, r.filename)
        preparedStmt.setString (2, r.np_name)
        preparedStmt.setString (3, r.ident)
        preparedStmt.setString (4, r.code)
        preparedStmt.setString (5, r.code_bk)
        preparedStmt.setString (6, r.s_name)
        preparedStmt.setString (7, r.curator)
        preparedStmt.setString (8, r.supervisor)
        preparedStmt.setString (9, r.stages)
        preparedStmt.setString (10, r.begin_impl_date)
        preparedStmt.setString (11, r.end_impl_date)
        preparedStmt.setString (12, r.is_related_np_taks)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveTargetIndicCode(seq :Seq[TargetIndicCode])(implicit conn :Connection) ={
    val insertSql =
      """
        |insert into target_indics_code(filename,np_name,ww_np,measure,vtype,val_2018,val_2019, val_2020, val_2021,val_2022,val_2023, val_2024,targetIndicId)
        |values(?,?,?,?,?,?,?,?,?,?,?,?,?)"""
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString (1, r.filename)
        preparedStmt.setString (2, r.np_name)
        preparedStmt.setString (3,r.ww_np )
        preparedStmt.setString (4, r.measure)
        preparedStmt.setString (5, r.vtype)
        preparedStmt.setString (6, r.val_2018)
        preparedStmt.setString (7, r.val_2019)
        preparedStmt.setString (8, r.val_2020)
        preparedStmt.setString (9, r.val_2021)
        preparedStmt.setString (10, r.val_2022)
        preparedStmt.setString (11, r.val_2023)
        preparedStmt.setString (12, r.val_2024)
        preparedStmt.setString (13, r.targetIndicId)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveTaskCode(seq :Seq[TaskCode])(implicit conn :Connection) = {
    val insertSql =
      """
        |insert into task_code(filename,np_name,code,s_name,percent_impact,fp_id,targetIndicId,taskId)
        |values(?,?,?,?,?,?,?,?)"""
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString (1, r.filename)
        preparedStmt.setString (2, r.np_name)
        preparedStmt.setString (3,r.code )
        preparedStmt.setString (4, r.s_name)
        preparedStmt.setString (5, r.percent_impact)
        preparedStmt.setString (6, r.fp_id)
        preparedStmt.setString (7, r.targetIndicId)
        preparedStmt.setString (8, r.taskId)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveFinProvis(seq :Seq[FinancialProvision])(implicit conn :Connection) = {
    val insertSql =
      """
        |insert into financial_provision(FILENAME,NP_NAME,FP_CODE,FP_NAME,KBK,PROJECT_NP,S_NAME,GRBS_CODE,GRBS_NAME,
        |NATP_NAME,VR_NAME,CS_ID,
        |BUDGET_ASSIGN_2019,APPROVED_LBO_2019,LOCKED_ELBO_2019,BROUGHT_PNO_2019,PNO_WCONDITIONS_2019,
        |BUDGET_ASSIGN_2020,APPROVED_LBO_2020,LOCKED_ELBO_2020,BROUGHT_PNO_2020,PNO_WCONDITIONS_2020,
        |BUDGET_ASSIGN_2021,APPROVED_LBO_2021,LOCKED_ELBO_2021,BROUGHT_PNO_2021,PNO_WCONDITIONS_2021,
        |SBT_ROW_TYPE)
        |values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"""
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.fp_code)
        preparedStmt.setString(4, r.fp_name)
        preparedStmt.setString(5, r.kbk)
        preparedStmt.setString(6, r.project_np)
        preparedStmt.setString(7, r.s_name)
        preparedStmt.setString(8, r.grbs_code)
        preparedStmt.setString(9, r.grbs_name)
        preparedStmt.setString(10, r.natp_name)
        preparedStmt.setString(11, r.vr_name)
        preparedStmt.setString(12, r.cs_id)
        preparedStmt.setString(13, r.budget_assign_2019)
        preparedStmt.setString(14, r.approved_lbo_2019)
        preparedStmt.setString(15, r.locked_elbo_2019)
        preparedStmt.setString(16, r.brought_pno_2019)
        preparedStmt.setString(17, r.pno_wconditions_2019)
        preparedStmt.setString(18, r.budget_assign_2020)
        preparedStmt.setString(19, r.approved_lbo_2020)
        preparedStmt.setString(20, r.locked_elbo_2020)
        preparedStmt.setString(21, r.brought_pno_2020)
        preparedStmt.setString(22, r.pno_wconditions_2020)
        preparedStmt.setString(23, r.budget_assign_2021)
        preparedStmt.setString(24, r.approved_lbo_2021)
        preparedStmt.setString(25, r.locked_elbo_2021)
        preparedStmt.setString(26, r.brought_pno_2021)
        preparedStmt.setString(27, r.pno_wconditions_2021)
        preparedStmt.setString(28, r.sbt_row_type)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

}
