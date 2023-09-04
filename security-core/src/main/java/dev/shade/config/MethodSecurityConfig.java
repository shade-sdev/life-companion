package dev.shade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import dev.shade.shared.security.PermissionEvaluatorManager;
import dev.shade.shared.security.model.MainPermissionEvaluator;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfig {

    private final PermissionEvaluatorManager manager;

    @Autowired
    public MethodSecurityConfig(PermissionEvaluatorManager manager) {
        this.manager = manager;
    }

    @Bean
    public MethodSecurityExpressionHandler handler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new MainPermissionEvaluator(manager));
        return handler;
    }
}
