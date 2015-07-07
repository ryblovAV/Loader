package org.loader.out.gesk.objects

case class Address(reg: Option[String],
                   ul: Option[String],
                   dom: Option[String],
                   kv: Option[String],
                   ind: Option[String],
                   inn: Option[String],
                   phone: Option[String],
                   abv1: Option[String] = None,
                   abv2: Option[String] = None)

case class Phone(tel1:Option[String],
                 tel2:Option[String],
                 tel3:Option[String])

case class Plat(idPlat: String,
                seq: Int,
                el_adr: Option[String],
                addressU: Address,
                addressF: Address,
                kontr: Option[String],
                kpp: Option[String],
                naimF: String,
                naimU: String,
                tF: String,
                data: Option[java.util.Date],
                ndLgek: String,
                ndGesk: Option[String],
                ko: String,
                kob: Option[String],
                kOkwed:Option[String],
                bankNb:Option[String],
                rspl:Option[String],
                oplataSum:List[Double] = List.empty[Double],
                oplataDat:List[java.util.Date] = List.empty[java.util.Date],
                phone: Phone,
                potrList:List[Potr]) {

  def addMailingAddrtoAcct = addressU == addressF
}
