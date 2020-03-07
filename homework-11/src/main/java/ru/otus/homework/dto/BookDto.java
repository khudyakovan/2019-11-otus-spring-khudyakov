package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Genre;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String title;

    private long isbn;

    private int publicationYear;

    private List<Author> authors;

    private List<Genre> genres;

    private List<CommentDto> comments;
}
