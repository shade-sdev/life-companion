package dev.shade.model;

import dev.shade.domain.user.Permission;
import dev.shade.domain.user.Role;
import dev.shade.domain.user.User;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
@Builder(toBuilder = true)
public class UserPrincipal implements UserDetails {

    User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.concat(Optional.of(user)
                                     .map(User::getRole)
                                     .map(Role::getName)
                                     .map(Enum::name)
                                     .stream(),
                             Optional.of(user)
                                     .map(User::getRole)
                                     .map(Role::getPermissions)
                                     .stream()
                                     .flatMap(Collection::stream)
                                     .map(Permission::getName))
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
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
