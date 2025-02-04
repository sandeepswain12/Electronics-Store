package com.sd.electronicstore.ElectronicStore.services.impl;

import com.sd.electronicstore.ElectronicStore.exceptions.BadApiRequestException;
import com.sd.electronicstore.ElectronicStore.services.FileService;
import com.sd.electronicstore.ElectronicStore.validate.ImageNameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = (Logger) LoggerFactory.getLogger(ImageNameValidator.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("Filename : {}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileWithExtension = filename + extension;
        String fullPathWithFileName = path + fileWithExtension;

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

            return fileWithExtension;
        } else {
            throw new BadApiRequestException("File with this "+extension+" not allowed");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path+File.separator+name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
