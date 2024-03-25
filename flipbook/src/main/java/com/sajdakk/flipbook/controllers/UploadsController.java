package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController("/uploads")
public class UploadsController {

    UploadService uploadService;

    @Autowired
    public UploadsController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("/uploads/{fileName}")
    public byte[] getUpload(@PathVariable("fileName") String fileName) {
        if (fileName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File name must be provided");
        }

        if (fileName.contains("/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File name cannot contain '/'");
        }

        return uploadService.getFile(fileName);
    }


}
