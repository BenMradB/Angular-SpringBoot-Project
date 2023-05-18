package com.bilel.SpringBoot_TP01.restcontrollers;

import com.bilel.SpringBoot_TP01.entities.Image;
import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.services.ImageService;
import com.bilel.SpringBoot_TP01.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin
public class ImageRestController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping( "/uploadTeacherImage/{teacherId}" )
    public Image uploadMultiImages(
            @RequestParam("image")MultipartFile file,
            @PathVariable("teacherId") Long teacherId
    ) throws IOException {
        return this.imageService.uploadImageTeacher(file, teacherId);
    }

    @GetMapping("/getTeacherImages/{teacherId}")
    public List<Image> getTeacherImages(@PathVariable("teacherId") Long teacherId) throws IOException {
        return this.imageService.getTeacherImages(teacherId);
    }

    @PostMapping("/upload")
    public Image uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return this.imageService.uploadImage(file);
    }

    @GetMapping("/get/info/{id}")
    public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
        return this.imageService.getImageDetails(id);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<byte[]> loadImage(@PathVariable("id") Long id) throws IOException {
        return this.imageService.getImage(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable("id") Long id) {
        this.imageService.deleteImage(id);
    }

    @PatchMapping("/update")
    public Image updateImage(@RequestParam("image") MultipartFile file) throws IOException {
        return this.imageService.uploadImage(file);
    }

//    @PostMapping("/uploadFS/{teacherId}")
//    public void uploadImageFS(@RequestParam("image") MultipartFile file, @PathVariable("teacherId") Long teacherId) throws IOException {
//        Teacher teacher = this.teacherService.getTeacher(teacherId);
//        teacher.setImagePath(teacherId + ".jpg");
//
//        Files.write(
//                Paths.get(
//                        System.getProperty("user.home") + "/images/" + teacher.getImagePath()
//                ),
//                file.getBytes()
//        );
//
//        this.teacherService.createTeacher(teacher);
//    }
//    @GetMapping(value ="/loadFromFS/{teacherId}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] getImageFS(@PathVariable("teacherId") Long teacherId) throws IOException {
//
//        Teacher teacher = this.teacherService.getTeacher(teacherId);
//        return Files.readAllBytes(
//                Paths.get(
//                        System.getProperty("user.home") + "/images/" + teacher.getImagePath()
//                )
//        );
//    }

}
