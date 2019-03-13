package com.ordre.tsl.service;

import com.ordre.tsl.exception.ImageException;
import com.ordre.tsl.model.ImageFiles;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import static com.ordre.tsl.provider.MockProvider.createMockFile;

public class ImageServiceTest {

    private ImageService imageService;
    private static final String location = "/tmp/threesixty-loader/";

    private MultipartFile frontImage;
    private MultipartFile image2;
    private MultipartFile leftSideImage;
    private MultipartFile image4;
    private MultipartFile[] totalImages;

    @Before
    public void setUp() {
        this.imageService = new ImageService(location);
        frontImage = createMockFile("img01");
        image2 = createMockFile("img02");
        leftSideImage = createMockFile("img03");
        image4 = createMockFile("img04");
        totalImages = new MultipartFile[]{frontImage, image2, leftSideImage, image4};
    }

    @Test
    public void testToVerifyThatImagesGetSortedCorrectlyByIndex() {
        imageService.sortImagesByIndex(totalImages);

        assertEquals(imageService.getListOfIndexedImages().get(0).getIndex(), 1);
        assertEquals(imageService.getListOfIndexedImages().get(0).getMultipartFile(), frontImage);

        assertEquals(imageService.getListOfIndexedImages().get(1).getIndex(), 2);
        assertEquals(imageService.getListOfIndexedImages().get(1).getMultipartFile(), image2);

        assertEquals(imageService.getListOfIndexedImages().get(2).getIndex(), 3);
        assertEquals(imageService.getListOfIndexedImages().get(2).getMultipartFile(), leftSideImage);

        assertEquals(imageService.getListOfIndexedImages().get(3).getIndex(), 4);
        assertEquals(imageService.getListOfIndexedImages().get(3).getMultipartFile(), image4);
    }

    /**
     * When inputting 8 images, the images uploaded should correspond to
     * indexes 1, 3, 5, 7
     */
    @Test
    public void testUploadImagesCorrespondingToCalculatedIndex() throws ImageException {
        MultipartFile backImage = createMockFile("img05");
        MultipartFile image6 = createMockFile("img06");
        MultipartFile rightSideImage = createMockFile("img07");
        MultipartFile image8 = createMockFile("img08");

        MultipartFile[] listOfImages = concat(totalImages, new MultipartFile[]{backImage, image6, rightSideImage, image8});

        ImageFiles sortedImages = imageService.saveImages(listOfImages);

        assertEquals(sortedImages.getFront().getName(), frontImage.getOriginalFilename());
        assertEquals(sortedImages.getFront().getPosition(), 1);

        assertEquals(sortedImages.getBack().getName(), backImage.getOriginalFilename());
        assertEquals(sortedImages.getBack().getPosition(), 5);

        assertEquals(sortedImages.getLeftSide().getName(), leftSideImage.getOriginalFilename());
        assertEquals(sortedImages.getLeftSide().getPosition(), 3);

        assertEquals(sortedImages.getRightSide().getName(), rightSideImage.getOriginalFilename());
        assertEquals(sortedImages.getRightSide().getPosition(), 7);
    }

    @Test
    public void testSaveToTempDirectory() {
        imageService.sortImagesByIndex(totalImages);

    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] concatenatedArray = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, concatenatedArray, first.length, second.length);
        return concatenatedArray;
    }

}