package org.polytech.spring;

public class Patient {
    String name;

    public Patient() {
        this.name = "Patient name";
    }
    public Patient(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Patient name: " + name;
    }
}
