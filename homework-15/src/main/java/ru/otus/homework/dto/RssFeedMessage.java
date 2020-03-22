package ru.otus.homework.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class RssFeedMessage  {
    private String id = new ObjectId().toString();
    private String title;
    private String description;
    private boolean isAboutCoronavirus = false;
}
