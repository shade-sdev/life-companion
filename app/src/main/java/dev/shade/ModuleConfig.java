package dev.shade;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {"dev.shade.domain", "dev.shade.application.service", "dev.shade.infrastructure"})
@EnableJpaRepositories(basePackages = {"dev.shade.infrastructure"})
@EntityScan("dev.shade.infrastructure.repository")
@EnableJpaAuditing
@EnableTransactionManagement
@EnableCaching
public class ModuleConfig {
}
