package org.tju.food_007.controller.com.uploadCom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.tju.food_007.dto.sto.*;
import org.tju.food_007.service.com.uploadCom.StoUploadCommodityService;

import java.io.IOException;
import java.text.ParseException;



/**
 * @author WGY
 * @create 2024-03-20-16:14
 */
@RestController
@RequestMapping("/api/sto")
public class StoUploadCommodityController {
    @Autowired
    private StoUploadCommodityService stoUploadCommodityService;
    @RequestMapping("/uploadImage")
    public ResponseEntity<String> StoreUploadImage(@ModelAttribute StoUploadImageRequestDTO formDTO) throws IOException {
        System.out.println(formDTO.getCom_id());
        stoUploadCommodityService.UploadCommodityImage(formDTO);
        return new ResponseEntity<>("成功上传图片", HttpStatus.OK);
    }

    @RequestMapping(value="uploadMysteryBox",method= RequestMethod.POST)
    public ResponseEntity<StoUploadRegularCommodityResponseDTO> StoreUploadMysteryBox(@RequestBody StoUploadMysteryBoxCommodityRequestDTO requestDTO){
        StoUploadRegularCommodityResponseDTO com_id=stoUploadCommodityService.UploadMysteryBoxCommodity(requestDTO);
        return new ResponseEntity<>(com_id, HttpStatus.OK);
    }

    @RequestMapping(value = "uploadRegularCommodity",method= RequestMethod.POST)
    public ResponseEntity<StoUploadMysteryCommodityResponseDTO> StoreUploadRegularCommodity(@RequestBody StoUploadRegularCommodityRequestDTO requestDTO) throws ParseException {
        StoUploadMysteryCommodityResponseDTO com_id=stoUploadCommodityService.UploadRegularCommodity(requestDTO);
        return new ResponseEntity<>(com_id, HttpStatus.OK);
    }

}
