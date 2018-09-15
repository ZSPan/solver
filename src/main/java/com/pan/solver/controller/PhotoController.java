package com.pan.solver.controller;

import com.pan.solver.dto.PhotoDto;
import com.pan.solver.mapper.PhotoMapper;
import com.pan.solver.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/photo")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @Autowired
    public PhotoController(PhotoService photoService, PhotoMapper photoMapper) {
        this.photoService = photoService;
        this.photoMapper = photoMapper;
    }

    @PostMapping()
    public PhotoDto upload(@RequestParam("file") MultipartFile file) {
        return photoMapper.toDto(photoService.upload(file));
    }
}
