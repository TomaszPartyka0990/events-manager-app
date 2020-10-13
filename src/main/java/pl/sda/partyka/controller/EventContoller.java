package pl.sda.partyka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.dto.EventCreateRequest;
import pl.sda.partyka.dto.EventTableView;
import pl.sda.partyka.error.ErrorInfo;
import pl.sda.partyka.service.EventService;
import pl.sda.partyka.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class EventContoller {

    private final EventService eventService;
    private final UserService userService;

    @Secured("ROLE_ORGANIZER")
    @GetMapping("/addEvent")
    public String shoeAddingEventForm(Model model, @ModelAttribute(value = "errors") ArrayList<ErrorInfo> errorsCaught){
        ArrayList<ErrorInfo> errors = new ArrayList<>(errorsCaught);
        model.addAttribute("errors", errors);
        EventCreateRequest eventCreateRequest = new EventCreateRequest();
        model.addAttribute("eventCreateRequest", eventCreateRequest);
        return "add_event";
    }

    @Secured("ROLE_ORGANIZER")
    @PostMapping("/addEvent")
    public String addNewEvent(
            @ModelAttribute(value = "eventCreateRequest") @Valid EventCreateRequest eventToCreate, Errors validationErrors, RedirectAttributes redirectAttributes, Principal principal){
        if (validationErrors.hasErrors()){
            ControllersMethods.getErrorsAddThemToAttributes(validationErrors, redirectAttributes);
            return "redirect:/addEvent";
        }
        User author = userService.getUserByLogin(principal.getName());
        eventService.addEvent(eventToCreate, author);
        return "redirect:/main";
    }

    @GetMapping("/events")
    public String showEvents(Model model,
                             @RequestParam(name = "title", defaultValue = "") String title,
                             @RequestParam(name = "range", defaultValue = "1") Integer range,
                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page){
        Page<EventTableView> events = eventService.searchForEventsByTitleSortedAscendingByStartingDate(title, range, page);
        model.addAttribute("title", title);
        model.addAttribute("range", range);
        ControllersMethods.addEventsAndSearchOptionsAndPageNumbersToModelAttributes(model, events);
        return "events";
    }
}
