package ru.otus.homework.service;

import java.util.Scanner;

public interface IOService {
    void printLine(String line);

    void printLocalizedMessage(String property, String[] args);

    String readLine();

    int readInteger();

    Scanner getCurrentScanner();
}
