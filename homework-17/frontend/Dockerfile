FROM node:alpine

ENV PROJECT_DIR=/opt/homework-17
ENV REMOTE_DIR=hw10-ng-app
ENV NG_CLI_ANALYTICS=false

RUN rm -Rf $PROJECT_DIR

RUN mkdir -p $PROJECT_DIR

WORKDIR $PROJECT_DIR

#Поскольку образ почти пустой, добавляю в него git
RUN apk update
RUN apk add git
#RUN apk add vim
#RUN apk add mc

#Получение прошлого ДЗ#10 из github внутри контейнера
RUN git init
RUN git config core.sparsecheckout true
RUN echo $REMOTE_DIR > .git/info/sparse-checkout
RUN git remote add -f origin https://github.com/khudyakovan/2019-11-otus-spring-khudyakov.git
RUN git pull origin master
RUN mv ./$REMOTE_DIR/* ./
RUN rm -Rf ./$REMOTE_DIR && rm -Rf ./.git

# Замена адреса в конфигурации прокси в приложении Angular
# Мне это решение не очень нравится, но лучше ничего не придумал
# {
#   "/api/*": {
#     "target": "http://localhost:8080",
#     "secure": false,
#     "logLevel": "debug"
#   }
# }
RUN sed -i 's/localhost/hw17-backend/g' ./proxy.config.json

#Получение зависимостей приложения и устанавка Angular
RUN npm install
RUN npm install -g @angular/cli

EXPOSE 4200