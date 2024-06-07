package org.tju.food_007.controller.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.tju.food_007.service.sto.StoGetIndentListService;


import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-04-01-18:50
 */

@RestController
@RequestMapping("/api/sto")
public class StoGetIndentListController {
    @Autowired
    StoGetIndentListService stoGetIndentListService;

    @RequestMapping(value = "/getIndentList",method = RequestMethod.GET)
    public ResponseEntity<ArrayList<String>>GetIndentList
            (@RequestParam Integer sto_id){
        return new ResponseEntity<>(stoGetIndentListService.StoGetInd(sto_id), HttpStatus.OK);
    }


}
