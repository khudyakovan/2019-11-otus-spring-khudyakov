package ru.otus.homework.service;

public interface IOService {
    void printLine(String line);

    void printLocalizedMessage(String property, String...args);

    String readLine();

    int readInteger();
}
