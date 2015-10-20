package com.whatever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
            throws IOException, InterruptedException, ExecutionException {

        WordCollection collection = WordCollector.collectWords(uploadForm.getFiles());
        byte[] zipBytes = ZipWriter.createZip(collection);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, "application/zip");
        responseHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(zipBytes.length));
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "filename='words.zip'");

        return new ResponseEntity<>(zipBytes, responseHeaders, HttpStatus.CREATED);
    }

    @ExceptionHandler({IOException.class, InterruptedException.class, ExecutionException.class})
    public ModelAndView handleError(RequestEntity<String> req, Exception exception) {
        log.error("Request: " + req.getUrl() + " raised " + exception);
        return new ModelAndView("error")
                .addObject("exception", exception)
                .addObject("url", req.getUrl());
    }
}
