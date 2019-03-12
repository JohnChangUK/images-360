package com.ordre.tsl.controller;

import com.ordre.tsl.exception.ImageException;
import com.ordre.tsl.model.ImageFiles;
import com.ordre.tsl.provider.MockProvider;
import com.ordre.tsl.service.ImageService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ThreeSixtyControllerTest {

    private Model model;
    private MultipartFile[] mockFiles;
    private MultipartFile[] singleMockFile;

    private MockProvider mockProvider;
    private MockMvc mockMvc;

    private static final String TEMP_DIR = "/tmp/threesixty-loader/";
    private static final String ERROR = "Error when attempting to upload images. " +
            "Please upload at least 4 images, with the total divisible by 4";

    @InjectMocks
    private ThreeSixtyController threeSixtyController;

    @Mock
    private MultipartFile mockFile;

    @Mock
    private ImageService imageService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        model = mock(Model.class);
        mockFiles = new MultipartFile[]{mockFile, mockFile, mockFile, mockFile};
        singleMockFile = new MultipartFile[]{mockFile};
        mockProvider = new MockProvider();
        mockMvc = MockMvcBuilders.standaloneSetup(threeSixtyController)
                .build();
    }

    @Test
    public void testImagesUploadedByPopulatingModel() throws ImageException {
        ImageFiles imageFiles = mockProvider.createMockImageFiles(
                "img01.jpg", "img03.jpg", "img02.jpg", "img04.jpg");

        when(imageService.saveImages(mockFiles)).thenReturn(imageFiles);
        String form = threeSixtyController.upload(mockFiles, model);

        assertEquals("form successfully uploaded onto", "form", form);
        verify(model).addAttribute("imageCount", 4);

        verify(model).addAttribute("frontImageName", "img01.jpg");
        verify(model).addAttribute("frontImagePosition", 1);

        verify(model).addAttribute("backImageName", "img03.jpg");
        verify(model).addAttribute("backImagePosition", 3);

        verify(model).addAttribute("leftSideImageName", "img02.jpg");
        verify(model).addAttribute("leftSideImagePosition", 2);

        verify(model).addAttribute("rightSideImageName", "img04.jpg");
        verify(model).addAttribute("rightSidePosition", 4);

        verify(model, never()).addAttribute("imageError", ERROR);
    }

    @Test
    public void testImageExceptionThrownWhenProvidingInvalidNumberOfImages() throws ImageException {
        ImageService imageService = new ImageService(TEMP_DIR);

        expectedException.expect(ImageException.class);
        expectedException.expectMessage(ERROR);

        imageService.saveImages(singleMockFile);
        verify(model).addAttribute("imageError", ERROR);
    }

    @Test
    public void testWhenMakingPostRequestStatusIsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testWhenMakingGetRequestStatusIsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}