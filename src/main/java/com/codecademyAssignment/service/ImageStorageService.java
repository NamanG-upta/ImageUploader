package com.codecademyAssignment.service;

import com.codecademyAssignment.entity.ImageData;
import com.codecademyAssignment.entity.ImageDataBO;
import com.codecademyAssignment.respository.ImageStorageRepository;
import com.codecademyAssignment.util.ImageUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageStorageService {
    @Autowired
    private ImageStorageRepository repository;

    public ImageDataBO uploadImage(MultipartFile file) throws IOException {
        Optional<ImageData> imageDataOptional = repository.findByName(file.getOriginalFilename());

        ImageData imageData;

        if (imageDataOptional.isPresent()) {
            imageData = imageDataOptional.get();
            imageData.setImageData(ImageUtils.compressImage(file.getBytes()));
        } else {
            imageData = ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes()))
                    .build();
        }

        ImageData savedImageData = repository.save(imageData);

        return ImageDataBO.builder()
                .id(savedImageData.getId())
                .name(savedImageData.getName())
                .build();
    }

    public byte[] getImageById(@NonNull final Long id) throws Exception {
        Optional<ImageData> dbImageData = repository.findById(id);
        return ImageUtils.decompressImage(dbImageData.get().getImageData());
    }

    public List<ImageData> getAllImages() {
        return repository.findAll();
    }

    public void deleteImageById(Long id) {
        repository.deleteById(id);
    }
}
