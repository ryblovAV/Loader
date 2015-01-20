package org.loader.models

import java.util.Date

import org.loader.builders.Utills

case class Characteristic(charTypeCd: String,
                charVal: String = " ",
                adhocCharVal: String = " ",
                effDt: Date  = Utills.getDefaultDt) {

  def isEmpty = ((charVal == null) || (charVal == " ")) && ((adhocCharVal == null) || (adhocCharVal == " "))

}
