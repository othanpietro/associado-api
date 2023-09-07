package br.com.associadoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class AssociadoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssociadoApiApplication.class, args);
    }

}
