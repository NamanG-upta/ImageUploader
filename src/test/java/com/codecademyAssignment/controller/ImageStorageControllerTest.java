package com.codecademyAssignment.controller;


import com.codecademyAssignment.TestDataHelper;
import com.codecademyAssignment.entity.ImageData;
import com.codecademyAssignment.entity.ImageDataBO;
import com.codecademyAssignment.service.ImageStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ImageStorageControllerTest {
    @Mock
    private ImageStorageService service;

    @InjectMocks
    private ImageStorageController controller;

    @Test
    public void testGetAllImages() {
        List<ImageData> expectedImagesDataList = List.of(TestDataHelper.getImageData());
        when(service.getAllImages()).thenReturn(expectedImagesDataList);

        ResponseEntity<?> response = controller.getAllImages();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedImagesDataList, response.getBody());
    }

    @Test
    public void testGetImageById() throws Exception {
        when(service.getImageById(1L)).thenReturn(TestDataHelper.imageBytes);

        ResponseEntity<?> response = controller.getImageById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TestDataHelper.imageBytes, response.getBody());
    }

    @Test
    public void testUploadImage() throws IOException {
        MockMultipartFile mockFile = TestDataHelper.getMockMultipartFile();
        ImageDataBO imageDataBO = TestDataHelper.getImageDataBO();
        when(service.uploadImage(any(MockMultipartFile.class))).thenReturn(imageDataBO);

        ResponseEntity<?> response = controller.uploadImage(mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imageDataBO, response.getBody());
    }

    @Test
    public void testDeleteImage() {
        doNothing().when(service).deleteImageById(1L);

        ResponseEntity<?> response = controller.deleteImage(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Image deleted successfully", response.getBody());
    }
}
