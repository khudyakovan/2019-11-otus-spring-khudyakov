package ru.otus.homework.dao.relation;

public class BookGenreRelation {
    private long uid;
    private long bookUid;
    private long genreUid;

    public BookGenreRelation() {
    }

    public BookGenreRelation(long uid, long bookUid, long genreUid) {
        this.uid = uid;
        this.bookUid = bookUid;
        this.genreUid = genreUid;
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

    public long getGenreUid() {
        return genreUid;
    }

    public void setGenreUid(long genreUid) {
        this.genreUid = genreUid;
    }
}
