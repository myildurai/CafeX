package com.servicecharge.billing

import com.servicecharge.menu.Menu

/**
  * Created by Myilvahanan on 03/06/2017.
  */
object ServiceChargeBilling extends Billing {
  final val SERVICE_CHARGE_WITHOUT_HOT_FOOD=0.10
  final val SERVICE_CHARGE_WITH_HOT_FOOD=0.20
  final val SERVICE_CHARGE_WITH_HOT_FOOD_MAX=20.00

  def calculateBill(shoppingList:List[String]): BigDecimal = {
    var returnValue:BigDecimal = 0.00
    var totalPurchase:BigDecimal = 0.00
    var onlyDrinks=true
    var hasHotFood=false

    for(item <- shoppingList) {
      if(Menu.items.contains(item)) {
        totalPurchase += Menu.items.get(item).get.cost
      } else {
        throw new NoSuchElementException(item+" not found")
      }
    }
    if(shoppingList.contains(Menu.STEAK_SANDWICH))
      hasHotFood=true
    if(shoppingList.contains(Menu.CHEESE_SANDWICH) || shoppingList.contains(Menu.STEAK_SANDWICH))
      onlyDrinks=false

    var serviceCharge:BigDecimal = 0.00
    if (onlyDrinks)
      serviceCharge = 0.00
    else if (hasHotFood) {
      serviceCharge = totalPurchase * SERVICE_CHARGE_WITH_HOT_FOOD
      if (serviceCharge>SERVICE_CHARGE_WITH_HOT_FOOD_MAX)
        serviceCharge=SERVICE_CHARGE_WITH_HOT_FOOD_MAX
    } else
      serviceCharge = totalPurchase*SERVICE_CHARGE_WITHOUT_HOT_FOOD

    returnValue=totalPurchase+serviceCharge
    returnValue
  }

}
