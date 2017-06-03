package com.servicecharge.shopping

import com.servicecharge.billing.Billing

/**
  * Created by Myilvahanan on 03/06/2017.
  */
class Shopping(billing:Billing) {
  def calculateBill(shoppingList:List[String]): BigDecimal = {
    billing.calculateBill(shoppingList).setScale(2)
  }
}
