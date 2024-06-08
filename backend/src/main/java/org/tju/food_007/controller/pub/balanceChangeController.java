package org.tju.food_007.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.tju.food_007.dto.pub.NormalResponseDTO;
import org.tju.food_007.dto.pub.balanceChangeDTO;

import org.tju.food_007.service.pub.BalanceChangeService;


/**
 * @author WGY
 * @create 2024-04-26-14:06
 */
@RestController
@RequestMapping("/api/pub/balanceChange")
public class balanceChangeController {
    @Autowired
    BalanceChangeService  balanceChangeService;
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<NormalResponseDTO> changeUserBalance(@RequestBody balanceChangeDTO requestDTO){
        //GetDistanceResponseDTO responseDTO=getDistanceService.GetStoDistance(requestDTO);
        balanceChangeService.changeUserBalance(requestDTO);
        NormalResponseDTO responseDTO=new NormalResponseDTO();
        responseDTO.setMsg("成功");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
