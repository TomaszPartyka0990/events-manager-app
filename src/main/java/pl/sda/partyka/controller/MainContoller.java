package pl.sda.partyka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainContoller {

    @GetMapping(value = {"/", "main"})
    public String showMainSite(){
        return "main";
    }
}
