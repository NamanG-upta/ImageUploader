package com.codecademyAssignment;

import com.codecademyAssignment.entity.ImageData;
import com.codecademyAssignment.entity.ImageDataBO;
import org.springframework.mock.web.MockMultipartFile;

public class TestDataHelper {
    public static final byte[] imageBytes = new byte[0];

    public static ImageData getImageData() {
        return ImageData.builder().name("test1.png").type("image/png").imageData(imageBytes).build();
    }

    public static ImageData getImageData2() {
        return ImageData.builder().name("test2.png").type("image/png").imageData(imageBytes).build();
    }

    public static MockMultipartFile getMockMultipartFile() {
        return new MockMultipartFile("file", "filename.txt", "text/plain", "some_xml".getBytes());
    }

    public static ImageDataBO getImageDataBO() {
        return ImageDataBO.builder()
                .id(1L)
                .name("test1.png")
                .build();
    }
}
