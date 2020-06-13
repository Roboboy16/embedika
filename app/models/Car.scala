package models

import slick.jdbc.MySQLProfile.api._
import play.api.libs.json.{JsPath, JsValue, Json, Reads, Writes}
import play.api.libs.functional.syntax._

class CarTable (tag:Tag) extends  Table[Car](tag,"Cars") {
  def number = column[Int]("number",O.PrimaryKey)
  def brand = column[String]("brand")
  def color = column[String]("color")
  def year = column[Int]("year")

  def * = (number,brand,color,year) <> ((Car.apply _).tupled,Car.unapply)
}

case class Car(number: Int, brand:String, color:String, year:Int)

object Car {
  implicit val carReader: Reads[Car] =
    ((JsPath \ "number").read[Int] and
      (JsPath \ "brand").read[String] and
      (JsPath \ "color").read[String] and
      (JsPath \ "year").read[Int] )(Car.apply _)

  implicit val carWriter: Writes[Car] = new Writes[Car] {
    def writes (car:Car) = Json.obj(
      "number"->car.number,
      "brand"-> car.brand,
      "color"->car.color,
      "year"->car.year
    )
  }
}
