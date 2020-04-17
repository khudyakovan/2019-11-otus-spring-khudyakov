FROM maven:3.3-jdk-8

ENV PROJECT_DIR=/opt/homework-17

RUN rm -Rf $PROJECT_DIR

RUN mkdir -p $PROJECT_DIR

EXPOSE 8080

WORKDIR $PROJECT_DIR

#Получение прошлого ДЗ#10 из github внутри контейнера
RUN git init
RUN git config core.sparsecheckout true
RUN echo homework-10 > .git/info/sparse-checkout
RUN git remote add -f origin https://github.com/khudyakovan/2019-11-otus-spring-khudyakov.git
RUN git pull origin master
RUN mv ./homework-10/* ./
RUN rm -Rf ./homework-10 && rm -Rf ./.git

#Получение зависимостей приложения и сборка
RUN mvn dependency:resolve
RUN mvn package -DskipTests

RUN mv ./target/homework-*.jar ./target/hw17-backend.jar
