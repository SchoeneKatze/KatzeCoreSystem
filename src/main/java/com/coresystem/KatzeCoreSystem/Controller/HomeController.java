package com.coresystem.KatzeCoreSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// No Spring Security imports or checks as it's removed.

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {return "/index.html";}
//
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
//
//    @GetMapping("/employee")
//    public String employeePage() {
//        return "register";
//    }
}
