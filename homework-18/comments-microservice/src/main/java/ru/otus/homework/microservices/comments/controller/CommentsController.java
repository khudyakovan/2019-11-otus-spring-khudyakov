package ru.otus.homework.microservices.comments.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.microservices.comments.dto.CommentDto;
import ru.otus.homework.microservices.comments.entity.Comment;
import ru.otus.homework.microservices.comments.service.CommentService;
import ru.otus.homework.microservices.comments.service.CommentatorService;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentsController {

    private final CommentatorService commentatorService;
    private final CommentService commentService;
    private static final String URI = "/api/v1/comments";

    @GetMapping(value = URI)
    public List<CommentDto> findAll() {
        return commentMapper(commentService.findAll());
    }

    @GetMapping(path = URI + "/{id}")
    public List<CommentDto> findCommentsByBookId(@PathVariable("id") long id) {
        return commentMapper(commentService.findCommentsByBookUid(id));
    }

    @PostMapping(path = URI)
    public Comment save(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @PutMapping(path = URI)
    public Comment update(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @DeleteMapping(path = URI + "/{id}")
    public void deleteById(@PathVariable long id) {
        commentService.deleteByUid(id);
    }

    private List<CommentDto> commentMapper(List<Comment> comments) {
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<CommentDto>>() {}.getType();
        return modelMapper.map(comments, listType);
    }

}
