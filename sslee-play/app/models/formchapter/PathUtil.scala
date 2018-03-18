package models.formchapter

import java.nio.file.Paths
import java.io.File
import java.nio.file.Path
import play.api.Logger

object PathUtil {
  
  def getPath(path: String, fileNamePath: Path): Path = {
    val file = new File(path)
    Logger.debug("@@@@@@@@=>"+file.exists()+":"+file)
    if(!file.exists()) {
      val r = file.mkdirs()
      Logger.debug("@@@@@@@@r=>"+r)
    }
    Paths.get(path + fileNamePath)
  }
  
}