package com.servicecharge

import com.servicecharge.billing.{BasicBilling, ServiceChargeBilling}
import com.servicecharge.menu.{ItemType, Menu}
import com.servicecharge.shopping.Shopping
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
  "A shopping list " should " calculate the price without service charge correctly " in {
    val shopping = new Shopping(BasicBilling)

    val shoppingList1=List(Menu.COLA, Menu.COFFEE, Menu.CHEESE_SANDWICH)
    shopping.calculateBill(shoppingList1) should be (BigDecimal(3.50))

    val shoppingList2=List(Menu.COLA, Menu.COFFEE, Menu.CHEESE_SANDWICH, Menu.STEAK_SANDWICH)
    shopping.calculateBill(shoppingList2) should be (BigDecimal(8.00))

  }
  "A shopping list with only drinks " should " calculate the price with no service charge correctly " in {
    val shopping = new Shopping(ServiceChargeBilling)

    val shoppingList1=List(Menu.COLA)
    shopping.calculateBill(shoppingList1) should be (BigDecimal(0.50))

    val shoppingList2=List(Menu.COLA, Menu.COFFEE)
    shopping.calculateBill(shoppingList2) should be (BigDecimal(1.50))

  }

  "A shopping list with no hotfood " should " calculate the price with 10% service charge correctly " in {
    val shopping = new Shopping(ServiceChargeBilling)

    val shoppingList1=List(Menu.COLA, Menu.COFFEE, Menu.CHEESE_SANDWICH)
    shopping.calculateBill(shoppingList1) should be (BigDecimal(3.85))

    val shoppingList2=List(Menu.COLA, Menu.COFFEE, Menu.CHEESE_SANDWICH, Menu.CHEESE_SANDWICH, Menu.CHEESE_SANDWICH)
    shopping.calculateBill(shoppingList2) should be (BigDecimal(8.25))
  }

  "A shopping list with hotfood " should " calculate the price with 20% service charge to a maximum 20 pounds correctly " in {
    val shopping = new Shopping(ServiceChargeBilling)

    /* 1 COLA, 1 COFFEE, 1 CHEESE SANDWICH, 2 STEAK SANDWICH FOR A BILL WHERE THE SERVICE TAX IS LESS THAN 20 POUNDS*/
    val shoppingList1=List(Menu.COLA, Menu.COFFEE, Menu.CHEESE_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH)
    shopping.calculateBill(shoppingList1) should be (BigDecimal(15.00))

    /* 1 COLA, 1 COFFEE, 1 CHEESE SANDWICH, 39 STEAK SANDWICH FOR A BILL WHERE THE SERVICE TAX GOES OVER 20 POUNDS*/
    val shoppingList2=List(Menu.COLA, Menu.COFFEE, Menu.CHEESE_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH,
      Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH,
      Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH,
      Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH,
      Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH,
      Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH,
      Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH,
      Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH, Menu.STEAK_SANDWICH
    )
    shopping.calculateBill(shoppingList2) should be (BigDecimal(190.00))
  }
  "A shopping list with items not in the Menu " should " throw exception " in {
    intercept[NoSuchElementException] {
      val shopping = new Shopping(ServiceChargeBilling)
      val shoppingList1=List(Menu.COLA, Menu.COFFEE, Menu.CHEESE_SANDWICH, "Chicken Masala", Menu.CHEESE_SANDWICH)
      shopping.calculateBill(shoppingList1) should be (BigDecimal(3.85))
    }
  }

}
