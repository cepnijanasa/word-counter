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
import java.util.*;
import java.util.zip.ZipOutputStream;

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
        ZipOutputStream zos = null;
        try {
            Thread thread;
            List<Thread> threadList = new ArrayList<>();
            WordContainer container = new WordContainer();
            for (MultipartFile file : uploadForm.getFiles()) {
                thread = new Thread(new WordCounter(file.getInputStream(), container));
                threadList.add(thread);
            }
            for (Thread t : threadList) {
                t.start();
                t.join();
            }

            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "filename='words.zip'");
            zos = new ZipOutputStream(new BufferedOutputStream(
                    response.getOutputStream()));

            ZipWriter.createZipEntry(zos, container.getWordsAG(), "A_G.txt");
            ZipWriter.createZipEntry(zos, container.getWordsHN(), "H_N.txt");
            ZipWriter.createZipEntry(zos, container.getWordsOU(), "O_U.txt");
            ZipWriter.createZipEntry(zos, container.getWordsVZ(), "V_Z.txt");
        } catch (InterruptedException | IOException e) {
            log.error("", e);
            // TODO
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                //silent
            }
        }
    }
}
