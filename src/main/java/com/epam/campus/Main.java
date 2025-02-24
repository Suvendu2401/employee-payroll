package com.epam.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.campus")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
