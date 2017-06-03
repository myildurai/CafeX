package com.servicecharge.menu

/**
  * Created by Myilvahanan on 03/06/2017.
  */

case class Detail(itemType:ItemType.Value,val cost:BigDecimal)

object Menu {

  final val COFFEE = "Coffee"
  final val COLA = "Cola"
  final val CHEESE_SANDWICH = "Cheese Sandwich"
  final val STEAK_SANDWICH = "Steak Sandwich"

  val items:Map[String, Detail]=Map()

}
