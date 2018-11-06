package com.maciejbihun;


import com.maciejbihun.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author BHN
 */
@SpringBootApplication
@EnableJpaRepositories
public class Application{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
