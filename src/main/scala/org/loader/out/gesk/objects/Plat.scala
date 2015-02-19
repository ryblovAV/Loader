package org.loader.out.gesk.objects

case class Address(region: String, city: String, street: String, house: String, room: String, postalCode: String, inn: String)

case class Plat(id: String,
                email: String,
                addressJ: Address,
                addressF: Address,
                contractNumber: String,
                inn: String,
                kpp: String,
                nameF: String,
                phoneF: String,
                dateConclusion: java.util.Date,
                agreementNumberLGEK: String,
                agreementNumberGESK: String)
