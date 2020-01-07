package ru.otus.homework.service;

public interface IOService {

    void printLocalizedMessage(String property, String... args);

    String readLine();

    int readInteger();
}
