package com.whatever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WordCounterController {

    Logger log = LoggerFactory.getLogger(WordCounterController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showInputForm() {
        FileUploadForm fileUploadForm = new FileUploadForm();
        return new ModelAndView("input")
                .addObject("fileUploadForm", fileUploadForm);
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String handleFormUpload(FileUploadForm uploadForm) {
        // TODO
        return "input";
    }
}
