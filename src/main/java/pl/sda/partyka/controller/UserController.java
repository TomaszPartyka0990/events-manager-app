package pl.sda.partyka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.partyka.domain.Role;
import pl.sda.partyka.dto.UserCreateRequest;
import pl.sda.partyka.service.RoleService;
import pl.sda.partyka.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        model.addAttribute("userCreateRequest", userCreateRequest);
        List<Role> allRoles = roleService.getAllWithoutDefault();
        model.addAttribute("allRoles", allRoles);
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(
            @ModelAttribute(value = "userCreateRequest") @Valid UserCreateRequest userToCreate){
        userService.addUser(userToCreate);
        return "main";
    }
}
