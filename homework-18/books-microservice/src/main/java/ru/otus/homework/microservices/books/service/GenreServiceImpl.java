package ru.otus.homework.microservices.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.microservices.books.config.ApplicationProperties;
import ru.otus.homework.microservices.books.entity.Genre;
import ru.otus.homework.microservices.books.repository.GenreRepository;
import ru.otus.homework.microservices.books.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ApplicationProperties applicationProperties;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public void deleteByUid(long genreUid) {
        genreRepository.deleteById(genreUid);
    }

    @Override
    public Genre findByUid(long genreUid) {
        return genreRepository.findById(genreUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), genreUid)));
    }

    @Override
    public long count() {
        return genreRepository.count();
    }

    @Override
    public void insertGenresByBookUid(long bookUid, List<Genre> genres) {
        genreRepository.appendGenresByBookUid(bookUid, genres);
    }

    @Override
    public void editGenresByBookUid(long bookUid, List<Genre> genres) {
        genreRepository.setGenresByBookUid(bookUid, genres);
    }

    @Override
    public void deleteGenresByBookUid(long bookUid, List<Genre> genres) {
        genreRepository.resetGenresByBookUid(bookUid, genres);
    }

    @Override
    public List<Genre> findGenresByBookUid(long bookUid) {
        return genreRepository.findGenresByBookUid(bookUid);
    }
}
