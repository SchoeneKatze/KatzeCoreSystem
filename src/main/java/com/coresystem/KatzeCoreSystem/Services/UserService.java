package com.coresystem.KatzeCoreSystem.Services;

import com.coresystem.KatzeCoreSystem.Entities.User ;
import com.coresystem.KatzeCoreSystem.Repositories.UserRepository ;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user.
     * WARNING: Password is NOT hashed in this version (no Spring Security).
     * DO NOT USE IN PRODUCTION.
     * @param user The user object to register.
     * @return The saved User object, or null if username/email already exists.
     */
    public User registerNewUser(User user) {
        // Check for existing username or email to prevent duplicates
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered!");
        }

        // --- WARNING: Password is NOT hashed in this version ---
        // For a secure application, you MUST hash the password (e.g., using BCryptPasswordEncoder)
        // before saving it to the database.
        // user.setPassword(passwordEncoder.encode(user.getPassword())); // This line would be for security
        // --------------------------------------------------------

        // Assign default role(s) to new user, e.g., "ROLE_USER"
        Set<String> defaultRoles = new HashSet<>();
        defaultRoles.add("ROLE_USER");
        user.setRoles(defaultRoles);

        return userRepository.save(user);
    }

    /**
     * Finds a user by their username.
     * @param username The username to find.
     * @return An Optional containing the User if found, empty otherwise.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Checks if a username exists.
     * @param username The username to check.
     * @return true if username exists, false otherwise.
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Checks if an email exists.
     * @param email The email to check.
     * @return true if email exists, false otherwise.
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Authenticates a user (basic, insecure check without Spring Security).
     * WARNING: This is NOT secure for production.
     * @param username The username.
     * @param password The plaintext password.
     * @return true if credentials match, false otherwise.
     */
    public boolean authenticate(String username, String password) {
        Optional<User> userOptional = findByUsername(username);
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }
}
