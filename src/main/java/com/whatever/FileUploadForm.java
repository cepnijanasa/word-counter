package com.whatever;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUploadForm {
    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<MultipartFile> files;
}
