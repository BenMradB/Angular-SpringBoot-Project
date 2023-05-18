package com.bilel.SpringBoot_TP01.services;

import com.bilel.SpringBoot_TP01.entities.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image uploadImage(MultipartFile file) throws IOException;
    Image getImageDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    void deleteImage(Long id) ;
    Image uploadImageTeacher(MultipartFile file,Long teacherId) throws IOException;
    List<Image> getTeacherImages(Long teacherId);
    void deleteTeacherImages(Long teacherId);
}
