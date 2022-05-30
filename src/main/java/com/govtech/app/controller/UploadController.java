package com.govtech.app.controller;

import com.govtech.app.service.IUploadService;
import com.govtech.app.util.exception.CsvInvalidFormatException;
import com.govtech.app.util.pojo.HttpErrorResponse;
import com.govtech.app.util.pojo.HttpPostUploadResponse;
import com.govtech.app.util.pojo.HttpUploadErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private IUploadService uploadService;
    
    // HTTP POST method for uploading CSV file
    @PostMapping("/api/upload")
    public ResponseEntity<?> postUpload(@RequestParam("file") MultipartFile file) {
        try {
            uploadService.parseCsvAndSave(file);

            return ResponseEntity.ok(new HttpPostUploadResponse(1));

        } catch (CsvInvalidFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpUploadErrorResponse(ex));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpErrorResponse(ex));
        }
    }
}
