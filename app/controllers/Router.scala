package controllers

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class Router @Inject() (cc: APIController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/getAllCars") =>
      cc.getAllCars()
    case POST(p"/addCar") =>
      cc.addCar()
    case POST(p"/removeCar/${number}")=>
      cc.removeCar(number.toInt)
    case GET(p"/dataBase/count") =>
      cc.dataBaseCount()
    case x =>
      cc.wrongPath(x.toString())
  }
}
