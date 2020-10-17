package pl.sda.partyka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.partyka.domain.Comment;
import pl.sda.partyka.domain.Event;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.dto.CommentCreateRequest;
import pl.sda.partyka.dto.EventCreateRequest;
import pl.sda.partyka.dto.EventTableView;
import pl.sda.partyka.error.ErrorInfo;
import pl.sda.partyka.service.CommentService;
import pl.sda.partyka.service.EventService;
import pl.sda.partyka.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class EventContoller {

    private final EventService eventService;
    private final UserService userService;
    private final CommentService commentService;

    @Secured("ROLE_ORGANIZER")
    @GetMapping("/addEvent")
    public String showAddingEventForm(Model model, @ModelAttribute(value = "errors") ArrayList<ErrorInfo> errorsCaught){
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

    @GetMapping("/events/{eventId}")
    public String showEventById(Model model, @PathVariable(name = "eventId") Long eventId, @RequestParam (name = "page", defaultValue = "0") Integer page){
        EventTableView event = eventService.getEventViewById(eventId);
        Event eventById = eventService.getEventById(eventId);
        model.addAttribute("eventDetails", event);
        CommentCreateRequest commentCreateRequest = new CommentCreateRequest();
        commentCreateRequest.setEventId(eventId);
        model.addAttribute("commentCreateRequest", commentCreateRequest);
        Page<Comment> comments = commentService.showAllCommentsForSpecifiedEventOrderedByCreationDateDesc(eventById, page);
        model.addAttribute("comments", comments);
        int totalPages = comments.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "single_event";
    }
}
