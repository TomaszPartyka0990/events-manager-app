package pl.sda.partyka.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.partyka.error.ErrorInfo;

import java.util.ArrayList;
import java.util.List;

public class ControllersMethods {

    static void getErrorsAddThemToAttributesAndSendRedirect(Errors validationErrors, RedirectAttributes redirectAttributes){
        List<FieldError> fieldErrors = validationErrors.getFieldErrors();
        ArrayList<ErrorInfo> errors = new ArrayList<>();
        fieldErrors.forEach(e -> errors.add(new ErrorInfo("Value " + e.getRejectedValue() + " provided in " + e.getField() + " does not meet the field requirements")));
        redirectAttributes.addFlashAttribute("errors", errors);
    }
}
