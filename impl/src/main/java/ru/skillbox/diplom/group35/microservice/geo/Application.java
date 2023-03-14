package ru.skillbox.diplom.group35.microservice.geo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import ru.skillbox.diplom.group35.library.core.annotation.EnableBaseRepository;
import ru.skillbox.diplom.group35.microservice.geo.model.City;
import ru.skillbox.diplom.group35.microservice.geo.model.Country;
import ru.skillbox.diplom.group35.microservice.geo.repository.CityRepository;
import ru.skillbox.diplom.group35.microservice.geo.repository.CountryRepository;

import java.util.UUID;


/**
 * Application
 *
 * @Author Tretyakov Alexandr
 */

@EnableBaseRepository
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
