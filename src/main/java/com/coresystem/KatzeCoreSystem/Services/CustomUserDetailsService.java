package com.coresystem.KatzeCoreSystem.Services;

import com.coresystem.KatzeCoreSystem.Repositories.UserRepository;
import com.coresystem.KatzeCoreSystem.Entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections; // Used for authorities if you don't have roles defined yet

@Service // Marks this as a Spring Service component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Inject your UserRepository

    // Constructor for dependency injection
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch the user from your database using the UserRepository
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Return a Spring Security UserDetails object
        // The password retrieved from the database MUST be the BCrypt encoded password.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),      // The username
                user.getPassword(),      // The password (MUST BE BCrypted)
                Collections.emptyList()  // User's authorities/roles (empty for now, add if you have roles)
        );
    }
}