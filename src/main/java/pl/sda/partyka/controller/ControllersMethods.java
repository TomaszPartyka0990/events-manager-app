package pl.sda.partyka.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.partyka.dto.EventTableView;
import pl.sda.partyka.dto.EventsSearchOptions;
import pl.sda.partyka.error.ErrorInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ControllersMethods {

    static void getErrorsAddThemToAttributes(Errors validationErrors, RedirectAttributes redirectAttributes){
        List<FieldError> fieldErrors = validationErrors.getFieldErrors();
        ArrayList<ErrorInfo> errors = new ArrayList<>();
        fieldErrors.forEach(e -> errors.add(new ErrorInfo("Value " + e.getRejectedValue() + " provided in " + e.getField() + " does not meet the field requirements")));
        redirectAttributes.addFlashAttribute("errors", errors);
    }

    static void addEventsAndSearchOptionsAndPageNumbersToModelAttributes(Model model, Page<EventTableView> events){
        model.addAttribute("events", events);
        model.addAttribute("eventSearchOptions", EventsSearchOptions.values());
        int totalPages = events.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
