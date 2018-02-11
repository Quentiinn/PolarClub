package com.example.quentincourvoisier.polarclub.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by antho on 07/02/2018.
 */

public class Session {

    private String uid;
    private long debut;
    private Map<String, Integer> frequences;

    public Session() {}

    public Session(String uid, long debut) {
        this.uid = uid;
        this.debut = debut;
        this.frequences = new HashMap<>();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getDebut() {
        return debut;
    }

    public void setDebut(long debut) {
        this.debut = debut;
    }

    public Map<String, Integer> getFrequences() {
        return frequences;
    }

    public void setFrequences(Map<String, Integer> frequences) {
        this.frequences = frequences;
    }
}
