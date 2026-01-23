package com.example.placement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.placement.service.SampleDataService;

@SpringBootApplication
public class CollegePlacementPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollegePlacementPortalApplication.class, args);
        System.out.println("Application started successfullys");
    }

    @Bean
    CommandLineRunner init(SampleDataService sampleDataService) {
        return args -> {
            sampleDataService.insertAll();
        };
    }
}
