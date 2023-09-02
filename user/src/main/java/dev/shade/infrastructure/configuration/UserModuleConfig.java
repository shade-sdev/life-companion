package dev.shade.infrastructure.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"dev.shade.domain", "dev.shade.service", "dev.shade.infrastructure"})
@EnableJpaRepositories(basePackages = {"dev.shade.infrastructure"})
@EntityScan("dev.shade.infrastructure.repository")
@EnableJpaAuditing
public class UserModuleConfig {
}
