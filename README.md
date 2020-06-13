# embedika
Test task

 О приложении
===================
Данное REST API написанно с помощью **play** и **slick** framework'ов, в качетсве БД используется **MySQL сервер**.

Написанны Unit Test'ы (Тесты на добавление и удаление работают криво, если запись использующаяся в тесте уже есть в таблице)

 Запуск сервера
==================
Сервер компилируется и запускаеться с помощью sbt. Url локального сервера <http://localhost:9000>.

 Примеры запросов 
=====================
 Получение всех записей
--------------------------
GET http://localhost:9000/getAllCars

Ответ ввиде json: {"number" : value, "brand"  :"value", "color" : "value", "year" : value} (**number и year** : Int, **color и brand**: String)

 Добавление записи
-----------------------
POST http://localhost:9000/addCar

В теле json ввида : {"number" : value, "brand"  :"value", "color" : "value", "year" : value}

Удаление записи
-----------------------
POST http://localhost:9000/removeCar/${number}

 Получение количества записей в таблице
------------------------------------------
GET http://localhost:9000/dataBase/count
