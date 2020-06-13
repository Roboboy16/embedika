package controllers

import javax.inject._
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import DataBase.DataBase._
import models.{Car, CarTable}
import play.api.Logger
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class APIController @Inject()(controllerComponents: ControllerComponents)
                             (implicit val executionContext:ExecutionContext)
  extends AbstractController(controllerComponents) {

  val logger = Logger.apply(this.getClass)
  val cars = TableQuery[CarTable]

  def getAllCars = Action.async {
    logger.trace("Started getting data")
    val starTime = System.currentTimeMillis()
    val futureResult:Future[Seq[Car]] = dataBase.run(cars.result)
    futureResult.map{x=>
      logger.trace(s"Data received in ${System.currentTimeMillis()-starTime} ms")
      Ok(Json.toJson(x))
    }
  }

  def addCar = Action(parse.json).async { request =>
    val startTime = System.currentTimeMillis()
    logger.trace("Started adding car in table")
    val carResult = request.body.validate[Car]
    carResult.fold(
      errors => {
        logger.error("Incorrect data")
        Future{BadRequest(Json.obj("Error" -> JsError.toJson(errors)))}
      },
      car => {
        logger.trace(s"Got car with number:${car.number}, brand: ${car.brand}, color: ${car.color}, year: ${car.year} ")
        val exists = cars.filter(x => x.number === car.number).exists
        dataBase.run(exists.result).map {
          case true =>
            logger.error("Record already exist")
            BadRequest(Json.obj("error" -> "record exists"))
          case false =>
            val insert = cars += car
            dataBase.run(insert)
            logger.trace(s"Record added, time processed ${System.currentTimeMillis()-startTime} ms")
            Ok(Json.obj("message" -> "success"))
        }
      }
        )
  }

  def removeCar(number:Int) = Action.async {
    logger.trace(s"Deleting record with number: ${number}")
    val startTime = System.currentTimeMillis()
    val remove = cars.filter(_.number===number).delete
    val futureResult = dataBase.run(remove)
    futureResult.map{
      case 0 =>
        logger.error("Record doesn't exist")
        BadRequest(Json.obj("error"->"record doesn't exist"))
      case _ =>
        logger.trace(s"Record deleted in ${System.currentTimeMillis()-startTime} ms")
        Ok(Json.obj("message"->"success"))
    }
  }

  def dataBaseCount = Action.async {
    logger.trace("Getting ammount of records")
    dataBase.run(cars.size.result).map { x =>
      logger.trace(s"Amount of records:${x}")
      Ok(Json.obj("records" -> x))
    }
  }

  def wrongPath(string: String) = Action {
    logger.error(s"Wrong path was tried\n Path: ${string}")
    BadRequest("This route doesn't exist")
  }
}
