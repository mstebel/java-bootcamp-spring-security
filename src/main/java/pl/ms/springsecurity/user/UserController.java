package pl.ms.springsecurity.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userPanel")
    public String userEditForm(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "userEditForm";
    }

    @PostMapping("/userPanel/edit/{id}")
    public String updateUserData(@PathVariable Long id, User user) {
        userService.updateUser(user, id);
        return "redirect:/logout";
    }
}
