package pl.sda.partyka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.partyka.domain.Role;
import pl.sda.partyka.dto.UserCreateRequest;
import pl.sda.partyka.error.ErrorInfo;
import pl.sda.partyka.service.RoleService;
import pl.sda.partyka.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/register")
    public String showRegisterForm(Model model, @ModelAttribute(value = "errors") ArrayList<ErrorInfo> errorsCaught){
        ArrayList<ErrorInfo> errors = new ArrayList<>(errorsCaught);
        model.addAttribute("errors", errors);
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        model.addAttribute("userCreateRequest", userCreateRequest);
        List<Role> allRoles = roleService.getAllWithoutDefault();
        model.addAttribute("allRoles", allRoles);
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(
            @ModelAttribute(value = "userCreateRequest") @Valid UserCreateRequest userToCreate, Errors validationErrors, RedirectAttributes redirectAttributes){
        if (validationErrors.hasErrors()){
            ControllersMethods.getErrorsAddThemToAttributesAndSendRedirect(validationErrors, redirectAttributes);
            return "redirect:/register";
        }
        userService.addUser(userToCreate);
        return "main";
    }
}
