package com.servicecharge.billing

/**
  * Created by Myilvahanan on 03/06/2017.
  */
trait Billing {
  def calculateBill(purchase:List[String]): BigDecimal
}
