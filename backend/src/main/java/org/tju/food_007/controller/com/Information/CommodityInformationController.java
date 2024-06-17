package org.tju.food_007.controller.com.Information;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.com.CommodityDetailDTO;
import org.tju.food_007.dto.com.CommodityStatisticsDTO;
import org.tju.food_007.dto.com.GetCommodityListRequestDTO;
import org.tju.food_007.service.com.Information.CommodityInfomationService;
import org.tju.food_007.service.com.Information.GetCommodityCategoryService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/com")
public class CommodityInformationController {

    @Autowired
    private CommodityInfomationService commodityInfomationService;

    @RequestMapping(value="/commoditydetail",method = RequestMethod.GET)
    public ResponseEntity<CommodityDetailDTO> getCommodityInfo
            (@RequestParam Integer com_ID){
        CommodityDetailDTO response = commodityInfomationService.getCommodityDetail(com_ID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/commoditylist", method = RequestMethod.GET)
    public ResponseEntity<CommodityDetailDTO[]> getCommodityList (
            @RequestParam int sto_ID,
            @RequestParam int page_size,
            @RequestParam int page_num,
            @RequestParam(required = false) String com_type, // 将com_type设置为可选参数
            @RequestParam(required = false) Double remaining_proportion
    ) {
        System.out.println("正在进行商品列表查询");
        // 创建一个GetCommodityListRequestDTO对象
        GetCommodityListRequestDTO requestDTO = new GetCommodityListRequestDTO();
        // 将请求参数设置到DTO对象中
        requestDTO.setSto_ID(sto_ID);
        requestDTO.setPage_size(page_size);
        requestDTO.setPage_num(page_num);
        requestDTO.setCom_type(com_type);
        requestDTO.setRemaining_proportion(remaining_proportion);

        List<CommodityDetailDTO> response = commodityInfomationService.getCommodityList(requestDTO);


        return new ResponseEntity<>(response.toArray(new CommodityDetailDTO[0]),HttpStatus.OK);
    }

    @RequestMapping(value="/ProductStatistics",method = RequestMethod.GET)
    public ResponseEntity<CommodityStatisticsDTO> getCommodityStatistics(@RequestParam int sto_id)
    {
        CommodityStatisticsDTO response = commodityInfomationService.getCommodityStatistics(sto_id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @RequestMapping(value="/commoditydetailbyarray",method = RequestMethod.GET)
    public ResponseEntity<List<CommodityDetailDTO>> getCommodityInfoByArray
            (@RequestParam Integer[] com_ID){
        List<CommodityDetailDTO> response =new ArrayList<>();
        for(Integer id : com_ID){
            CommodityDetailDTO tempDTO = commodityInfomationService.getCommodityDetail(id);
            response.add(tempDTO);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Autowired
    GetCommodityCategoryService getCommodityCategoryService;
    @RequestMapping("/getCategories")
    public ResponseEntity<ArrayList<String>> GetCommodityCategory(){
        ArrayList<String> list=getCommodityCategoryService.getCommodityCategory();
        return new ResponseEntity<>( list, HttpStatus.OK);

    }


}
