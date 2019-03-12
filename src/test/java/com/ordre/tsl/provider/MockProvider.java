package com.ordre.tsl.provider;

import com.ordre.tsl.model.Image;
import com.ordre.tsl.model.ImageFiles;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Service
public class MockProvider {

    private Image createMockImage(String imageName, int position) {
        Image image = mock(Image.class);
        when(image.getName()).thenReturn(imageName);
        when(image.getPosition()).thenReturn(position);
        return image;
    }

    public ImageFiles createMockImageFiles(String front, String back, String leftSide, String rightSide) {
        ImageFiles imageFiles = mock(ImageFiles.class);
        Image frontImage = createMockImage(front, 1);
        Image backImage = createMockImage(back, 3);
        Image leftSideImage = createMockImage(leftSide, 2);
        Image rightSideImage = createMockImage(rightSide, 4);
        when(imageFiles.getFront()).thenReturn(frontImage);
        when(imageFiles.getBack()).thenReturn(backImage);
        when(imageFiles.getLeftSide()).thenReturn(leftSideImage);
        when(imageFiles.getRightSide()).thenReturn(rightSideImage);
        return imageFiles;
    }

    public static MultipartFile createMockFile(String image) {
        MultipartFile mockImage = mock(MultipartFile.class);
        when(mockImage.getOriginalFilename()).thenReturn(image);
        return mockImage;
    }
}
