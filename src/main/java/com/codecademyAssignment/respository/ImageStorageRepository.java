package com.codecademyAssignment.respository;

import com.codecademyAssignment.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageStorageRepository extends JpaRepository<ImageData,Long> {

    Optional<ImageData> findByName(String fileName);

    Optional<ImageData> findById(Long id);

    List<ImageData> findAll();

    void deleteById(Long id);
}
