package ru.otus.homework.microservices.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentatorDto {

    private long uid;

    private String login;

    private String firstName;

    private String lastName;
}
