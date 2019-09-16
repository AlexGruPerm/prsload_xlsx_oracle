package loader

import java.sql.{Connection, DriverManager}
import java.io.File
import org.slf4j.LoggerFactory
import XLSXFilesTree._


object XLSXLoader extends App {
  val log = LoggerFactory.getLogger(getClass.getName)
  log.info("Start: XLSXLoader")

  val rootPathToFiles = "C:\\RP\\MKR2-2862\\Нац проекты"
  val driver     = "oracle.jdbc.driver.OracleDriver"
  val username   = "NP_DATA_SRC"
  val password   = "NP_DATA_SRC"
  val oracleUrl  = "10.127.24.11:1521/test"
  val url        = "jdbc:oracle:thin:"+username+"/"+password+"@//"+oracleUrl

  // there's probably a better way to do this
  var connection :Connection = null

  try {
    // make the connection
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    /*
    val listOfFiles = getFileTree(new File(rootPathToFiles))
    listOfFiles.foreach(fn => log.info(fn.getAbsolutePath))
    */
    val files :Seq[FileNode] = getFilesTree(new File(rootPathToFiles))
    log.info(s"Directory $rootPathToFiles contains ${files.size} files and directories.")
    log.info(s" Directories ${files.count(_.isFile == false)}")
    log.info(s" XLSX files ${files.count(_.isFile == true)}")
    //printTree(files)

    val parser = new ParsersLoaders(files,connection)
    parser.parseLoadAll

    /* keep it here just as select example.
    //todo: remove it.
    // create the statement, and run the select query
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("select user,to_char(trunc(sysdate),'dd.mm.yyyy') as ddate,1 as numval from dual")
    while ( resultSet.next() ) {
      val user = resultSet.getString("user")
      val ddate = resultSet.getString("ddate")
      val numval = resultSet.getString("numval")
      println("user = "+user+" ddate = "+ddate+" numval = "+numval)
    }
    */
  } catch {
    case e => e.printStackTrace
  }
  connection.close()
  log.info("End: XLSXLoader")
}