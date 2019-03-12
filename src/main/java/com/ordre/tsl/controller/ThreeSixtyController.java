package com.ordre.tsl.controller;

import com.ordre.tsl.model.ImageFiles;
import com.ordre.tsl.service.ImageService;
import com.ordre.tsl.exception.ImageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class ThreeSixtyController {

    private static final Logger log = LoggerFactory.getLogger(ThreeSixtyController.class);

    private final ImageService imageService;

    @Value("${app.files.path}")
    private String filesPath;

    @Autowired
    public ThreeSixtyController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public String form() {
        return "form";
    }

    @PostMapping
    public String upload(@RequestParam("imageFiles") MultipartFile[] images,
                         Model model) {

        try {
            ImageFiles imageFiles = imageService.saveImages(images);

            addModelAttribute(images, model, imageFiles);
        } catch (ImageException e) {
            model.addAttribute("imageError", e.getMessage());
            log.error(e.getMessage());
        } finally {
            return "form";
        }
    }

    private void addModelAttribute(@RequestParam("imageFiles") MultipartFile[] images, Model model, ImageFiles imageFiles) {
        model.addAttribute("imageCount", images.length);

        model.addAttribute("frontImageName", imageFiles.getFront().getName());
        model.addAttribute("frontImagePosition", imageFiles.getFront().getPosition());

        model.addAttribute("backImageName", imageFiles.getBack().getName());
        model.addAttribute("backImagePosition", imageFiles.getBack().getPosition());

        model.addAttribute("leftSideImageName", imageFiles.getLeftSide().getName());
        model.addAttribute("leftSideImagePosition", imageFiles.getLeftSide().getPosition());

        model.addAttribute("rightSideImageName", imageFiles.getRightSide().getName());
        model.addAttribute("rightSidePosition", imageFiles.getRightSide().getPosition());
    }
}
