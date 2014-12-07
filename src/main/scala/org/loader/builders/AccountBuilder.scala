package org.loader.builders

import java.util.Date

import org.loader.db.utl.DBUtl
import org.loader.out.lesk.objects.Client
import org.loader.pojo.acct.{AcctKEntity, AcctCharEntity, AcctEntity}

import org.loader.builders.Utills.dateToStr

object AccountBuilder {

  val listClientsTypeContract = List(
    "80110002",
    "80110035",
    "80110057",
    "80110204",
    "81411239",
    "81411221",
    "81411258",
    "81411270",
    "81411275",
    "81411285",
    "81411307",
    "81411310",
    "81411311",
    "81411312",
    "81411313",
    "81411329",
    "81411335",
    "81411331",
    "81411338",
    "81411341",
    "81411342",
    "81411350",
    "81411362",
    "81411366",
    "81411363",
    "81411367",
    "81411377",
    "81411380",
    "81411383",
    "81411384",
    "81411385",
    "81411422",
    "81411389",
    "81411428",
    "81411451",
    "81411500",
    "81411504",
    "81411520",
    "81411530",
    "81411535",
    "81411558",
    "81411578",
    "81411607",
    "81411626",
    "81411659",
    "81411699",
    "81411758",
    "81411817",
    "81411825",
    "81411833",
    "81411837",
    "81411955",
    "81411920",
    "81411971",
    "81411973",
    "81411974",
    "81411976",
    "81412022",
    "81412051",
    "81412063",
    "81412089",
    "81412095",
    "81412096",
    "81412100",
    "81412131",
    "81412136",
    "81412287",
    "81412276",
    "81412289",
    "81412318",
    "81412333",
    "81412354",
    "81412356",
    "81412338",
    "81412361",
    "70055002",
    "70050758",
    "70055069",
    "70055182",
    "70055207",
    "90222454",
    "90222455",
    "70040937",
    "70044008",
    "70044028",
    "70044055",
    "80188141",
    "80120401",
    "80120414",
    "80120039",
    "80120261",
    "80155374",
    "80155405",
    "80155406",
    "80155407",
    "80155410",
    "80155414",
    "80133143",
    "90233378",
    "90210350",
    "90210391",
    "90255252",
    "90255269",
    "70013091",
    "70010517",
    "70010611",
    "70013573",
    "70014611",
    "70014655",
    "70010523",
    "80177345",
    "70033035",
    "80160237",
    "90240153")
  
  def defineSetupDt(client: Client) = {

    if (client.dateConclusion != null)
      client.dateConclusion
    else {
      if (client.getFirstDt != null)
        client.getFirstDt
      else
        Utills.getDefaultDt
    }
  }

  def defineTypeContract(client:Client) = {
    if (client.budget != null) {
      "7"
    } else {
      if (listClientsTypeContract.exists(_ == client.id)) {
        "2"
      } else {
        if (client.isTSO)
          "10"
        else
          null
      }
    }
  }

  def buildAcctChar(charTypeCd: String,
                    charVal: String = " ",
                    adhocCharVal: String = " ",
                    effDt: Date = Utills.getDefaultDt) = {

    val acctChar = new AcctCharEntity()

    acctChar.charTypeCd = charTypeCd
    acctChar.effdt = effDt
    acctChar.charVal = charVal
    acctChar.adhocCharVal = adhocCharVal

    acctChar
  }

  def buildAccountChar = {

  }


  def buildAccount(client: Client) = {

    val account = new AcctEntity(Utills.getEnvId)
    account.acctId = Utills.getAcctId
    account.setupDt = defineSetupDt(client)
    account.cisDivision = LeskConstants.cisDivision
    account.custClCd = LeskConstants.custClCd
    account.accessGrpCd = LeskConstants.accessGrpCd

    //Характеристики
    //номер договора
    account.acctCharEntitySet.add(buildAcctChar(
      charTypeCd = "NOM_DOG",
      adhocCharVal = client.contract))

    //дата расторжения
    account.acctCharEntitySet.add(buildAcctChar(
      charTypeCd = "DATARAST",
      adhocCharVal = client.dateCancelling))

    //Вид договора
    val typeContract = defineTypeContract(client)
    if (typeContract != null)
      account.acctCharEntitySet.add(buildAcctChar(
        charTypeCd = "VID_DOG",
        adhocCharVal = typeContract))

    account
  }
}
