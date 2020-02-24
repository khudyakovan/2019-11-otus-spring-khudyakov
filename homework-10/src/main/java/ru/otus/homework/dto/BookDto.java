package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.entity.Author;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.entity.Genre;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private long  uid;

    private String title;

    private long isbn;

    private int publicationYear;

    private List<Author> authors;

    private List<Genre> genres;

    private List<CommentDto> comments;
}
