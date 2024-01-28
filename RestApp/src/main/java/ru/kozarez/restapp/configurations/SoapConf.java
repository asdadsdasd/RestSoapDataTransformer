package ru.kozarez.restapp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConf {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        //marshaller.setContextPath("ru.test_soap");
        marshaller.setPackagesToScan("ru.test_soap");
        return marshaller;
    }
}
