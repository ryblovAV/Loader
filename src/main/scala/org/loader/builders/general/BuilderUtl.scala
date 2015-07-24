package org.loader.builders.general

import scala.language.implicitConversions


object BuilderUtl {

  implicit def optToStrWithDef(vOpt:Option[String]):String = vOpt.getOrElse(" ")

}
