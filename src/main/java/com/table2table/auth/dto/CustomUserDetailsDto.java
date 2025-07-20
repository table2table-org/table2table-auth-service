package com.table2table.auth.dto;


import com.table2table.auth.entity.UserCred;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetailsDto implements UserDetails {

    private UserCred userCred; // your entity

    public UserCred getUser() {
        return userCred;
    }

    public CustomUserDetailsDto(UserCred userCred) {
        this.userCred = userCred;
    }

    public Long getId() {
        return userCred.getCredId();
    }

    // delegate all other UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userCred.getRole()));
    }

    @Override
    public String getPassword() {
        return userCred.getPassword();
    }

    @Override
    public String getUsername() {
        return userCred.getEmail(); // or username field
    }

    // remaining methods...
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
