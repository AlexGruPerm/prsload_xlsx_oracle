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
        |insert into COEXECUTOR(filename,np_name,code,name,budget_stages_cycle,rn)
        |values(?,?,?,?,?,?)"""
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString (1, r.filename)
        preparedStmt.setString (2, r.np_name)
        preparedStmt.setString (3, r.code)
        preparedStmt.setString (4, r.name)
        preparedStmt.setString (5, r.budget_stages_cycle)
        preparedStmt.setInt(6,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

   def saveInterestedFoiv(seq :Seq[InterestedFoiv])(implicit conn :Connection) ={
    val insertSql =
      """
        |insert into interested_foiv(filename,np_name,id_project_version,code,name,rn)
        |values(?,?,?,?,?,?)"""
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString (1, r.filename)
        preparedStmt.setString (2, r.np_name)
        preparedStmt.setString (3, r.id_project_version)
        preparedStmt.setString (4, r.code)
        preparedStmt.setString (5, r.name)
        preparedStmt.setInt(6,r.RowNum)
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
        |DESCRIPTION,WAY_ACHIV_INDIC,PREDICT_ACHIV_DATE,IDENT,DECREE,NUM,IS_USING_IN_FP,rn)
        |values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) """.stripMargin

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
        preparedStmt.setInt(38,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

   def saveProjStruct(seq :Seq[ProjStruct])(implicit conn :Connection) ={
    val insertSql =
      """ insert into proj_struct(filename,np_name,ident,code,code_bk,s_name,curator,supervisor,stages,begin_impl_date,end_impl_date,is_related_np_taks,rn)
        |values(?,?,?,?,?,?,?,?,?,?,?,?,?) """
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
        preparedStmt.setInt(13,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveTargetIndicCode(seq :Seq[TargetIndicCode])(implicit conn :Connection) ={
    val insertSql =
      """
        |insert into target_indics_code(filename,np_name,ww_np,measure,vtype,val_2018,val_2019, val_2020, val_2021,val_2022,val_2023, val_2024,targetIndicId,rn)
        |values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"""
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
        preparedStmt.setInt(14,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveTaskCode(seq :Seq[TaskCode])(implicit conn :Connection) = {
    val insertSql =
      """
        |insert into task_code(filename,np_name,code,s_name,percent_impact,fp_id,targetIndicId,taskId,rn)
        |values(?,?,?,?,?,?,?,?,?)"""
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
        preparedStmt.setInt(9,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveFinProvisVolume(seq :Seq[FinancialProvisionVolume])(implicit conn :Connection) = {
    val insertSql = """ insert into financial_provision_volume(FILENAME,NP_NAME,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL,AM,AN,AO,AP,AQ,AR,ASCOL,RN)
                      |values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) """
      .stripMargin
    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)

    println("saveFinProvisVolume seq.size = "+seq.size)

    seq.foreach {
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.A)
        preparedStmt.setString(4, r.B)
        preparedStmt.setString(5, r.C)
        preparedStmt.setString(6, r.D)
        preparedStmt.setString(7, r.E)
        preparedStmt.setString(8, r.F)
        preparedStmt.setString(9, r.G)
        preparedStmt.setString(10, r.H)
        preparedStmt.setString(11, r.I)
        preparedStmt.setString(12, r.J)
        preparedStmt.setString(13, r.K)
        preparedStmt.setString(14, r.L)
        preparedStmt.setString(15, r.M)
        preparedStmt.setString(16, r.N)
        preparedStmt.setString(17, r.O)
        preparedStmt.setString(18, r.P)
        preparedStmt.setString(19, r.Q)
        preparedStmt.setString(20, r.R)
        preparedStmt.setString(21, r.S)
        preparedStmt.setString(22, r.T)
        preparedStmt.setString(23, r.U)
        preparedStmt.setString(24, r.V)
        preparedStmt.setString(25, r.W)
        preparedStmt.setString(26, r.X)
        preparedStmt.setString(27, r.Y)
        preparedStmt.setString(28, r.Z)
        preparedStmt.setString(29, r.AA)
        preparedStmt.setString(30, r.AB)
        preparedStmt.setString(31, r.AC)
        preparedStmt.setString(32, r.AD)
        preparedStmt.setString(33, r.AE)
        preparedStmt.setString(34, r.AF)
        preparedStmt.setString(35, r.AG)
        preparedStmt.setString(36, r.AH)
        preparedStmt.setString(37, r.AI)
        preparedStmt.setString(38, r.AJ)
        preparedStmt.setString(39, r.AK)
        preparedStmt.setString(40, r.AL)
        preparedStmt.setString(41, r.AM)
        preparedStmt.setString(42, r.AN)
        preparedStmt.setString(43, r.AO)
        preparedStmt.setString(44, r.AP)
        preparedStmt.setString(45, r.AQ)
        preparedStmt.setString(46, r.AR)
        preparedStmt.setString(47, r.ASCOL)
        preparedStmt.setInt(48, r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveFinProvisMonitor(seq :Seq[FinancialProvisionMonitor])(implicit conn :Connection) = {
    val insertSql = """ insert into financial_provision_monitor(FILENAME,NP_NAME,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL,AM,AN,
                      |AO,AP,AQ,AR,ASCOL,AT,AU,AV,AW,AX,AY,AZ,BA,BB,BC,BD,BE,BF,BG,BH,BI,BJ,BK,BL,BM,BN,BO,BP,BQ,BR,BS,BT,BU,BV,BW,BX,BYCOL,BZ,CA,CB,CC,CD,CE,RN)
                      |values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,
                      |?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) """.stripMargin
    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.A)
        preparedStmt.setString(4, r.B)
        preparedStmt.setString(5, r.C)
        preparedStmt.setString(6, r.D)
        preparedStmt.setString(7, r.E)
        preparedStmt.setString(8, r.F)
        preparedStmt.setString(9, r.G)
        preparedStmt.setString(10, r.H)
        preparedStmt.setString(11, r.I)
        preparedStmt.setString(12, r.J)
        preparedStmt.setString(13, r.K)
        preparedStmt.setString(14, r.L)
        preparedStmt.setString(15, r.M)
        preparedStmt.setString(16, r.N)
        preparedStmt.setString(17, r.O)
        preparedStmt.setString(18, r.P)
        preparedStmt.setString(19, r.Q)
        preparedStmt.setString(20, r.R)
        preparedStmt.setString(21, r.S)
        preparedStmt.setString(22, r.T)
        preparedStmt.setString(23, r.U)
        preparedStmt.setString(24, r.V)
        preparedStmt.setString(25, r.W)
        preparedStmt.setString(26, r.X)
        preparedStmt.setString(27, r.Y)
        preparedStmt.setString(28, r.Z)
        preparedStmt.setString(29, r.AA)
        preparedStmt.setString(30, r.AB)
        preparedStmt.setString(31, r.AC)
        preparedStmt.setString(32, r.AD)
        preparedStmt.setString(33, r.AE)
        preparedStmt.setString(34, r.AF)
        preparedStmt.setString(35, r.AG)
        preparedStmt.setString(36, r.AH)
        preparedStmt.setString(37, r.AI)
        preparedStmt.setString(38, r.AJ)
        preparedStmt.setString(39, r.AK)
        preparedStmt.setString(40, r.AL)
        preparedStmt.setString(41, r.AM)
        preparedStmt.setString(42, r.AN)
        preparedStmt.setString(43, r.AO)
        preparedStmt.setString(44, r.AP)
        preparedStmt.setString(45, r.AQ)
        preparedStmt.setString(46, r.AR)
        preparedStmt.setString(47, r.ASCOL)
        preparedStmt.setString(48, r.AT)
        preparedStmt.setString(49, r.AU)
        preparedStmt.setString(50, r.AV)
        preparedStmt.setString(51, r.AW)
        preparedStmt.setString(52, r.AX)
        preparedStmt.setString(53, r.AY)
        preparedStmt.setString(54, r.AZ)
        preparedStmt.setString(55, r.BA)
        preparedStmt.setString(56, r.BB)
        preparedStmt.setString(57, r.BC)
        preparedStmt.setString(58, r.BD)
        preparedStmt.setString(59, r.BE)
        preparedStmt.setString(60, r.BF)
        preparedStmt.setString(61, r.BG)
        preparedStmt.setString(62, r.BH)
        preparedStmt.setString(63, r.BI)
        preparedStmt.setString(64, r.BJ)
        preparedStmt.setString(65, r.BK)
        preparedStmt.setString(66, r.BL)
        preparedStmt.setString(67, r.BM)
        preparedStmt.setString(68, r.BN)
        preparedStmt.setString(69, r.BO)
        preparedStmt.setString(70, r.BP)
        preparedStmt.setString(71, r.BQ)
        preparedStmt.setString(72, r.BR)
        preparedStmt.setString(73, r.BS)
        preparedStmt.setString(74, r.BT)
        preparedStmt.setString(75, r.BU)
        preparedStmt.setString(76, r.BV)
        preparedStmt.setString(77, r.BW)
        preparedStmt.setString(78, r.BX)
        preparedStmt.setString(79, r.BYCOL)
        preparedStmt.setString(80, r.BZ)
        preparedStmt.setString(81, r.CA)
        preparedStmt.setString(82, r.CB)
        preparedStmt.setString(83, r.CC)
        preparedStmt.setString(84, r.CD)
        preparedStmt.setString(85, r.CE)
        preparedStmt.setInt(86, r.RowNum)

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
        |SBT_ROW_TYPE,rn)
        |values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"""
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
        preparedStmt.setInt(29,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }


  def saveMethodCalc(seq :Seq[MethodCalc])(implicit conn :Connection) = {
    val insertSql =
      """ insert into method_calc(filename,np_name,code,name_basic_indic,measure,methodcalc,agr_level_1,agr_level_2,asses_period,provision_date,methodcalc_id,descr,rn)
        | values(?,?,?,?,?,?,?,?,?,?,?,?,?) """
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.code)
        preparedStmt.setString(4, r.name_basic_indic)
        preparedStmt.setString(5, r.measure)
        preparedStmt.setString(6, r.methodcalc)
        preparedStmt.setString(7, r.agr_level_1)
        preparedStmt.setString(8, r.agr_level_2)
        preparedStmt.setString(9, r.asses_period)
        preparedStmt.setString(10, r.provision_date)
        preparedStmt.setString(11, r.methodcalc_id)
        preparedStmt.setString(12, r.descr)
        preparedStmt.setInt(13,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }


  def saveMethodCalcCode(seq :Seq[MethodCalcCode])(implicit conn :Connection) = {
    val insertSql =
      """ insert into method_calc_code(filename,np_name,method,method_code,method_name,measure,data_source,source_respons,agr_level,asses_period,provision_date,var_description,rn)
        | values(?,?,?,?,?,?,?,?,?,?,?,?,?) """
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.method)
        preparedStmt.setString(4, r.method_code)
        preparedStmt.setString(5, r.method_name)
        preparedStmt.setString(6, r.measure)
        preparedStmt.setString(7, r.data_source)
        preparedStmt.setString(8, r.source_respons)
        preparedStmt.setString(9, r.agr_level)
        preparedStmt.setString(10, r.asses_period)
        preparedStmt.setString(11, r.provision_date)
        preparedStmt.setString(12, r.var_description)
        preparedStmt.setInt(13,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveTasks(seq :Seq[Tasks])(implicit conn :Connection) = {
    val insertSql =
      """ insert into tasks(FILENAME,NP_NAME,NPP,CODE,PRJ_VERS,PRJ_NAME,TASK_NAME,RESPONS,IMPL_PERIOD,FP_NAME,DECREE,CODE_NAME,rn)
        | values(?,?,?,?,?,?,?,?,?,?,?,?,?) """
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.npp)
        preparedStmt.setString(4, r.code)
        preparedStmt.setString(5, r.prj_vers)
        preparedStmt.setString(6, r.prj_name)
        preparedStmt.setString(7, r.task_name)
        preparedStmt.setString(8, r.respons)
        preparedStmt.setString(9, r.impl_period)
        preparedStmt.setString(10, r.fp_name)
        preparedStmt.setString(11, r.decree)
        preparedStmt.setString(12, r.code_name)
        preparedStmt.setInt(13,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveGovernment(seq :Seq[Government])(implicit conn :Connection) = {
    val insertSql =
      """ insert into government(FILENAME,NP_NAME,PARTICIPANT,LOGIN,POSITION,ORG1,ORG2,ROLE_IN_TEAM,GROUP_OF_ROLES,BASIS,PERIOD_PARTIC_FROM,
        | PERIOD_PARTIC_TO,PRJ_ID,USER_ID,ROLE_IN_TEAM_ID,ROLE_IN_TEAM_PRIOR,ROLE_IN_TEAM_SYSCODE,USER_FIO_POSIT,ID,PERCENT_PARTIC,rn)
        | values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) """
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.participant)
        preparedStmt.setString(4, r.login)
        preparedStmt.setString(5, r.position)
        preparedStmt.setString(6, r.org1)
        preparedStmt.setString(7, r.org2)
        preparedStmt.setString(8, r.role_in_team)
        preparedStmt.setString(9, r.group_of_roles)
        preparedStmt.setString(10, r.basis)
        preparedStmt.setString(11, r.period_partic_from)
        preparedStmt.setString(12, r.period_partic_to)
        preparedStmt.setString(13, r.prj_id)
        preparedStmt.setString(14, r.user_id)
        preparedStmt.setString(15, r.role_in_team_id)
        preparedStmt.setString(16, r.role_in_team_prior)
        preparedStmt.setString(17, r.role_in_team_syscode)
        preparedStmt.setString(18, r.user_fio_posit)
        preparedStmt.setString(19, r.id)
        preparedStmt.setString(20, r.percent_partic)
        preparedStmt.setInt(21,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveResults(seq :Seq[Results])(implicit conn :Connection) = {
    val insertSql =
      """   insert into results(FILENAME,NP_NAME,FP_NAME,TASK_OF_NP,CODE,S_NAME,RESULT_TYPE,MEASURE,RESPONS,
        |   CONTROL_LEVEL,VALUE,ACHIEV_DATE,TASK_VERS_PROJ_ID,TASK_ID,IS_MONEY,REALIZE_REGIONS,WITHOUT_FB,rn)
        |  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) """
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.fp_name)
        preparedStmt.setString(4, r.task_of_np)
        preparedStmt.setString(5, r.code)
        preparedStmt.setString(6, r.s_name)
        preparedStmt.setString(7, r.result_type)
        preparedStmt.setString(8, r.measure)
        preparedStmt.setString(9, r.respons)
        preparedStmt.setString(10, r.control_level)
        preparedStmt.setString(11, r.value)
        preparedStmt.setString(12, r.achiev_date)
        preparedStmt.setString(13, r.task_vers_proj_id)
        preparedStmt.setString(14, r.task_id)
        preparedStmt.setString(15, r.is_money)
        preparedStmt.setString(16, r.realize_regions)
        preparedStmt.setString(17, r.without_fb)
        preparedStmt.setInt(18,r.RowNum)

        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveAdditInfo(seq :Seq[AdditInfo])(implicit conn :Connection) = {
    val insertSql =
      """   insert into  add_info(filename,np_name,s_name,npp,section,add_info,rn)
        |  values(?,?,?,?,?,?,?) """
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setString(3, r.s_name)
        preparedStmt.setString(4, r.npp)
        preparedStmt.setString(5, r.section)
        preparedStmt.setString(6, r.add_info)
        preparedStmt.setInt(7,r.RowNum)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }

  def saveAssessmentTaskIndic(seq :Seq[AssessmentTaskIndic])(implicit conn :Connection) = {
    val insertSql =
      """ insert into assessment_task_indic(filename,np_name,cell_num,top_header_name,row_num,left_header_name,cell_value)
        | values(?,?,?,?,?,?,?) """
        .stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)
    seq.foreach{
      r =>
        preparedStmt.setString(1, r.filename)
        preparedStmt.setString(2, r.np_name)
        preparedStmt.setInt(3,r.cell_num)
        preparedStmt.setString(4, r.top_header_name)
        preparedStmt.setInt(5,r.row_num)
        preparedStmt.setString(6, r.left_header_name)
        preparedStmt.setString(7, r.cell_value)
        preparedStmt.execute
    }
    preparedStmt.close()
    conn.commit()
  }


}
