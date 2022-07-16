package pl.ms.springsecurity.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.ms.springsecurity.user.dto.UserEditDto;

import java.util.Optional;


@Controller
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userPanel")
    public String userEditForm(Model model) {
        UserEditDto currentUserEditDto = userService.getCurrentUserEditDto().orElseThrow();
        model.addAttribute("user", currentUserEditDto);
        return "userEditForm";
    }

    @PostMapping("/userPanel/edit/{id}")
    public String updateUserData(UserEditDto dto) {
        userService.updateUser(dto);
        return "redirect:/logout";
    }
}
