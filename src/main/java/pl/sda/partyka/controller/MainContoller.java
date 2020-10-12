package pl.sda.partyka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.partyka.dto.EventTableView;
import pl.sda.partyka.service.EventService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class MainContoller {

    private final EventService eventService;

    @GetMapping(value = {"/", "main"})
    public String showMainSite(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page){
        Page<EventTableView> events = eventService.showAllEventsFromClosestOne(page);
        model.addAttribute("events", events);
        int totalPages = events.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "main";
    }

    @GetMapping("/login")
    public String login(){ return "login"; }
}
