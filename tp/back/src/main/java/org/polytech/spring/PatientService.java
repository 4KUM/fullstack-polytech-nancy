package org.polytech.spring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientStore patientStore;
    public PatientService(@Qualifier("patientDatabase") PatientStore patientStore) {
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
