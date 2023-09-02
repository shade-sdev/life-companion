package dev.shade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LifeCompanionApplication
{
    public static void main(String[] args) {
        SpringApplication.run(LifeCompanionApplication.class, args);
    }
}
