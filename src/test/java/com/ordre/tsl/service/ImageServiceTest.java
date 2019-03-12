package com.ordre.tsl.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;

import static com.ordre.tsl.provider.MockProvider.createMockFile;

public class ImageServiceTest {

    private ImageService imageService;
    private static final String location = "/tmp/threesixty-loader/";

    @Before
    public void setUp() {
        this.imageService = new ImageService(location);
    }

    @Test
    public void testToVerifyThatImagesGetSortedCorrectlyByIndex() {
        MultipartFile image = createMockFile("img01");
        MultipartFile image2 = createMockFile("img02");
        MultipartFile image3 = createMockFile("img03");
        MultipartFile image4 = createMockFile("img04");

        MultipartFile[] totalImages = new MultipartFile[]{image, image2, image3, image4};

        imageService.sortImagesByIndex(totalImages);

        assertEquals(imageService.getListOfIndexedImages().get(0).getIndex(), 1);
        assertEquals(imageService.getListOfIndexedImages().get(0).getMultipartFile(), image);

        assertEquals(imageService.getListOfIndexedImages().get(1).getIndex(), 2);
        assertEquals(imageService.getListOfIndexedImages().get(1).getMultipartFile(), image2);

        assertEquals(imageService.getListOfIndexedImages().get(2).getIndex(), 3);
        assertEquals(imageService.getListOfIndexedImages().get(2).getMultipartFile(), image3);

        assertEquals(imageService.getListOfIndexedImages().get(3).getIndex(), 4);
        assertEquals(imageService.getListOfIndexedImages().get(3).getMultipartFile(), image4);
    }

}