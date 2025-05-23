package dev.shade.shared.security.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
@Builder
public class UserPrincipal implements UserDetails {

    UUID id;

    String userName;

    String password;

    String email;

    Role role;

    boolean isAccountNonLocked;

    @Value
    @Builder
    public static class Role implements Serializable {

        RoleCode roleCode;

        Set<String> permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.concat(Optional.of(role)
                                     .map(Role::getRoleCode)
                                     .map(RoleCode::getCode)
                                     .stream(),

                             Optional.of(role)
                                     .map(Role::getPermissions)
                                     .stream()
                                     .flatMap(Collection::stream))

                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toSet());
    }

    public List<String> getAuthoritiesList() {
        return getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
