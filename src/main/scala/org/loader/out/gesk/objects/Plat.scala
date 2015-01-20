package org.loader.out.gesk.objects

case class Address(region: String, city: String, street: String, house: String, room: String, postalCode: String)

case class Plat(id: String,
                email: String,
                addressJ: Address,
                contractNumber: String,
                inn: String,
                kpp: String,
                nameF: String,
                phoneF: String,
                data: java.util.Date,
                dtCancellation: java.util.Date,
                dtConclusion: java.util.Date,
                ndGesk: String)
