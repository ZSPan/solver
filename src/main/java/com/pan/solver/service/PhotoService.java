package com.pan.solver.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    void upload(MultipartFile file);

}
