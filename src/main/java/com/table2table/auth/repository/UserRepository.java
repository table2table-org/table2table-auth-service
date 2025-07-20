package com.table2table.auth.repository;


import com.table2table.auth.entity.UserCred;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserCred, Long> {

    boolean existsByEmail(String email);

    Optional<UserCred> findByEmail(String email);

}
