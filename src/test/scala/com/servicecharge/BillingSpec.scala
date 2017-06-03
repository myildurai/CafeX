package com.servicecharge

import com.servicecharge.menu.{ItemType, Menu}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Myilvahanan on 03/06/2017.
  */
class BillingSpec extends FlatSpec with Matchers{

  import ItemType._

  /*    Item type testing    */
  "ItemType (Hot or Cold) enum " should " parse known types " in {
    ItemType("Hot") should be (Hot)
    ItemType("Cold") should be (Cold)
  }

  it should " reject unknown item types " in {
    intercept[NoSuchElementException] {
      ItemType("")
    }
    intercept[NullPointerException] {
      ItemType(null)
    }
    intercept[NoSuchElementException] {
      ItemType("Warm")
    }
  }

  /*    Menu Testing    */
  "Given Menu " must "have only the 4 expected items (Coffee/Cola/Cheese Sandwich/Steak Sanwich) " in {
    Menu.items.size should be (4)

    Menu.items.contains(Menu.COFFEE) should be (true)
    Menu.items.contains(Menu.COLA) should be (true)
    Menu.items.contains(Menu.CHEESE_SANDWICH) should be (true)
    Menu.items.contains(Menu.STEAK_SANDWICH) should be (true)
    Menu.items.contains("Nandos Chicken") should be (false)
  }
  " Menu items " should " have expected type and cost " in {
    Menu.items.get(Menu.COFFEE).get.itemType should be (Hot)
    Menu.items.get(Menu.COFFEE).get.cost should be (BigDecimal(1.00))

    Menu.items.get(Menu.COLA).get.itemType should be (Cold)
    Menu.items.get(Menu.COLA).get.cost should be (BigDecimal(0.50))

    Menu.items.get(Menu.CHEESE_SANDWICH).get.itemType should be (Cold)
    Menu.items.get(Menu.CHEESE_SANDWICH).get.cost should be (BigDecimal(2.00))

    Menu.items.get(Menu.STEAK_SANDWICH).get.itemType should be (Hot)
    Menu.items.get(Menu.STEAK_SANDWICH).get.cost should be (BigDecimal(4.50))

  }

}
