package org.tju.food_007.controller.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.cus.indent.RatingRequestDTO;
import org.tju.food_007.service.cus.indent.IndentRatingService;

/**
 * @author WGY
 * @create 2024-03-17-20:58
 */
@RestController
@RequestMapping("/api/cus/rating")
public class IndentRatingController {
    @Autowired
    IndentRatingService indentRatingService;
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<String> GenerateIndent(@RequestBody RatingRequestDTO requestDTO){
        indentRatingService.IndentRatingAndComment(requestDTO);
        return new ResponseEntity<>("订单评论评分成功", HttpStatus.OK);
    }
}
