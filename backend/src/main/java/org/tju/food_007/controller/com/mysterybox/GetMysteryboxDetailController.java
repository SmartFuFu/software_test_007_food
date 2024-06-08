package org.tju.food_007.controller.com.mysterybox;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.com.mysterybox.GetMysteryboxRequestDTO;
import org.tju.food_007.dto.com.mysterybox.GetMysteryboxResponseDTO;
import org.tju.food_007.service.com.mysterybox.GetMysteryboxDetailService;

import java.util.List;

@RestController
@RequestMapping("/api/mys")
public class GetMysteryboxDetailController {
    @Autowired
    GetMysteryboxDetailService getMysteryboxDetailService;

    @RequestMapping(value="/getmysterybox",method = RequestMethod.GET)
    public ResponseEntity<GetMysteryboxResponseDTO[]> getMysteryboxDetail
            (@RequestParam(required = false) Integer mystery_box_ID,
             @RequestParam(required = false) Integer sto_ID,
             @RequestParam(required = false) Integer page_size,
             @RequestParam(required = false) Integer page_num,
             @RequestParam(required = false) Integer sort_by,
             @RequestParam(required = false) Integer sort_order){
        GetMysteryboxRequestDTO request =new GetMysteryboxRequestDTO();
        request.setMystery_box_ID(mystery_box_ID);
        request.setSto_ID(sto_ID);
        request.setPage_num(page_num);
        request.setPage_size(page_size);
        request.setSort_order(sort_order);
        request.setSort_by(sort_by);
        List<GetMysteryboxResponseDTO> response =getMysteryboxDetailService.getMysteryboxList(request);
        return new ResponseEntity<>
                (response.toArray(response.toArray(new GetMysteryboxResponseDTO[0])), HttpStatus.OK);
    }
}
