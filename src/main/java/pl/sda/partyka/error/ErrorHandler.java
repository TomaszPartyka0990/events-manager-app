package pl.sda.partyka.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({PasswordsMissmatchException.class, UserAlreadyExistsInDbException.class})
    public String PasswordsMissmatchError(Exception e, RedirectAttributes redirectAttributes){
        return addErrorToRedirectAttributesAndRedirectToRegisterContoller(redirectAttributes, e.getMessage());
    }

    private String addErrorToRedirectAttributesAndRedirectToRegisterContoller(RedirectAttributes redirectAttributes, String message) {
        List<ErrorInfo> errors = new ArrayList<>();
        errors.add(new ErrorInfo(message));
        redirectAttributes.addFlashAttribute("errors", errors);
        return "redirect:/register";
    }
}
