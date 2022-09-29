package com.ossjk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication()
@PropertySource({ "classpath:druid.properties" })
public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        SpringApplication.run(Application.class, args);

    }

}
