package com.example.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by antho on 07/02/2018.
 */

public class Session {

    private String uid;
    private long debut;
    private String prof;
    private Map<String, Integer> frequences;

    public Session() {}

    public Session(String uid, long debut, String prof) {
        this.uid = uid;
        this.debut = debut;
        this.prof = prof;
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

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public Map<String, Integer> getFrequences() {
        return frequences;
    }

    public void setFrequences(Map<String, Integer> frequences) {
        this.frequences = frequences;
    }

    public void addParticipant(String pseudo){
        frequences.put(pseudo, new Integer(80));
    }
}
