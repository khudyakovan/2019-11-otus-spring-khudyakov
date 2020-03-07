package ru.otus.homework.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.models.Author;
import ru.otus.homework.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class AuthorCascadeDelete extends AbstractMongoEventListener <Author> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.deleteAuthorFromBooksByAuthorId(id);
    }
}
