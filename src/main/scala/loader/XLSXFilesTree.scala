package loader

import java.io.File

import org.slf4j.LoggerFactory

//case class FileNode(name :String,fullPath :String, isFile :Int, parentName : String, nOrder :Int)
case class FileNode(fullPath :String, isFile :Boolean, parentName :String, absName :String, parentAbsName :String, fFile :File)

object XLSXFilesTree {
  val log = LoggerFactory.getLogger(getClass.getName)

  private def getFileTree(f: File): LazyList[File] =
    f #:: (if (f.isDirectory) f.listFiles().to(LazyList).flatMap(getFileTree)
    else LazyList.empty)

  def getFilesTree(f: File) :Seq[FileNode] = {
    getFileTree(f).map(f => FileNode(
      f.getAbsolutePath, f.isFile, f.getParent, f.getName, f.getParentFile.getName, f
    ))
  }

  def printTree(seqFiles: Seq[FileNode]): Unit = {
    seqFiles.foreach { f =>
      if (f.isFile) {
        log.info("      " + f.fullPath + " > " + f.absName)
      } else {
        log.info(" ")
        log.info("--------------------------------------------------------")
        log.info(f.absName)
      }
    }
  }

}
