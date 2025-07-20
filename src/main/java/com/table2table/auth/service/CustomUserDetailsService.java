package com.table2table.auth.service;


import com.table2table.auth.dto.CustomUserDetailsDto;
import com.table2table.auth.entity.UserCred;
import com.table2table.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Or UserService if you prefer

    @Override
    public CustomUserDetailsDto loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCred userCred = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        userCred.setRole("ROLE_"+ userCred.getRole());
        return new CustomUserDetailsDto(userCred); // ✅ injects role like "ADMIN"
    }
}
