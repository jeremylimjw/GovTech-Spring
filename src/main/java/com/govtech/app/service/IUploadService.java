package com.govtech.app.service;

import com.govtech.app.util.exception.CsvInvalidFormatException;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    public void parseCsvAndSave(MultipartFile file) throws CsvInvalidFormatException;
}
