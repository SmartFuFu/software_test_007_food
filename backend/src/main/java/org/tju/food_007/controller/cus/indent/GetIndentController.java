package org.tju.food_007.controller.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tju.food_007.dto.cus.indent.GetIndentResponseDTO;
import org.tju.food_007.service.cus.indent.GetIndentService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.cus.indent.GetIndRequestDTO;
import org.tju.food_007.dto.cus.indent.GetIndResponseDTO;

import java.util.ArrayList;
import java.util.List;
/**
 * @author WGY
 * @create 2024-03-17-21:35
 */
@RestController
@RequestMapping("/api/cus")
public class GetIndentController {

    @Autowired
    GetIndentService getIndentService;

    @RequestMapping(value = "/getInd",method = RequestMethod.GET)
    public ResponseEntity<GetIndResponseDTO[]> getIndByUserID(@RequestParam int cus_ID,
                                                                 @RequestParam(required = false)Integer state){

        GetIndRequestDTO request = new GetIndRequestDTO();
        request.setCus_ID(cus_ID);
        request.setState(state);
        List<GetIndResponseDTO> response = getIndentService.getIndbyUserID(request);

        return new ResponseEntity<>(response.toArray(new GetIndResponseDTO[0]), HttpStatus.OK);
    }


    @GetMapping("/getIndById")
    public ResponseEntity<ArrayList<GetIndentResponseDTO>> GetIndent(@RequestParam("ind_id") ArrayList<Integer> indentIds){
        return new ResponseEntity<>(getIndentService.GetIndent(indentIds), HttpStatus.OK);
    }
}
