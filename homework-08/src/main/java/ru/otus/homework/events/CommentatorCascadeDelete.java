package ru.otus.homework.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.models.Commentator;
import ru.otus.homework.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class CommentatorCascadeDelete extends AbstractMongoEventListener<Commentator> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Commentator> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.deleteCommentsFromBooksWhereCommentatorId(id);
    }
}
