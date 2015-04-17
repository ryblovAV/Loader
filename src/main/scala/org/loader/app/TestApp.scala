package org.loader.app

import org.loader.builders.general.{DateBuilder, KeysBuilder}
import org.loader.builders.gesk.LoaderG
import org.loader.out.gesk.objects.{Plat, Address, Potr}
import org.loader.out.gesk.reader.GeskReader

/**
 * Created by RyblovAV on 29.10.14.
 */
object TestApp extends App{
  LoaderG.removePer(KeysBuilder.getPerId)
}
