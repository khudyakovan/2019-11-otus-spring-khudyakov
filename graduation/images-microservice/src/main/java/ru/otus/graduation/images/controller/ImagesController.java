package ru.otus.graduation.images.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.graduation.service.master.ImageService;

@RestController
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesController.class);
    private static final String ERROR_NOT_FOUND = "Image with Id=%s not found!";

    @RequestMapping(value = "/images/{number}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("number") String number) {

//        var imageFile = new ClassPathResource(String.format("images/plu_%s_190.jpg", number));
//        byte[] bytes = new byte[0];
//        try {
//            bytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
//        } catch (IOException e) {
//            LOGGER.error(String.format(ERROR_NOT_FOUND, number));
//        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.findByProductId(number).getFile().getData());
    }
}
