package org.polytech.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;

public class App {

    public static void main(String[] args) {

        System.out.println("tp-back : application démarrée.");

        try
            (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)){
            PatientService patientService = context.getBean(PatientService.class);

            patientService.SavePatient(new Patient("Patient 1"));
            System.out.println("PatientStore injecte dans PatientService : "
                    + (patientService.getPatientStore() != null));
            System.out.println("Type du PatientStore injecte : "
                    + patientService.getPatientStore().getClass().getSimpleName());

            Patient p1 = context.getBean(Patient.class);
            Patient p2 = context.getBean(Patient.class);

            System.out.println("p1 = " + ObjectUtils.identityToString(p1));
            System.out.println("p2 = " + ObjectUtils.identityToString(p2));
            System.out.println("p1 == p2 ? " + (p1 == p2));
        }

    }
}
