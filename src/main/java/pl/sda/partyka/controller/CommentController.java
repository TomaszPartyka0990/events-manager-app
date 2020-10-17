package pl.sda.partyka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.partyka.domain.Event;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.dto.CommentCreateRequest;
import pl.sda.partyka.service.CommentService;
import pl.sda.partyka.service.EventService;
import pl.sda.partyka.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final EventService eventService;

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute (value = "commentCreateRequest") @Valid CommentCreateRequest commentCreateRequest,
                             Errors validationErrors, RedirectAttributes redirectAttributes, Principal principal){
        if (validationErrors.hasErrors()){
            ControllersMethods.getErrorsAddThemToAttributes(validationErrors, redirectAttributes);
            return "redirect:/events/" + commentCreateRequest.getEventId();
        }
        User author = userService.getUserByLogin(principal.getName());
        author.getCreatedComments().forEach(comment -> System.out.println(comment.getContent()));
        Event event = eventService.getEventById(commentCreateRequest.getEventId());
        commentService.addNewComment(commentCreateRequest, author, event);
        return "redirect:/events/" + commentCreateRequest.getEventId();
    }
}
