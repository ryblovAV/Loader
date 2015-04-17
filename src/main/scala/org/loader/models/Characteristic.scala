package org.loader.models

import java.util.Date

import org.loader.builders.general.DateBuilder

case class Characteristic(charTypeCd: String,
                charVal: String = " ",
                adhocCharVal: String = " ",
                effDt: Date  = DateBuilder.getDefaultDt) {

  def isEmpty = ((charVal == null) || (charVal == " ")) && ((adhocCharVal == null) || (adhocCharVal == " "))

}
