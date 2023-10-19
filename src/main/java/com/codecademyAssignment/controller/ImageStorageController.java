package com.codecademyAssignment.controller;

import com.codecademyAssignment.entity.ImageDataBO;
import com.codecademyAssignment.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@RequestMapping("/image")
public class ImageStorageController {
    @Autowired
    private ImageStorageService service;

    /**
     * Retrieve all images.
     * @return ResponseEntity with the list of all images or an error message.
     */
    @GetMapping
    public ResponseEntity<?> getAllImages() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.getAllImages());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
        }
    }

    /**
     * Retrieve an image by its ID.
     * @param id The ID of the image to be retrieved.
     * @return ResponseEntity with the image data or an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        try {
            byte[] imageData = service.getImageById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found");
        }
    }

    /**
     * Upload an image.
     * @param file The image file to be uploaded.
     * @return ResponseEntity with the details of the uploaded image or an error message.
     */
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            ImageDataBO response = service.uploadImage(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image");
        }
    }

    /**
     * Delete an image by its ID.
     * @param id The ID of the image to be deleted.
     * @return ResponseEntity with a success message or an error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        try {
            service.deleteImageById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found");
        }
    }
}
