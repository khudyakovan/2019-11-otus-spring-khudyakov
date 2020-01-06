package ru.otus.homework.dao.relation;

public class BookAuthorRelation {
    private long uid;
    private long bookUid;
    private long authorUid;

    public BookAuthorRelation() {
    }

    public BookAuthorRelation(long uid, long bookUid, long authorUid) {
        this.uid = uid;
        this.bookUid = bookUid;
        this.authorUid = authorUid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getBookUid() {
        return bookUid;
    }

    public void setBookUid(long bookUid) {
        this.bookUid = bookUid;
    }

    public long getAuthorUid() {
        return authorUid;
    }

    public void setAuthorUid(long authorUid) {
        this.authorUid = authorUid;
    }
}
