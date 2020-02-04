package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.configs.ApplicationProperties;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.repositories.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    @Autowired
    private final GenreRepository genreRepository;
    @Autowired
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
    public void deleteByUid(String genreUid) {
        genreRepository.deleteById(genreUid);
    }

    @Override
    public Genre findByUid(String genreUid) {
        return genreRepository.findById(genreUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), genreUid)));
    }

    @Override
    public long count() {
        return genreRepository.count();
    }

    @Override
    public void insertGenresByBookUid(String bookUid, List<Genre> genres) {
        genreRepository.appendGenresByBookUid(bookUid, genres);
    }

    @Override
    public void editGenresByBookUid(String bookUid, List<Genre> genres) {
        genreRepository.setGenresByBookUid(bookUid, genres);
    }

    @Override
    public void deleteGenresByBookUid(String bookUid, List<Genre> genres) {
        genreRepository.resetGenresByBookUid(bookUid, genres);
    }

    @Override
    public List<Genre> findGenresByBookUid(String bookUid) {
        return genreRepository.findGenresByBookUid(bookUid);
    }
}
