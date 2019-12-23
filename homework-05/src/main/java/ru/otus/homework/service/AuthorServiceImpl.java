package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public void insert(Author author) {
        authorDao.insert(author);
    }

    @Override
    public void edit(Author author) {
        authorDao.edit(author);
    }

    @Override
    public void deleteByUid(long uid) {
        authorDao.deleteByUid(uid);
    }

    @Override
    public Author getByUid(long uid) {
        return authorDao.getByUid(uid);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public int count() {
        return authorDao.count();
    }
}
