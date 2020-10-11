package pl.sda.partyka.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(PasswordsMissmatchException.class)
    public String PasswordsMissmatchError(PasswordsMissmatchException e, RedirectAttributes redirectAttributes){
        List<ErrorInfo> errors = new ArrayList<>();
        errors.add(new ErrorInfo(e.getMessage()));
        redirectAttributes.addFlashAttribute("errors", errors);
        return "redirect:/register";
    }
}
