package org.tju.food_007.controller.com.Information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.service.com.Information.GetCommodityCategoryService;

import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-03-24-20:45
 */
@RestController
@RequestMapping("/api/com/getCategories")
public class GetCommodityCategoryController {
    @Autowired
    GetCommodityCategoryService getCommodityCategoryService;
    @RequestMapping("")
    public ResponseEntity<ArrayList<String>> GetCommodityCategory(){
        ArrayList<String> list=getCommodityCategoryService.getCommodityCategory();
        return new ResponseEntity<>( list, HttpStatus.OK);

    }
}
