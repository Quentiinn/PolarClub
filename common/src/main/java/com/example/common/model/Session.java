package com.example.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by antho on 07/02/2018.
 */

public class Session implements Serializable{

    private String uid;
    private long debut;
    private String prof;

    public Session() {}

    public Session(String uid, long debut, String prof) {
        this.uid = uid;
        this.debut = debut;
        this.prof = prof;
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
}
