package com.example.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by antho on 07/02/2018.
 */

public class Session {

    private String uid;
    private long debut;
    private String prof;
    private List<Participant> participants;

    public Session() {}

    public Session(String uid, long debut, String prof) {
        this.uid = uid;
        this.debut = debut;
        this.prof = prof;
        participants = new ArrayList<>();
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

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }
}
