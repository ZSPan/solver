package com.pan.solver.service.impl;

import com.pan.solver.entity.Photo;
import com.pan.solver.repository.PhotoRepository;
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

    private PhotoRepository photoRepository;
    private String filePath = "E://photo/";

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public void upload(MultipartFile file) {
        String originFileName = file.getOriginalFilename();
        String suffix = originFileName.substring(originFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        File destFile = new File(filePath + fileName);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(destFile);
            Photo photo = new Photo();
            photo.setPhotoName(fileName);
            photoRepository.save(photo);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Upload file failed!");
        }

    }

}
