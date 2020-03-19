Домашнее задание #14


На основе Spring Batch разработать процедуру миграции данных из реляционного хранилища в NoSQL или наоборот
Цель: Цель: мигрировать данные с помощью Spring Batch Результат: приложение для пакетных обработок данных на Spring Batch
1. Задание может быть выполнено в отдельном репозитории, с сущностями из ДЗ JPA и MongoDB.
2. Вы можете выбрать другую доменную модель
3. Не обязательно добавлять процесс миграции в веб-приложение. Приложение может быть оформлено в виде отдельной утилиты.
3. Используя Spring Batch, следите, чтобы связи сущностей сохранились.
4. Опционально: Сделать restart задачи с помощью Spring Shell

Реализовано:
- подключение к реляционной БД (MySQL);
- миграция данных в MongoDB
Структура базы данных Mongo:
- авторы. Таблица authorsMongo, хранится отдельно
- жанры. Таблица genresMongo, хранится отдельно
- книги. Таблица books, хранит в себе:
    - ссылку на таблицу авторов;
    - ссылку на таблицу жанров;
    - внутри себя содержит внедренную сущность "комментарии" и ссылку на таблицу пользователей;
- пользователи. Таблица пользователей, внутри содержит сущность "роли"