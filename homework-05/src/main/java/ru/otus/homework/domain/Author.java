package ru.otus.homework.domain;

import java.util.List;

public class Author {
    private long uid;
    private String fullName;
    private String penName;

    public Author(String fullName, String penName) {
        this.fullName = fullName;
        this.penName = penName;
    }

    public Author(long uid, String fullName, String penName) {
        this.uid = uid;
        this.fullName = fullName;
        this.penName = penName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }
}
