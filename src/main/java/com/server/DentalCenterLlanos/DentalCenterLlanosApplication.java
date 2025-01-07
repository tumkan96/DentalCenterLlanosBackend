package com.server.DentalCenterLlanos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = "com.server.DentalCenterLlanos")
public class DentalCenterLlanosApplication {
    public static void main(String[] args) {
        SpringApplication.run(DentalCenterLlanosApplication.class, args);
    }
}
