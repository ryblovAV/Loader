package org.loader.builders.general

import scala.language.implicitConversions


object BuilderUtl {

  implicit def optToStrWithDef(vOpt:Option[String]):String = vOpt.getOrElse(" ")

  implicit def translateToString(v:Double):String = (if (v.round == v) v.round.toString else v.toString).replace(".",",")

}
