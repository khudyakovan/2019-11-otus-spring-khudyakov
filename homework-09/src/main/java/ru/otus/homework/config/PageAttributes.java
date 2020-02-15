package ru.otus.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.otus.homework.service.TranslationService;

@Component
@RequiredArgsConstructor
public class PageAttributes {
    @Autowired
    private final TranslationService translationService;

    public void setCommonAttributes(Model model) {
        model.addAttribute("pageHeader", translationService.getTranslation("view.header"));
        model.addAttribute("booksLink", translationService.getTranslation("view.sidebar.links.books"));
        model.addAttribute("addNewBookLink", translationService.getTranslation("view.sidebar.links.books.add.new.book"));
        model.addAttribute("authorsLink", translationService.getTranslation("view.sidebar.links.authors"));
        model.addAttribute("genresLink", translationService.getTranslation("view.sidebar.links.genres"));
    }

    public void setBookAttributes(Model model) {
        model.addAttribute("id", translationService.getTranslation("view.book.header.id"));
        model.addAttribute("title", translationService.getTranslation("view.book.header.title"));
        model.addAttribute("isbn", translationService.getTranslation("view.book.header.isbn"));
        model.addAttribute("isbn", translationService.getTranslation("view.book.header.isbn"));
        model.addAttribute("publishing", translationService.getTranslation("view.book.header.publishing.year"));
        model.addAttribute("authorsLabel", translationService.getTranslation("view.book.header.authors"));
        model.addAttribute("genresLabel", translationService.getTranslation("view.book.header.genres"));
    }

    public void setBookDetailsAttributes(Model model) {
        model.addAttribute("detailsHeader", translationService.getTranslation("view.book.details.description.header"));
        model.addAttribute("commentsHeader", translationService.getTranslation("view.book.details.comments.header"));
        model.addAttribute("commentatorName", translationService.getTranslation("view.book.details.commentator"));
        model.addAttribute("comment", translationService.getTranslation("view.book.details.comment"));
        model.addAttribute("commentDate", translationService.getTranslation("view.book.details.comment.date"));
        model.addAttribute("editLabel", translationService.getTranslation("view.book.details.edit"));
        model.addAttribute("deleteLabel", translationService.getTranslation("view.book.details.delete"));
        model.addAttribute("deleteConfirmationLabel", translationService.getTranslation("view.book.details.delete.confirmation"));
        model.addAttribute("commentDeleteLabel", translationService.getTranslation("view.book.details.comment.delete"));
    }
}
