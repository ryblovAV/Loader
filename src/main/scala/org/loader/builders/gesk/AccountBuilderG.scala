package org.loader.builders.gesk

import grizzled.slf4j.Logging
import org.loader.builders.general.AccountBuilder.addAcctChar
import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.models.Characteristic
import org.loader.out.gesk.objects.Plat
import org.loader.pojo.acct.AcctEntity
import org.loader.pojo.acctper.AcctPerEntity
import org.loader.pojo.per.{PerEntity, PerAddrOvrdEntity}

import org.loader.builders.general.BuilderUtl._


object AccountBuilderG extends Logging {


  def addPerAddrOvrd(plat: Plat,
                     per:PerEntity,
                     acct: AcctEntity,
                     acctPer: AcctPerEntity) = {
    val perAddrOvrd = new PerAddrOvrdEntity
    perAddrOvrd.acct = acct
    perAddrOvrd.address1 = AddressBuilderG.buildAddress1(plat.addressF)
    perAddrOvrd.address2 = plat.addressF.dom
    perAddrOvrd.address3 = plat.addressF.ul
    perAddrOvrd.address4 = plat.addressF.kv
    perAddrOvrd.country = "RUS"
    per.perAddrOvrdEntitySet.add(perAddrOvrd)

    acctPer.billAddrSrceFlg = "ACOV"
  }

  def addMailingAddress(plat: Plat,
                        per:PerEntity,
                        acct: AcctEntity,
                        acctPer: AcctPerEntity) = {
    if (plat.addMailingAddrtoAcct) {
      acct.mailingPrem = PremiseBuilderG.buildPremise(
        address = plat.addressF)
      
      acctPer.billAddrSrceFlg = "PER"
    } else {
    addPerAddrOvrd(plat, per, acct, acctPer)
    }
  }

  def buildAcct(plat: Plat) = {

    val account = new AcctEntity(KeysBuilder.getEnvId)

    account.acctId = KeysBuilder.getAcctId
    account.billCycCd = "M-D1"
    account.custClCd = "COMM"
    account.accessGrpCd = "GESK_COMM"
    account.collClCd = "N/A"

    account.setupDt = plat.data match {
      case Some(data) => data
      case None => DateBuilder.getDefaultDt
    }
    account.currencyCd = "RUR"
    account.cisDivision = "GESK"

    //char
    plat.kontr match {
      case Some(kontr) => {
//        addAcctChar(account, Characteristic(charTypeCd = "NOM_DOG", adhocCharVal = kontr))
        addAcctChar(account, Characteristic(charTypeCd = "VID_DOG", charVal = "7", effDt = account.setupDt))
      }
      case None => plat.ndGesk match {
        case Some(ndGesk) => {
//          addAcctChar(account, Characteristic(charTypeCd = "NOM_DOG", adhocCharVal = ndGesk))
          addAcctChar(account, Characteristic(charTypeCd = "VID_DOG", charVal = "6", effDt = account.setupDt))
        }
        case None =>
      }
    }

    //TODO Расчетный счет (ГЭСК) (4 характеристики)
    //банк
    for (bank <- plat.rs.bank) {
      addAcctChar(account, Characteristic(charTypeCd = "BANK_G", adhocCharVal = bank, effDt = account.setupDt))
    }
    //расчетный счет
    for (rasSch <- plat.rs.rasSch) {
      addAcctChar(account, Characteristic(charTypeCd = "R/CH_G", adhocCharVal = rasSch, effDt = account.setupDt))
    }
    //бик
    for (bik <- plat.rs.bik) {
      addAcctChar(account, Characteristic(charTypeCd = "BIK_G", adhocCharVal = bik, effDt = account.setupDt))
    }
    //корреспондентский счет банка
    for (korSch <- plat.rs.korSch) {
      addAcctChar(account, Characteristic(charTypeCd = "KORSCH_G", adhocCharVal = korSch, effDt = account.setupDt))
    }

    //банк
    for (naimb <- plat.finance.naimb) {
      addAcctChar(account, Characteristic(charTypeCd = "BANK_K_L", adhocCharVal = naimb, effDt = account.setupDt))
    }
    //БИК
    for (nb <- plat.finance.nb) {
      addAcctChar(account, Characteristic(charTypeCd = "BIK_K_L", adhocCharVal = nb, effDt = account.setupDt))
    }
    //расчетный счет
    for (rspl <- plat.id.rspl) {
      addAcctChar(account, Characteristic(charTypeCd = "RSCH_K_L", adhocCharVal = rspl, effDt = account.setupDt))
    }
    //Корр.счет банка клиента
    for (rs <- plat.finance.rs) {
      addAcctChar(account, Characteristic(charTypeCd = "KOR_KL_L", adhocCharVal = rs, effDt = account.setupDt))
    }

    account
  }


}
