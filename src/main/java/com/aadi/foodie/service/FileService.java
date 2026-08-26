package com.aadi.foodie.service;

import com.aadi.foodie.dto.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileData uploadFile(MultipartFile file, String path) throws IOException;
}
