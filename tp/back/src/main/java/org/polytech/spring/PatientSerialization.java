package org.polytech.spring;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientSerialization implements PatientStore {
    private final List<Patient> patients = new ArrayList<>();

    @Override
    public void addPatient(Patient patient) {
        patients.add(patient);
        System.out.println("Patient " + patient.getName() + " has been serialized");
    }

    @Override
    public List<Patient> findAll() {
        return patients;
    }
}
