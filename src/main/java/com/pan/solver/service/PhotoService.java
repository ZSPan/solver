package com.pan.solver.service;

import com.pan.solver.data.Photo;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    Photo upload(MultipartFile file);

}
