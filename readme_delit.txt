
 /**
  * https://stackoverflow.com/questions/48811192/reading-excel-file-with-scala
 */

  /*
  import org.apache.poi.ss.usermodel.{ DataFormatter, WorkbookFactory, Row }
  import java.io.File
  import scala.jdk.CollectionConverters._

  import org.apache.poi.ss.usermodel.{WorkbookFactory, DataFormatter}
  import org.apache.poi.ss.usermodel.Cell
  import org.apache.poi.ss.usermodel.Row
  import org.apache.poi.ss.usermodel.{WorkbookFactory, DataFormatter}
  */

    /*
    val headerRow = rowIterator.next()
    while(rowIterator.hasNext){
      val row :Row = rowIterator.next()
      val cellIterator = row.cellIterator()
      while(cellIterator.hasNext) {
        val cell = cellIterator.next()
        cell.getCellTypeEnum match {
          case CellType.STRING => {
            print(cell.getStringCellValue + "\t")
          }
          case CellType.NUMERIC => {
            print(cell.getNumericCellValue + "\t")
          }
          case CellType.BLANK => {
            print("null" + "\t")
          }
          case _ => throw new RuntimeException(" this error occured when reading ")
        }
      }
      println("")
    }
    */