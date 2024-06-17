package org.tju.food_007.controller.sto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.tju.food_007.dto.sto.StoCategoriesOnsaleResponseDTO;
import org.tju.food_007.dto.sto.StoinformationdetailResponseDTO;
import org.tju.food_007.service.sto.StocategoriesService;
import org.tju.food_007.service.sto.StoinformationdetailService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sto")
public class
StoinformationdetailController {

    @Autowired
    private StoinformationdetailService stoinformationdetailService;

    @RequestMapping(value ="/informationdetail",method = RequestMethod.GET)
    public ResponseEntity<List<StoinformationdetailResponseDTO>> Stoinformationdetail
            (@RequestParam Integer[] sto_ID){
        System.out.println("正在获取商家详细信息");

        List<StoinformationdetailResponseDTO>response =new ArrayList<StoinformationdetailResponseDTO>();
        for(Integer id : sto_ID){
            StoinformationdetailResponseDTO res =stoinformationdetailService.stoinformationdetail(id);
            response.add(res);
        }


        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Autowired
    private StocategoriesService stocategoriesService;

    @RequestMapping(value = "/stocategories",method = RequestMethod.GET)
    public ResponseEntity<StoCategoriesOnsaleResponseDTO> getStoCategoriesOnsale
            (@RequestParam Integer sto_ID){
        StoCategoriesOnsaleResponseDTO response =stocategoriesService.getStoCategoriesOnsale(sto_ID);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
