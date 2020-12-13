package com.prosolovich.tricr.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TricrExamApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TricrExamApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TricrExamApplication.class);
    }

}
