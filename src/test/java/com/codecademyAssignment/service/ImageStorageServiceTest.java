package com.codecademyAssignment.service;

import com.codecademyAssignment.TestDataHelper;
import com.codecademyAssignment.entity.ImageData;
import com.codecademyAssignment.entity.ImageDataBO;
import com.codecademyAssignment.respository.ImageStorageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ImageStorageServiceTest {

    @Mock
    private ImageStorageRepository repository;

    @InjectMocks
    private ImageStorageService service;

    @Test
    public void testUploadImage() throws IOException {
        MockMultipartFile mockFile = TestDataHelper.getMockMultipartFile();
        ImageData savedImageData = TestDataHelper.getImageData();
        when(repository.findByName(mockFile.getOriginalFilename())).thenReturn(Optional.empty());
        when(repository.save(TestDataHelper.getImageData())).thenReturn(savedImageData);

        ImageDataBO imageDataBO = service.uploadImage(mockFile);

        assertNotNull(imageDataBO);
        assertEquals(1L, imageDataBO.getId());
        assertEquals(mockFile.getOriginalFilename(), imageDataBO.getName());
    }

    @Test
    public void testGetImageById() throws Exception {
        ImageData imageData = TestDataHelper.getImageData();
        when(repository.findById(1L)).thenReturn(Optional.of(imageData));

        byte[] imageDataBytes = service.getImageById(1L);

        assertNotNull(imageDataBytes);
        assertEquals(0, imageDataBytes.length);
    }

    @Test
    public void testGetAllImages() {
        when(repository.findAll()).thenReturn(List.of(TestDataHelper.getImageData(), TestDataHelper.getImageData2()));

        List<ImageData> imageDataList = service.getAllImages();

        assertNotNull(imageDataList);
        assertEquals(2, imageDataList.size());
    }

    @Test
    public void testDeleteImageById() {
        doNothing().when(repository).deleteById(1L);

        service.deleteImageById(1L);

        verify(repository).deleteById(1L);
    }
}
