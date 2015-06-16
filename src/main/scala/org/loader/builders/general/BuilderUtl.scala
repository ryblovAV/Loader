package org.loader.builders.general

object BuilderUtl {

  implicit def optToStrWithDef(vOpt:Option[String]):String = vOpt.getOrElse(" ")

}
