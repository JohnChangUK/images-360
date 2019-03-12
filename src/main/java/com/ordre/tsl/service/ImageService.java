package com.ordre.tsl.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ordre.tsl.exception.UploadException;
import com.ordre.tsl.model.Image;
import com.ordre.tsl.model.ImageFiles;
import com.ordre.tsl.model.IndexedImageFile;
import com.ordre.tsl.exception.ImageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    private List<IndexedImageFile> listOfIndexedImages;
    private String tmpFolder;

    @Autowired
    public ImageService(@Value("/tmp/threesixty-loader/") String tmpFolder) {
        this.tmpFolder = tmpFolder;
        this.listOfIndexedImages = new ArrayList<>();
    }

    public ImageFiles saveImages(MultipartFile[] images) throws ImageException {

        if (images.length % 4 != 0) {
            throw new ImageException("Error when attempting to upload images. " +
                    "Please upload at least 4 images, with the total divisible by 4");
        }

        try {
            saveImageToFileSystem(images);
        } catch (UploadException e) {
            log.error("Error: ", e);
        }

        return getImageFiles(images);
    }

    private ImageFiles getImageFiles(MultipartFile[] images) {
        int totalNumberOfImages = images.length;

        Image frontImage = getImage(listOfIndexedImages.get(0));
        Image backImage = totalNumberOfImages == 4 ? getImage(listOfIndexedImages.get(2)) :
                getImage(listOfIndexedImages.get((2 * (totalNumberOfImages / 4)) + 1));
        Image leftSideImage = totalNumberOfImages == 4 ? getImage(listOfIndexedImages.get(1)) :
                getImage(listOfIndexedImages.get((totalNumberOfImages / 4) + 1));
        Image rightSideImage = totalNumberOfImages == 4 ? getImage(listOfIndexedImages.get(3)) :
                getImage(listOfIndexedImages.get((3 * (totalNumberOfImages / 4)) + 1));

        return new ImageFiles(frontImage, backImage, leftSideImage, rightSideImage);
    }

    private Image getImage(IndexedImageFile indexedImageFile) {
        return new Image(indexedImageFile.getMultipartFile().getOriginalFilename(),
                indexedImageFile.getIndex());
    }

    private void saveImageToFileSystem(MultipartFile[] images) throws UploadException {
        sortImagesByIndex(images);

        try {
            for (MultipartFile file : images) {
                String fileLocation = tmpFolder + file.getOriginalFilename();
                file.transferTo(new File(fileLocation));
            }
        } catch (IOException e) {
            throw new UploadException("Error attempting to save images to file system, cause: ", e);
        }
    }

    void sortImagesByIndex(MultipartFile[] images) {
        for (int i = 0; i < images.length; i++) {
            listOfIndexedImages.add(new IndexedImageFile(images[i], i + 1));
        }

        Collections.sort(listOfIndexedImages);
    }

    List<IndexedImageFile> getListOfIndexedImages() {
        return listOfIndexedImages;
    }
}
