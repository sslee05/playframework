package controllers.formchapter

import play.api.mvc.ControllerComponents
import play.api.mvc.AbstractController
import play.api.i18n.I18nSupport
import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext
import play.api.mvc.MultipartFormData
import play.api.libs.Files
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.json.Json
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import java.nio.file.Paths
import java.nio.file.attribute.PosixFilePermissions
import play.api.libs.streams.Accumulator
import akka.stream.scaladsl.FileIO
import java.io.File
import java.nio.file.attribute.PosixFilePermission._
import java.nio.file.attribute.PosixFilePermissions
import java.nio.file.{Files => JFiles, Path, Paths}
import play.core.parsers.Multipart.FileInfo
import akka.util.ByteString
import akka.stream.IOResult

@Singleton
class FileController @Inject() (cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {

  def _upload = Action(parse.multipartFormData) { implicit request =>
    val mlFormData: MultipartFormData[Files.TemporaryFile] = request.body
    val filePartOption: Option[FilePart[Files.TemporaryFile]] = mlFormData.file("fileName")
    filePartOption.map { filePart =>
      filePart.ref.moveTo(new java.io.File("/Users/sslee/work/tempfiles/" + filePart.filename))
      Ok(Json toJson ("Retrieved file %s" format filePart.filename))
    }.getOrElse {
      BadRequest("File missing")
    }
  }

  def upload = Action(parse.multipartFormData) { implicit request =>
    request.body.file("fileName").map { partFile =>
      partFile.ref.moveTo(new java.io.File("/Users/sslee/work/tempfiles/" + partFile.filename), true)
      Ok(Json toJson ("Retrieved file %s" format partFile.filename))
    }.getOrElse {
      BadRequest("File misssing")
    }
  }

  def fileSampleView01 = Action {
    Ok(views.html.formchapter.filesample01())
  }
  
  
  import models.formchapter.PathUtil._
  def uploadWithForm = Action(parse.multipartFormData) { implicit request =>
    //ignored 부분에  request가 있기 때문에 Action method 안에 Form를 선언해야 한다.
    val form = Form(tuple(
      "description" -> nonEmptyText,
      "file" -> ignored(request.body.file("file")).verifying(
        "error.filemissing",
        partFileOption => partFileOption.map(partFile => if (partFile.ref.length() > 0) true else false) getOrElse false)))

    form.bindFromRequest().fold(
      formWithError => {
        Ok(views.html.formchapter.filesample02(formWithError))
      },
      value => {
        request.body.file("file").map { partFile => 
          val filename = Paths.get(partFile.filename).getFileName
          //partFile.ref.moveTo(getPath("/Users/sslee/work/tmp/picture/",filename), true).deleteOnExit()
          partFile.ref.moveTo(getPath("/Users/sslee/work/tmp/picture/",filename), true)
          Ok(Json toJson ("Retrieved file %s" format partFile.filename))
        }.getOrElse {
          BadRequest("File misssing")
        }
      })
  }

  def fileSampleView02 = Action { implicit request =>
    val dummyForm = Form(ignored("dummy"))
    Ok(views.html.formchapter.filesample02(dummyForm))
  }
  
  
  type FilePartHandler[A] = FileInfo => Accumulator[ByteString, FilePart[A]]

  def handleFilePartAsFile: FilePartHandler[File] = {
    case FileInfo(partName, filename, contentType) =>
      val perms = java.util.EnumSet.of(OWNER_READ, OWNER_WRITE)
      val attr = PosixFilePermissions.asFileAttribute(perms)
      //val path = JFiles.createTempFile(s"/Users/sslee/work/tmp/picture/$filename", ".IMG", attr)
      val targetPath = Paths.get("/Users/sslee/work/tmp/picture/"+filename)
      val path = JFiles.createFile(targetPath, attr)
      val file = path.toFile
      val fileSink = FileIO.toPath(path)
      val accumulator = Accumulator(fileSink)
      accumulator.map {
        case IOResult(count, status) =>
          FilePart(partName, filename, contentType, file)
      }(ec)
  }
  
  def _upload2 = Action(parse.multipartFormData(handleFilePartAsFile)) { implicit request =>
    request.body.file("file").map{
      case FilePart(key,filename,contentType,file) =>
        Ok(Json toJson ("Retrieved file %s" format filename) )
    } getOrElse BadRequest("File missing")
  }
  
  def _uploadWithForm = Action(parse.multipartFormData(handleFilePartAsFile)) {implicit request =>
    
    val form = Form(tuple(
      "description" -> nonEmptyText,
      "file" -> ignored(request.body.file("file")).verifying(
        "error.filemissing",
        partFileOption => partFileOption.map(partFile => if (partFile.ref.length() > 0) true else false) getOrElse false)))

    form.bindFromRequest().fold(
      formWithError => {
        Ok(views.html.formchapter.filesample02(formWithError))
      },
      value => {
        request.body.file("file").map {  
          case FilePart(key,filename,contentType,file) => 
            Ok(Json toJson ("Retrieved file %s" format filename) )
        }.getOrElse {
          BadRequest("File misssing")
        }
      })
  }
  
  def uploadFilesView = Action {
    Ok(views.html.formchapter.filesample03())
  }
  
  def uploadWithFileList = Action(parse.multipartFormData) { implicit request =>
    request.body.files.map(filePart => 
      filePart.ref.moveTo(new java.io.File("/Users/sslee/work/tempfiles/" + filePart.filename)))
    Ok(Json toJson "submited")
  }

}