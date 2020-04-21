package ru.otus.homework.microservices.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private long uid;

    private CommentatorDto commentator;

    private String commentText;

    private Date commentDate;

}
