package com.example.common.model;

/**
 * Created by antho on 13/02/2018.
 */

public class Participant {

    private String uid;
    private String uidSession;
    private String name;
    private int battements;

    public Participant() {}

    public Participant(String name, int battement) {
        this.name = name;
        this.battements = battement;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidSession() {
        return uidSession;
    }

    public void setUidSession(String uidSession) {
        this.uidSession = uidSession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBattements() {
        return battements;
    }

    public void setBattements(int battements) {
        this.battements = battements;
    }
}
