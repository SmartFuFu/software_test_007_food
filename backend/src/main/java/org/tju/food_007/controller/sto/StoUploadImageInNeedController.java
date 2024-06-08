package org.tju.food_007.controller.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.pub.NormalResponseDTO;
import org.tju.food_007.dto.sto.StoUploadImageInNeedRequestDTO;

import org.tju.food_007.service.sto.StoUploadImageInNeedService;

import java.io.IOException;

/**
 * @author WGY
 * @create 2024-04-17-14:20
 */
@RestController
@RequestMapping("/api/sto")
public class StoUploadImageInNeedController {
    @Autowired
    StoUploadImageInNeedService stoUploadImageInNeedService;
    @RequestMapping("/uploadStoImage")
    public ResponseEntity<NormalResponseDTO> StoreUploadImage(@ModelAttribute StoUploadImageInNeedRequestDTO formDTO) throws IOException {
        stoUploadImageInNeedService.UploadStoImage(formDTO);
        NormalResponseDTO responseDTO=new NormalResponseDTO();
        responseDTO.setMsg("success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping("/uploadStoLicense")
    public ResponseEntity<NormalResponseDTO> StoreUploadLicenseImage(@ModelAttribute StoUploadImageInNeedRequestDTO formDTO) throws IOException {
        stoUploadImageInNeedService.UploadStoLicenseImage(formDTO);
        NormalResponseDTO responseDTO=new NormalResponseDTO();
        responseDTO.setMsg("success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
