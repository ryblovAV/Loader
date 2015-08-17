package org.loader.out.gesk.objects

case class Address(reg: Option[String],
                   ul: Option[String],
                   dom: Option[String],
                   kv: Option[String],
                   ind: Option[String],
                   inn: Option[String],
                   phone: Option[String],
                   abv1: Option[String] = None,
                   abv2: Option[String] = None) {

  def houseType = kv match {
    case Some(_) => "2"
    case _ => "10"
  }

}

case class Phone(tel1:Option[String],
                 tel2:Option[String],
                 tel3:Option[String])

case class Finance(nb:Option[String],
                   naimb:Option[String],
                   rs:Option[String],
                   saldo:Option[Double],
                   oplataSum:List[Double] = List.empty[Double],
                   oplataDat:List[java.util.Date] = List.empty[java.util.Date])

case class Rs(bank:Option[String],
              rasSch:Option[String],
              bik:Option[String],
              korSch:Option[String])

case class Name(naimF: String,
                naimU: String)

case class Id(rspl:Option[String],
              nLsh: Option[String])

case class Plat(idPlat: String,
                seq: Int,
                el_adr: Option[String],
                addressU: Address,
                addressF: Address,
                kontr: Option[String],
                kpp: Option[String],
                id: Id,
                name: Name,
                tF: String,
                data: Option[java.util.Date],
                ndLgek: String,
                ndGesk: Option[String],
                ko: String,
                kob: Option[String],
                kOkwed:Option[String],
                finance:Finance,
                rs:Rs,
                phone: Phone,
                potrList:List[Potr],
                perList:List[Per]) {

  def addMailingAddrtoAcct = addressU == addressF

}
