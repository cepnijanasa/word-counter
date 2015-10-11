package com.whatever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

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
        Thread thread;
        List<Thread> threadList = new ArrayList<>();
        WordContainer container = new WordContainer();
        try{
            for (MultipartFile file : uploadForm.getFiles()) {
                thread = new Thread(new WordCounter(file.getInputStream(), container));
                threadList.add(thread);
                thread.start();
            }

            for (Thread t: threadList) {
                t.join();
            }
        } catch (IOException | InterruptedException e){
            log.error("", e);
        }

        // TODO

        return "input";
    }
}
