package org.loader.models

import java.util.Date

import org.loader.builders.Utils

case class Characteristic(charTypeCd: String,
                charVal: String = " ",
                adhocCharVal: String = " ",
                effDt: Date  = Utils.getDefaultDt) {

  def isEmpty = ((charVal == null) || (charVal == " ")) && ((adhocCharVal == null) || (adhocCharVal == " "))

}
