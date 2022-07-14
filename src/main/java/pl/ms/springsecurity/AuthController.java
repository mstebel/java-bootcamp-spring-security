package pl.ms.springsecurity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ms.springsecurity.user.UserService;
import pl.ms.springsecurity.user.dto.UserRegistrationDto;

@Controller
class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String username, Model model) {
        model.addAttribute("username", username);
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        UserRegistrationDto user = new UserRegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/register")
    public String register(UserRegistrationDto dto) {
        userService.register(dto);
        return "redirect:/login?username=" + dto.getUsername();
    }

    @GetMapping("/logoutSuccess")
    public String logoutUser() {
        return "logoutSuccess";
    }
}
