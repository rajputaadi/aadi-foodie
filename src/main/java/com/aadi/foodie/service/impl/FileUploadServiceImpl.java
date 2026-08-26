package com.aadi.foodie.service.impl;

import com.aadi.foodie.dto.FileData;
import com.aadi.foodie.exception.InvalidFilePathException;
import com.aadi.foodie.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Override
    public FileData uploadFile(MultipartFile file, String path) throws IOException {

    }
}
