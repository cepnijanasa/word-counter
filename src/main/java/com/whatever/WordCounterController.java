package com.whatever;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WordCounterController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showInputForm() {
        return "input";
    }
}
