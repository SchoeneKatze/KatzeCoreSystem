package com.coresystem.KatzeCoreSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// No Spring Security imports or checks as it's removed.

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {return "/index.html";}

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("param.error", true);
        }
        if (logout != null) {
            model.addAttribute("param.logout", true);
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // This will look for src/main/resources/templates/register.html
    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }

//    @GetMapping("/employee")
//    public String employeePage() {
//        return "register";
//    }
}
