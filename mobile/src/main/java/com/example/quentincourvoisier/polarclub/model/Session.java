package com.example.quentincourvoisier.polarclub.model;

/**
 * Created by antho on 07/02/2018.
 */

public class Session {

    private String uid;

    private long date;

    private int nbHeart;

    public Session() {}

    public Session(String uid, long date, int nbHeart) {
        this.uid = uid;
        this.date = date;
        this.nbHeart = nbHeart;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getNbHeart() {
        return nbHeart;
    }

    public void setNbHeart(int nbHeart) {
        this.nbHeart = nbHeart;
    }
}
