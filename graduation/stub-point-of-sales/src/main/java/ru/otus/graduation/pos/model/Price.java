package ru.otus.graduation.pos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "prices")
public class Price {
    @Id
    private String id;
    private String name;
    private ParentLevel parent;
    private float price;
    private float stock;
}
