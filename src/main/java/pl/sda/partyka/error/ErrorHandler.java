package pl.sda.partyka.error;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({PasswordsMissmatchException.class, UserAlreadyExistsInDbException.class})
    public String PasswordsMissmatchError(Exception e, RedirectAttributes redirectAttributes){
        addErrorToRedirectAttributes(redirectAttributes, e.getMessage());
        return "redirect:/register";
    }

    @ExceptionHandler(EventCreationDateException.class)
    public String EventCreationDateError(EventCreationDateException e, RedirectAttributes redirectAttributes){
        addErrorToRedirectAttributes(redirectAttributes, e.getMessage());
        return "redirect:/addEvent";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String AccessDenied(AccessDeniedException e){
        return "access_denied";
    }

    private void addErrorToRedirectAttributes(RedirectAttributes redirectAttributes, String message) {
        List<ErrorInfo> errors = new ArrayList<>();
        errors.add(new ErrorInfo(message));
        redirectAttributes.addFlashAttribute("errors", errors);
    }
}
