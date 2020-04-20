### Сервер конфигурации (config-server, порт: 8888)
* Хранит настройки всех приложений
* Реализован с помощью Spring Config Server

### Реестр служб (service-discovery-server, порт: 8761)
* Помогает службам (приложениям/микросервисам) находить друг друга
* Реализован с помощью Eureka

### Микросервис книг
Отдает список книг и их связанных свойств (авторы, жанры, год издания и т.п.)
Комментарии забираются из микросервиса comments-microservice
Включен hystrix и если микросервис  не доступен, то включается fallback 
(см. CommentService и CommentsFallback)
Port: 8081

### Микросервис комментариев к книгам
Отдает комментарии по идентификатору книги
Port: 8082

### API шлюз (gateway, порт: 8080)
* Реализован с помощью Zuul
http://localhost:8080/api/v1/books
http://localhost:8080/api/v1/genres
http://localhost:8080/api/v1/authors
http://localhost:8080/hystrix - включил dashboard