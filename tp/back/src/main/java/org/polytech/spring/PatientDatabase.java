package org.polytech.spring;

import com.sun.jdi.event.StepEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.ArrayList;
import java.util.List;

public class PatientDatabase implements PatientStore {
    private final List<Patient> patients = new ArrayList<>();

    @Override
    public void addPatient(Patient p){
        patients.add(p);
        System.out.println("Patient " + p.getName() + " has been added");
    }

    @Override
    public List<Patient> findAll(){
        return patients;
    }

    @PostConstruct
    public void init(){
        System.out.println("Patient database has been initialized");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Patient database has been destroyed");
    }

}
