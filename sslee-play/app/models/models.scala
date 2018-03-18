package models

import java.text.SimpleDateFormat
import java.util.Date

case class Preparation(orderNumber: Long, product: Product, quantity: Int, location: String)
case class Warehouse(id: Long, name: String)
case class SockItem(id: Long, proudctId: Long, warehouseId: Long, quantity: Long)


object PickList {
  def find(wareHouse: String): List[Preparation]  = {
    val p = Product(5018206244611L, "Paperclips Large5", "Large Plain Pack of 5000")
    Thread.sleep(5000L)
    List(
      Preparation(3141592, p, 200, "Aisle 42 bin 7"),
      Preparation(6535897, p, 500, "Aisle 42 bin 7"),
    )
  }
}

object Wharehouse {
  def find() = {
    List("W123", "W456")
  }
}

object Order {
  def backlog(wharehouse: String): String = {
    Thread.sleep(5000L)
    new SimpleDateFormat("mmss").format(new Date())
  }
}

