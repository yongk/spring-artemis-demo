package com.example.spring.artemis.model;

public class Study {
    private int id;
    private Patient patient;

    public Study() {
    }

    public Study(int id, Patient patient) {
        this.id = id;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Study{" +
                "id=" + id +
                ", patient=" + patient +
                '}';
    }
}
