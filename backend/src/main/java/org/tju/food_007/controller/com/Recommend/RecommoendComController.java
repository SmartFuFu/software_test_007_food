package org.tju.food_007.controller.com.Recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.com.CommodityDetailDTO;
import org.tju.food_007.service.com.Information.CommodityInfomationService;
import org.tju.food_007.service.com.Recommend.RecommendComService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/com")
public class RecommoendComController {

    @Autowired
    RecommendComService recommendComService;

    @Autowired
    CommodityInfomationService commodityInfomationService;

    @RequestMapping(value = "/recommendComDetail",method = RequestMethod.GET)
    public ResponseEntity<List<CommodityDetailDTO>> recommendCommodity(@RequestParam(required = false) Integer user_ID,
                                                     @RequestParam(required = false) Integer com_ID,
                                                        @RequestParam Integer page_size,
                                                        @RequestParam Integer page_num)
    {
        List<Integer> responseID=new ArrayList<>();
        if(com_ID!=null){
            responseID =recommendComService.recommendCommodityByCom(com_ID,page_size,page_num);
        }
        if(user_ID!=null){
            responseID=recommendComService.recommendCommodityByUser(user_ID,page_size,page_num);
        }

        List<CommodityDetailDTO>response=new ArrayList<>();
        for(Integer id:responseID){
            CommodityDetailDTO tempDTO = commodityInfomationService.getCommodityDetail(id);
            response.add(tempDTO);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);    }
}
