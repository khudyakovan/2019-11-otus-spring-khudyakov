package ru.otus.homework.microservices.books.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.microservices.books.dto.CommentDto;
import ru.otus.homework.microservices.books.service.CommentService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentsFallback implements CommentService {

    private final ApplicationProperties applicationProperties;

    @Override
    public List<CommentDto> findCommentsByBookId(long id) {
        CommentDto dto = new CommentDto();
        dto.setCommentText(applicationProperties.getCommentServiceFallbackMessage());
        dto.setCommentDate(Calendar.getInstance().getTime());
        List<CommentDto> list = new ArrayList<>();
        list.add(dto);
        return list;
    }
}
