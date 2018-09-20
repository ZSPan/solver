package com.pan.solver.service.impl;

import com.pan.solver.data.Photo;
import com.pan.solver.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    private final String photoDirectory;

    @Autowired
    public PhotoServiceImpl() {
        this.photoDirectory = System.getProperty("user.home");
    }

    @Override
    public Photo upload(MultipartFile file) {
        String originFileName = file.getOriginalFilename();
        String suffix = originFileName.substring(originFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        File destFile = new File(photoDirectory + File.separatorChar + fileName);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(destFile);
            Photo photo = new Photo();
            photo.setFileName(fileName);
            return photo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}