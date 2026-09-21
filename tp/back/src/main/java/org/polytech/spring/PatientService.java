package org.polytech.spring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class PatientService {

    private final PatientStore patientStore;
    public PatientService(PatientStore patientStore) {
        this.patientStore = patientStore;
    }

    public void SavePatient(Patient patient){
        patientStore.addPatient(patient);
    }

    public PatientStore getPatientStore() {
        return patientStore;
    }

    @PostConstruct
    public void init(){
        System.out.println("Patient Service has been initialized");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Patient Service has been destroyed");
    }
}
