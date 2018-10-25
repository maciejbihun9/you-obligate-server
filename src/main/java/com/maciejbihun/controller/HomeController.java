package com.maciejbihun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author BHN
 */
@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home(Model model) {
        return "forward:/index.html";
    }

}
