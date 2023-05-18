package com.bilel.SpringBoot_TP01.repos;

import com.bilel.SpringBoot_TP01.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepo extends JpaRepository<Image, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.teacher.teacherId = ?1")
    void deleteTeacherTeacherId(Long teacherId);
}