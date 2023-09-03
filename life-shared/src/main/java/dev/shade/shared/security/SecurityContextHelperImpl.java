package dev.shade.shared.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextHelperImpl implements SecurityContextHelper {

    @Override
    public String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}