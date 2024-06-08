package org.tju.food_007.dto.cus.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.cus.comment.GetCommentResponseDTO;
import org.tju.food_007.model.CommentEntity;
import org.tju.food_007.model.CustomerEntity;

@Mapper
public interface GetCommentResponseMapper {
    GetCommentResponseMapper INSTANCE =
            Mappers.getMapper(GetCommentResponseMapper.class);


    @Mapping(source = "comment.cmtId", target = "cmt_ID")
    @Mapping(source = "comment.cmtContent", target = "cmt_content")
    @Mapping(source = "comment.cmtTime", target = "cmt_time")
    @Mapping(source = "comment.indId", target = "ind_ID")
    @Mapping(source = "user.cusId", target = "user_ID")
    @Mapping(source = "user.cusNickname", target = "user_name")
    @Mapping(source = "user.userLogo", target = "user_logo")
    GetCommentResponseDTO entityToResponse(CustomerEntity user, CommentEntity comment);

}
