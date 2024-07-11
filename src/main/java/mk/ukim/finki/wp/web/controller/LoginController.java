package mk.ukim.finki.wp.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.model.User;
import mk.ukim.finki.wp.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wp.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }


    @PostMapping
    public String login(HttpServletRequest req, Model model) {
        User user = null;

        try {
            user = authService.login(req.getParameter("username"), req.getParameter("password"));
        } catch (InvalidUserCredentialsException | InvalidArgumentsException exception) {
            model.addAttribute("bodyContent", "login");
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "master-template";
        }

        req.getSession().setAttribute("user", user);
        return "redirect:/explore";
    }
}
