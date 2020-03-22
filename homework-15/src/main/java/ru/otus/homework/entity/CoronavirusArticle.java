package ru.otus.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "coronavirus_articles")
public class CoronavirusArticle {

    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
}
