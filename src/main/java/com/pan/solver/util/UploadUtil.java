package com.pan.solver.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UploadUtil {

    String uploadFile(MultipartFile multipartFile) throws Exception;

    String uploadFile(String filePath, MultipartFile multipartFile) throws Exception;

    String uploadFile(MultipartFile multipartFile, String fileName) throws Exception;

    String uploadFile(MultipartFile multipartFile, String fileName, String filePath) throws Exception;

    String uploadFile(File file) throws Exception;

    String uploadFile(String filePath, File file) throws Exception;

    String uploadFile(File file, String fileName) throws Exception;

    String uploadFile(File file, String fileName, String filePath) throws Exception;

    String uploadFile(byte[] data) throws Exception;

    String uploadFile(String filePath, byte[] data) throws Exception;

    String uploadFile(byte[] data, String fileName) throws Exception;

    String uploadFile(byte[] data, String fileName, String filePath) throws Exception;

}
