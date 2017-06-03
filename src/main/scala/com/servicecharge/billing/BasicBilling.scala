package com.servicecharge.billing

import com.servicecharge.menu.Menu

/**
  * Created by Myilvahanan on 03/06/2017.
  */
object BasicBilling extends Billing {
  def calculateBill(shoppingList:List[String]): BigDecimal = {
    var returnValue:BigDecimal = 0.00
    for(item <- shoppingList) {
      if(Menu.items.contains(item)) {
        returnValue += Menu.items.get(item).get.cost
      } else {
        throw new NoSuchElementException(item+" not found")
      }
    }
    returnValue
  }
}
