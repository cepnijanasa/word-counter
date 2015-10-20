package com.whatever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<byte[]> handleFormUpload(FileUploadForm uploadForm)
            throws IOException, InterruptedException {

        WordCollector collector = new WordCollector();
        for (MultipartFile file : uploadForm.getFiles()) {
            collector.addSource(file.getInputStream());
        }
        WordCollection collection = collector.collectWords();
        byte[] zipBytes = ZipWriter.createZip(collection);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, "application/zip");
        responseHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(zipBytes.length));
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "filename='words.zip'");

        return new ResponseEntity<>(zipBytes, responseHeaders, HttpStatus.CREATED);
    }

    @ExceptionHandler({IOException.class, InterruptedException.class})
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        log.error("Request: " + req.getRequestURL() + " raised " + exception);
        return new ModelAndView("error")
                .addObject("exception", exception)
                .addObject("url", req.getRequestURL());
    }
}
