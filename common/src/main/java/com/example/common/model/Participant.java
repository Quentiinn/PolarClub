package com.example.common.model;

/**
 * Created by antho on 13/02/2018.
 */

public class Participant {

    private String name;

    private int battements;

    public Participant() {}

    public Participant(String name, int battement) {
        this.name = name;
        this.battements = battement;
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
