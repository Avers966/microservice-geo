package ru.skillbox.diplom.group35.microservice.geo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Config
 *
 * @Author Tretyakov Alexandr
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "geo-settings")
public class Config {
    private List<CountryConfig> countries;
    private String userAgent;
}
