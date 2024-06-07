package org.tju.food_007.service.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.tju.food_007.dto.cus.indent.RatingRequestDTO;
import org.tju.food_007.dto.cus.mapper.GenerateIndentComRequstMapper;
import org.tju.food_007.dto.cus.mapper.GenerateIndentRequestMapper;
import org.tju.food_007.model.CommentEntity;
import org.tju.food_007.model.IndentCommodityEntity;
import org.tju.food_007.model.IndentEntity;
import org.tju.food_007.repository.cus.indent.GenerateIndentComRepository;
import org.tju.food_007.repository.cus.indent.GenerateIndentRepository;
import org.tju.food_007.repository.cus.indent.IndentCommentRopsitory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-03-17-20:49
 */
@Service
public class IndentRatingService {
    @Autowired
    GenerateIndentRepository generateIndentRepository;
    @Autowired
    GenerateIndentComRepository generateIndentComRepository;
    @Autowired
    IndentCommentRopsitory indentCommentRopsitory;
    private final GenerateIndentComRequstMapper generateIndentComRequstMapper=GenerateIndentComRequstMapper.INSTANCE;
    private final GenerateIndentRequestMapper generateIndentRequestMapper=GenerateIndentRequestMapper.INSTANCE;

    @Transactional
    public String IndentRatingAndComment(RatingRequestDTO requestDTO){

        IndentEntity targetInd=generateIndentRepository.findByIndId(Integer.parseInt(requestDTO.getInd_id()));
        targetInd.setPricePerformanceRatio(BigDecimal.valueOf(requestDTO.getPrice_performance_ratio()));
        targetInd.setFoodQualityScore(BigDecimal.valueOf(requestDTO.getFood_quality_score()));
        targetInd.setServiceQualityScore(BigDecimal.valueOf(requestDTO.getService_quality_score()));
        targetInd.setIndState(5);
        generateIndentRepository.save(targetInd);

        ArrayList<IndentCommodityEntity> com_arr=generateIndentComRepository.findByIndId(Integer.parseInt(requestDTO.getInd_id()));
        for(IndentCommodityEntity com :com_arr){
            com.setRatingType(requestDTO.getRating_type());
            generateIndentComRepository.save(com);
        }
        CommentEntity cmt=indentCommentRopsitory.findByIndId(Integer.parseInt(requestDTO.getInd_id()));
        if(cmt !=null){
            System.out.println("已经存在评论");
            cmt.setCmtContent(requestDTO.getCmt_content());
            LocalDateTime nowTime = LocalDateTime.now();
            cmt.setCmtTime(Timestamp.valueOf(nowTime));
        }
        else {
            System.out.println("第一次评论，有点紧张");
            CommentEntity newCmt = new CommentEntity();
            newCmt.setCmtFather(0);
            LocalDateTime nowTime = LocalDateTime.now();
            newCmt.setCmtTime(Timestamp.valueOf(nowTime));
            newCmt.setCmtContent(requestDTO.getCmt_content());
            newCmt.setIndId(Integer.parseInt(requestDTO.getInd_id()));
            newCmt.setUserId(Integer.parseInt(requestDTO.getCus_id()));
            indentCommentRopsitory.save(newCmt);
        }
        return "成功评论评分";
    }
}
