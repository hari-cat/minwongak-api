package org.example.aptboardapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AptBoardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AptBoardApiApplication.class, args);
    }

}
