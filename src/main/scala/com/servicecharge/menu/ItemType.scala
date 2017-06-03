package com.servicecharge.menu

/**
  * Created by Myilvahanan on 03/06/2017.
  */
object ItemType extends Enumeration {
  type ItemType = Value

  val Hot = Value("Hot")
  val Cold = Value("Cold")

  def apply(itemType: String) = {
    if (itemType == null) {
      throw new NullPointerException
    }
    ItemType.withName(itemType)
  }
}
