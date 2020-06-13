package controllers

import akka.actor.ActorSystem
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration._


class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "APIController /getAllCars" should {

    "return all records from database" in {
      val controller = inject[APIController]
      val getAllCars = controller.getAllCars().apply(FakeRequest())

      status(getAllCars) mustBe OK
      contentType(getAllCars) mustBe Some("application/json")
    }
  }

  "APIController /removeCar" should {
    "add record into Table" in {
      val controller = inject[APIController]
      val request = FakeRequest(POST,"/addCar").withJsonBody(Json.parse("""{"number":228,"brand":"Merc","color":"Yellow","year":2000}"""))
      implicit val sys = ActorSystem("MyTest")
      val result = call(controller.addCar(),request)

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  "APIController /removeCar" should {
    "return message:success or error:Record already exist" in {
      val controller = inject[APIController]
      val request = FakeRequest(POST,"/addCar").withJsonBody(Json.parse("""{"number":0,"brand":"Merc","color":"Yellow","year":2000}"""))
      implicit val sys = ActorSystem("MyTest")
      Await.result(call(controller.addCar,request),10.seconds)
      val removeCar = controller.removeCar(0).apply(FakeRequest())

      status(removeCar) mustBe OK
      contentType(removeCar) mustBe Some("application/json")
    }
  }
}
