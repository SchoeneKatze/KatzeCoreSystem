package com.coresystem.KatzeCoreSystem.Repositories;

import com.coresystem.KatzeCoreSystem.Entities.User ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query method to find a user by username
    Optional<User> findByUsername(String username);

    // Custom query methods to check for existence of username or email
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
