package ru.otus.homework.microservices.books.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.otus.homework.microservices.books.config.CommentsFallback;
import ru.otus.homework.microservices.books.dto.CommentDto;

import java.util.List;

@FeignClient(name = "comments-microservice", fallback = CommentsFallback.class)
public interface CommentService {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/comments/{id}")
    List<CommentDto> findCommentsByBookId(@PathVariable("id") long id);

}
