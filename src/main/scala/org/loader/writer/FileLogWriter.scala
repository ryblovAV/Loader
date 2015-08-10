package org.loader.writer

import java.io.{FileWriter, BufferedWriter, File}

import org.loader.models.SubjectModel
import play.api.libs.json.{JsObject}

import java.util.{Map => JMap}

object FileLogWriter {

  val pathObjects = "logs/objects.json"

  def writeJSONToFile(path: String, json: JsObject) = {

    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(json.toString())
    bw.close()

  }

  def writeSubjects(subj: SubjectModel) = {

    LogWritter.subjToListMap(subj)

  }


}
