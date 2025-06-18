package com.coresystem.KatzeCoreSystem.Controller;

import com.coresystem.KatzeCoreSystem.Dto.LoginRequest;
import com.coresystem.KatzeCoreSystem.Dto.RegistrationRequest;
import com.coresystem.KatzeCoreSystem.Entities.User;
import com.coresystem.KatzeCoreSystem.Services.UserService;
import jakarta.servlet.http.HttpSession; // For manual session management (basic example)
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the login page.
     * In this non-security version, 'error' and 'logout' parameters are handled manually.
     * @param model Model for passing attributes to the Thymeleaf template.
     * @param error Request parameter indicating login error.
     * @param logout Request parameter indicating successful logout.
     * @return The name of the Thymeleaf template ("login").
     */
    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "login"; // Renders src/main/resources/templates/login.html
    }

    /**
     * Handles login form submission (basic, insecure authentication).
     * WARNING: This is NOT secure for production.
     * @param request LoginRequest containing username and password.
     * @param session HttpSession for manual session management.
     * @param redirectAttributes Used to pass messages after redirection.
     * @return Redirects to homepage on success, or back to login with error.
     */
    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        if (request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username and password are required.");
            return "redirect:/login?error";
        }

        if (userService.authenticate(request.getUsername(), request.getPassword())) {
            // Manual session management: Set a flag or user ID in session
            session.setAttribute("loggedInUser", request.getUsername());
            session.setAttribute("isManagement", userService.findByUsername(request.getUsername()).map(u -> u.getRoles().contains("ROLE_MANAGEMENT")).orElse(false));
            return "redirect:/"; // Redirect to homepage on successful login
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
            return "redirect:/login?error"; // Redirect back to login with error
        }
    }

    /**
     * Displays the registration page.
     * @param model Model for passing attributes to the Thymeleaf template.
     * @return The name of the Thymeleaf template ("register").
     */
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest()); // For binding form data
        return "register"; // Renders src/main/resources/templates/register.html
    }

    /**
     * Handles new user registration form submission.
     * @param request RegistrationRequest containing username, email, and password.
     * @return Redirects to login page on success, or back to register with error.
     */

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) { // Use @RequestBody for JSON
        if (request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty() ||
                request.getEmail() == null || request.getEmail().isEmpty()) {
            return new ResponseEntity<>("All fields are required.", HttpStatus.BAD_REQUEST);
        }

        try {
            User newUser = new User();
            newUser.setUsername(request.getUsername());
            newUser.setEmail(request.getEmail());
            // WARNING: Password is NOT hashed here. This is INSECURE for production.
            // Ensure hashing is done in UserService before setting it on the User entity.
            newUser.setPassword(request.getPassword()); // Pass this to UserService for hashing

            userService.registerNewUser(newUser); // Assuming userService hashes the password
            return new ResponseEntity<>("Account created successfully! Please log in.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // e.g., "Username already taken!"
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred during registration.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute RegistrationRequest request, RedirectAttributes redirectAttributes) {
//        if (request.getUsername() == null || request.getUsername().isEmpty() ||
//                request.getPassword() == null || request.getPassword().isEmpty() ||
//                request.getEmail() == null || request.getEmail().isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "All fields are required.");
//            return "redirect:/register";
//        }
//
//        try {
//            User newUser = new User();
//            newUser.setUsername(request.getUsername());
//            newUser.setEmail(request.getEmail());
//            newUser.setPassword(request.getPassword()); // Password will be stored plaintext in this non-security version
//
//            userService.registerNewUser(newUser);
//            redirectAttributes.addFlashAttribute("successMessage", "Account created successfully! Please log in.");
//            return "redirect:/login"; // Redirect to login page on success
//        } catch (IllegalArgumentException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//            return "redirect:/register"; // Redirect back to registration with error
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred during registration.");
//            return "redirect:/register";
//        }
//    }

    /**
     * Handles logout (basic, insecure).
     * @param session HttpSession to invalidate.
     * @return Redirects to login page with logout parameter.
     */
    @PostMapping("/logout")
    public String processLogout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "redirect:/login?logout";
    }
}