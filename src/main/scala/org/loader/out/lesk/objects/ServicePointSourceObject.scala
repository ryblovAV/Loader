package org.loader.out.lesk.objects

import java.util.Date

import org.loader.builders.Utills
import org.loader.db.utl.DBUtl

case class ServicePointType(isLosses: Boolean, isConsumption: Boolean, isWithoutMeterRead: Boolean)

case class Substation(level1: String, level2: String, level3: String)

case class PrpotSourceObject(typeObject: Int, doprashod: String, effdt: Date)


case class ServicePointSourceObject(id: String,
                                    name: String,
                                    code: String,
                                    level1: String,
                                    level2: String,
                                    level3: String,
                                    postrOr: String,
                                    prpotList: List[PrpotSourceObject]) {

  def getType = ServicePointType(isLosses, isConsumption, isWithoutMeterRead)

  //TODO realize methods isClose, isLosses, isConsumption, isWithoutMeterRead
  def isClose = false

  def isLosses = false

  def isConsumption = false

  def isWithoutMeterRead = false

  //TODO realize method installDt, abolishDt
  def installDt = Utills.getDefaultDt

  def abolishDt: Date = null

  //TODO realize substation
  def substation: Substation = Substation(level1, level2, level3)


}
