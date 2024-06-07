package org.tju.food_007.controller.cus.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.tju.food_007.dto.cus.info.GetCusInfoResponseDTO;

import org.tju.food_007.service.cus.info.GetCusInfoService;

/**
 * @author WGY
 * @create 2024-04-01-18:06
 */
@RestController
@RequestMapping("/api/cus")
public class GetCusInfoController {
    @Autowired
    GetCusInfoService getCusInfoService;
    @RequestMapping(value = "getInfo",method = RequestMethod.GET)
    public ResponseEntity<GetCusInfoResponseDTO> GetCusInfo(@RequestParam Integer cus_id){
        return new ResponseEntity<>(getCusInfoService.GetCusInfo(cus_id), HttpStatus.OK);
    }
}


