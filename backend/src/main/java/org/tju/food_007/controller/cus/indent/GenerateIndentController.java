package org.tju.food_007.controller.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tju.food_007.dto.cus.indent.*;
import org.tju.food_007.dto.pub.NormalResponseDTO;
import org.tju.food_007.service.cus.indent.GenerateIndentService;
import org.tju.food_007.service.cus.indent.GetIndentService;
import org.tju.food_007.service.cus.indent.IndentRatingService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WGY
 * @create 2024-03-17-16:57
 */
@RestController
@RequestMapping("/api/cus")
public class GenerateIndentController {
    @Autowired
    GenerateIndentService generateIndentService;
    @RequestMapping(value = "/generateIndent",method = RequestMethod.POST)
    public ResponseEntity<NormalResponseDTO>GenerateIndent(@RequestBody GenerateIndentRequestDTO requestDTO){
       // generateIndentService.GenerateIndent(requestDTO);
        NormalResponseDTO responseDTO=new NormalResponseDTO();
        responseDTO.setMsg(generateIndentService.GenerateIndent(requestDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/changeIndentState",method = RequestMethod.POST)
    public ResponseEntity<NormalResponseDTO>ChangeIndentState(@RequestBody ChangeIndStateRequestDTO requestDTO){
        generateIndentService.ChangeIndentState(requestDTO);
        //generateIndentService.ModifyStoID();
        NormalResponseDTO responseDTO=new NormalResponseDTO();
        responseDTO.setMsg("success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

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


    @Autowired
    IndentRatingService indentRatingService;
    @RequestMapping(value = "/rating",method = RequestMethod.POST)
    public ResponseEntity<String> GenerateIndent(@RequestBody RatingRequestDTO requestDTO){
        indentRatingService.IndentRatingAndComment(requestDTO);
        return new ResponseEntity<>("订单评论评分成功", HttpStatus.OK);
    }

}
