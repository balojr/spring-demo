package com.example.martin;

import com.example.martin.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class MartinApplication {

    private final Environment env;

    public MartinApplication(Environment env) {
        this.env = env;
    }


    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
    }


    public static void main(String[] args) {
        SpringApplication.run(MartinApplication.class, args);
    }


}
