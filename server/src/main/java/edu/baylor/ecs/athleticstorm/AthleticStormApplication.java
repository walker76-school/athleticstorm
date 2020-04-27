/*
 * Filename: AthleticStormApplication.java
 * Author: Andrew Walker
 * Date Last Modified: 4/26/2020
 */

package edu.baylor.ecs.athleticstorm;

import edu.baylor.ecs.athleticstorm.exception.AppException;
import edu.baylor.ecs.athleticstorm.model.auth.Role;
import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import edu.baylor.ecs.athleticstorm.model.auth.User;
import edu.baylor.ecs.athleticstorm.repository.RoleRepository;
import edu.baylor.ecs.athleticstorm.repository.UserRepository;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

/**
 * Runner application for AthleticStorm
 */
@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = { "edu.baylor.ecs.athleticstorm.*" })
public class AthleticStormApplication {

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

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

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }

    /**
     * Configures Tomcat for redirect
     * @return WebServlet factory for Tomcat with redirect
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    /**
     * Returns connector to redirect 8080 to 8443
     * @return connector to redirect 8080 to 8443
     */
    private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }

    /**
     * Adds an admin account to the database
     * @return command line runner for adding admin account
     */
    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {

            @Autowired
            private UserRepository userRepository;

            @Autowired
            private RoleRepository roleRepository;

            @Autowired
            PasswordEncoder passwordEncoder;

            @Override
            public void run(String... args) {

                // create admin account if not exists
                if (!userRepository.existsByUsername(adminUsername)) {
                    User admin = new User();
                    admin.setUsername(adminUsername);
                    admin.setPassword(passwordEncoder.encode(adminPassword));

                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new AppException("Admin Role not set."));
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new AppException("User Role not set."));
                    Role mvpRole = roleRepository.findByName(RoleName.ROLE_MVP)
                            .orElseThrow(() -> new AppException("MVP Role not set."));

                    Set<Role> roles = new HashSet<>();
                    roles.add(adminRole);
                    roles.add(userRole);
                    roles.add(mvpRole);

                    admin.setRoles(roles);

                    userRepository.save(admin);
                }

            }
        };
    }
}
