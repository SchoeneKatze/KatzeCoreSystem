package com.coresystem.KatzeCoreSystem;

import com.coresystem.KatzeCoreSystem.Entities.User;
import com.coresystem.KatzeCoreSystem.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class KatzeCoreSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(KatzeCoreSystemApplication.class, args);
	}

//	/**
//	 * CommandLineRunner to create initial users (for development purposes).
//	 * WARNING: Passwords are NOT hashed in this version. DO NOT USE IN PRODUCTION.
//	 * @param userService The UserService bean to interact with user data.
//	 * @return A CommandLineRunner instance.
//	 */
//	@Bean
//	public CommandLineRunner demoData(UserService userService) {
//		return args -> {
//			// Create a regular user if not exists
//			if (userService.findByUsername("user").isEmpty()) {
//				User user = new User("user", "password", "user@example.com", Set.of("ROLE_USER"));
//				userService.registerNewUser(user);
//				System.out.println("Created user: user/password (plaintext)");
//			}
//
//			// Create a management user if not exists
//			if (userService.findByUsername("manager").isEmpty()) {
//				User manager = new User("manager", "managerpass", "manager@company.com", Set.of("ROLE_USER", "ROLE_MANAGEMENT"));
//				userService.registerNewUser(manager);
//				System.out.println("Created manager: manager/managerpass (plaintext)");
//			}
//		};
//	}
}

