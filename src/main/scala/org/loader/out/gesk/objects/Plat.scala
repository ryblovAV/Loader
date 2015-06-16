package org.loader.out.gesk.objects

case class Address(reg: Option[String],
                   ul: Option[String],
                   dom: Option[String],
                   kv: Option[String],
                   ind: Option[String],
                   inn: Option[String],
                   abv1: Option[String] = None,
                   abv2: Option[String] = None)

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
                potrList:List[Potr])
