package org.tju.food_007.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tju.food_007.dto.pub.GetStoreNearByRequestDTO;
import org.tju.food_007.dto.pub.GetStoreNearByResponseDTO;
import org.tju.food_007.service.pub.GetStoreNearByService;

/**
 * @author WGY
 * @create 2024-03-10-15:01
 */
@RestController
@RequestMapping("/api/pub/map")
public class GetStoreNearByController {
    @Autowired
    GetStoreNearByService getStoreNearByService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<GetStoreNearByResponseDTO> GetNearBySto(@ModelAttribute GetStoreNearByRequestDTO requestDTO) {
        GetStoreNearByResponseDTO response=getStoreNearByService.GetStoNearBy(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


