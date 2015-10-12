package com.whatever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
    public void handleFormUpload(HttpServletResponse response, FileUploadForm uploadForm) {
        try {
            WordCollector collector = new WordCollector();
            for (MultipartFile file : uploadForm.getFiles()) {
                collector.addSource(file.getInputStream());
            }
            WordCollection container = collector.collectWords();

            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "filename='words.zip'");

            ZipWriter.putWordsInZip(response.getOutputStream(), container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
