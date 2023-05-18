package com.bilel.SpringBoot_TP01.services.implementations;

import com.bilel.SpringBoot_TP01.entities.Image;
import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.repos.ImageRepo;
import com.bilel.SpringBoot_TP01.services.ImageService;
import com.bilel.SpringBoot_TP01.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private TeacherService teacherService;
    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
        return this.imageRepo.save(
                Image.builder()
                        .imageName(file.getOriginalFilename())
                        .imageType(file.getContentType())
                        .image(file.getBytes())
                        .build()
        );
    }

    @Override
    public Image getImageDetails(Long id) throws IOException {
        final Optional<Image> image = this.imageRepo.findById(id);
        return Image.builder()
                .imageId(image.get().getImageId())
                .imageName(image.get().getImageName())
                .imageType(image.get().getImageType())
                .image(image.get().getImage())
                .build();
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        final Optional<Image> image = this.imageRepo.findById(id);
        return ResponseEntity.ok()
                .contentType(
                        MediaType.valueOf(
                                image.get().getImageType()
                        )
                )
                .body(
                        image.get().getImage()
                );
    }

    @Override
    public void deleteImage(Long id) {
        this.imageRepo.deleteById(id);
    }

    @Override
    public Image uploadImageTeacher(MultipartFile file, Long teacherId) throws IOException {
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);
        return this.imageRepo.save(
                Image.builder()
                        .imageName(file.getOriginalFilename())
                        .imageType(file.getContentType())
                        .image(file.getBytes())
                        .teacher(teacher)
                        .build()
        );
    }



    @Override
    public List<Image> getTeacherImages(Long teacherId) {
        Teacher teacher = this.teacherService.getTeacher(teacherId);
        return teacher.getImages();
    }

    @Override
    public void deleteTeacherImages(Long teacherId) {
        this.imageRepo.deleteTeacherTeacherId(teacherId);;
    }
}
