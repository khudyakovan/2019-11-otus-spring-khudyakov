package ru.otus.homework.domain;

import java.util.List;

public class Question {
    private int uid;
    private String question;
    private List<Answer> answers;

    public Question() {
    }

    public Question(int uid, String question) {
        this.uid = uid;
        this.question = question;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "Uid=" + uid +
                ", question='" + question + '\'' +
                '}';
    }
}
