package ru.fa.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signupGet(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("action", "/signup");
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute("user") User user) {
        userService.save(user, Role.USER);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginGet(@ModelAttribute("user") User user) {
        return "auth/login";
    }

    @GetMapping("/admin")
    public String addAdminGet(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("action", "/admin");
        return "auth/signup";
    }

    @PostMapping("/admin")
    public String addAdminPost(@ModelAttribute("user") User user) {
        userService.save(user, Role.ADMIN);
        return "redirect:/";
    }
}
