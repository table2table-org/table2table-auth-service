package com.table2table.auth.dto;


import com.table2table.auth.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetailsDto implements UserDetails {

    private User user; // your entity

    public User getUser() {
        return user;
    }

    public CustomUserDetailsDto(User user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    // delegate all other UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // or username field
    }

    // remaining methods...
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
