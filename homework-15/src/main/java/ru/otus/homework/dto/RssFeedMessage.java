package ru.otus.homework.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RssFeedMessage  {
    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private boolean isAboutCoronavirus = false;
}
