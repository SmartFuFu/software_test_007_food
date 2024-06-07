package org.tju.food_007.controller.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.pub.NormalResponseDTO;

import org.tju.food_007.dto.sto.UserUploadLogoImageRequestDTO;

import org.tju.food_007.service.sto.UserUploadLogoImageService;

import java.io.IOException;

/**
 * @author WGY
 * @create 2024-04-17-14:50
 */

@RestController
@RequestMapping("/api/sto")
public class UserUploadLogoImageController {
    @Autowired
    UserUploadLogoImageService userUploadLogoImageService;
    @RequestMapping("/uploadLogoImage")
    public ResponseEntity<NormalResponseDTO> StoreUploadImage(@ModelAttribute UserUploadLogoImageRequestDTO formDTO) throws IOException {
        userUploadLogoImageService.userUploadLogo(formDTO);
        NormalResponseDTO responseDTO=new NormalResponseDTO();
        responseDTO.setMsg("success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
