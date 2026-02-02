package com.example.placement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CollegePlacementPortalApplication {
        public static void main(String[] args) {
                SpringApplication.run(CollegePlacementPortalApplication.class, args);
                System.out.println("Application started successfully");
        }
}
