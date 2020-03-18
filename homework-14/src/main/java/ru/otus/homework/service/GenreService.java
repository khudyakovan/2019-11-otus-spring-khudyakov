package ru.otus.homework.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework.entity.mongo.GenreMongo;
import ru.otus.homework.entity.mysql.Genre;

@Service
public class GenreService implements GenericService<GenreMongo, Genre>{

    @Override
    public GenreMongo transform(ru.otus.homework.entity.mysql.Genre entity) {
        return new GenreMongo(String.valueOf(new ObjectId()), entity.getName());
    }
}
