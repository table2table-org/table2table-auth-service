package com.table2table.auth.service;

import com.table2table.auth.dto.CustomUserDetailsDto;
import com.table2table.auth.entity.UserCred;
import com.table2table.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("authUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetailsDto loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCred userCred = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        userCred.setRole("ROLE_" + userCred.getRole());
        return new CustomUserDetailsDto(userCred); // ✅ Includes password from DB
    }
}