package pl.ms.springsecurity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.ms.springsecurity.user.User;
import pl.ms.springsecurity.user.UserService;
import pl.ms.springsecurity.userrole.UserRole;


import java.util.List;

@Controller
class AdminController {
    private final UserService userService;

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adminPanel")
    public String adminPanel(Model model) {
        List<User> users = userService.findAllWithoutCurrentUser();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/adminPanel/editRoles/{id}")
    public String editUserRolesForm(@PathVariable Long id, Model model) {
        User userById = userService.findUserById(id);
        model.addAttribute(userById);
        List<UserRole> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        return "editAuthoritiesForm";
    }

    @PostMapping("/adminPanel/editRoles/{id}")
    public String editUserRoles(@PathVariable Long id, User user) {
        userService.updateRoles(user, id);
        return "redirect:/adminPanel";
    }
}
