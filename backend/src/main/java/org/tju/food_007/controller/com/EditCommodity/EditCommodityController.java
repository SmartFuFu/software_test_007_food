package org.tju.food_007.controller.com.EditCommodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tju.food_007.dto.com.EditCommodity.EditCommodityRequestDTO;
import org.tju.food_007.dto.com.EditCommodity.EditCommodityResponseDTO;
import org.tju.food_007.service.com.EditCommodity.EditCommodityService;

@RestController
@RequestMapping("/api/com")
public class EditCommodityController {

    @Autowired
    EditCommodityService editCommodityService;

    @RequestMapping(value="/editCommodity",method = RequestMethod.POST)
    public ResponseEntity<EditCommodityResponseDTO> getCommodityStatistics
            (@RequestBody EditCommodityRequestDTO request)
    {
        EditCommodityResponseDTO response=editCommodityService.editCommodity(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
