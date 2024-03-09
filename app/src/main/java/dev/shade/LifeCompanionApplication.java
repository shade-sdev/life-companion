package dev.shade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LifeCompanionApplication {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(200000);
        SpringApplication.run(LifeCompanionApplication.class, args);
    }
}
