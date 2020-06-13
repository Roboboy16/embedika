package DataBase

object DataBase {
  val dataBase = slick.jdbc.MySQLProfile.backend.Database.forConfig("MySQL")
}
