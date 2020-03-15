package ru.otus.homework.service;

public interface GenericService<T,T1>{
    T transform(T1 entity);
}
