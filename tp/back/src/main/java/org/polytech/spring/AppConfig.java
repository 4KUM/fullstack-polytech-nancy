package org.polytech.spring;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Bean
    public PatientService patientService(PatientStore patientStore) {
        return new PatientService(patientStore);
    }

    @Bean
    public PatientStore patientstore(){
        return new PatientDatabase();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Patient patient() {
        return new Patient();
    }

}
