package com.vuidoan.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {
    AuditorAware<String> auditorAwareImpl = () -> {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null) {
            return Optional.ofNullable("");
        }
        return Optional.ofNullable(username);
    };

    @Bean
    public AuditorAware<String> auditorProvider() {
        return auditorAwareImpl;
    }
}
