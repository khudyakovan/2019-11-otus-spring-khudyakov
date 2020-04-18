package ru.otus.homework.microservice.genres.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.microservice.genres.config.ApplicationProperties;
import ru.otus.homework.microservice.genres.entity.Genre;
import ru.otus.homework.microservice.genres.repository.GenreRepository;
import ru.otus.homework.microservice.genres.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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

}
