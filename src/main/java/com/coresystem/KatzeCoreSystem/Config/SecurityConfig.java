package com.coresystem.KatzeCoreSystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Allow public access to the homepage, login, registration, and static resources
                        .requestMatchers("/", "/home", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Specify your custom login page URL
                        .loginProcessingUrl("/authenticate") // The URL to which the login form will POST
                        .defaultSuccessUrl("/home", true) // Redirect after successful login
                        .failureUrl("/login?error=true") // Redirect after failed login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // The URL to trigger logout (defaults to /logout)
                        .logoutSuccessUrl("/login?logout=true") // Redirect after successful logout
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Delete session cookie
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Consider enabling CSRF protection for production

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}