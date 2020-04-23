/*
 * Filename: AthleticStormApplication.java
 * Author: Andrew Walker
 * Date Last Modified: 4/1/2020
 */

package edu.baylor.ecs.athleticstorm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Runner application for AthleticStorm
 */
@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = { "edu.baylor.ecs.athleticstorm.*" })
public class AthleticStormApplication {

    /**
     * Sets timezone of application
     */
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

    /**
     * Runs the application
     * @param args command-line args
     */
	public static void main(String[] args) {
		SpringApplication.run(AthleticStormApplication.class, args);
	}

    /**
     * Returns the RestTemplate for injection
     * @return the RestTemplate for injection
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
