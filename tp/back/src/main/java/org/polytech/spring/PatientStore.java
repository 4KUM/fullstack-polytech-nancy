package org.polytech.spring;

import java.util.List;

public interface PatientStore {

    void addPatient(Patient patient);

    List<Patient> findAll();
}
