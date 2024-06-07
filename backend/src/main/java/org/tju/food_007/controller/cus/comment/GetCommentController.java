package org.tju.food_007.controller.cus.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tju.food_007.dto.cus.comment.GetCommentRequestDTO;
import org.tju.food_007.dto.cus.comment.GetCommentResponseDTO;
import org.tju.food_007.service.cus.comment.GetCommentService;

import java.util.List;

@RestController
@RequestMapping("/api/cus")
public class GetCommentController {
    @Autowired
    GetCommentService getCommentService;

    @RequestMapping(value = "/getComment",method = RequestMethod.GET)
    public ResponseEntity<GetCommentResponseDTO[]> getComment(
            @RequestParam int sto_ID,
            @RequestParam int page_size,
            @RequestParam int page_num){
        GetCommentRequestDTO request = new GetCommentRequestDTO();
        request.setSto_ID(sto_ID);
        request.setPage_size(page_size);
        request.setPage_num(page_num);

        List<GetCommentResponseDTO> responseDTOS=getCommentService.getComment(request);
        return new ResponseEntity<>(responseDTOS.toArray(new GetCommentResponseDTO[0]), HttpStatus.OK);

    }
}
