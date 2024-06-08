package org.tju.food_007.dto.cus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.cus.indent.GenerateIndentRequestDTO;

import org.tju.food_007.model.IndentEntity;
import org.mapstruct.Mapping;


/**
 * @author WGY
 * @create 2024-03-17-15:14
 */

@Mapper
public interface GenerateIndentRequestMapper {
    GenerateIndentRequestMapper INSTANCE = Mappers.getMapper(GenerateIndentRequestMapper.class);

    @Mapping(target = "indId", ignore = true) // 假设订单ID是由数据库自动生成的
    @Mapping(target = "cusId", source = "cus_Id")
    @Mapping(target = "indMoney", source = "ind_money")
    @Mapping(target = "indCreationTime", expression = "java(java.sql.Timestamp.from(java.time.Instant.now()))")
    @Mapping(target = "indVerificationCode", ignore = true) // 取货码可能需要特别处理或生成
    @Mapping(target = "indNotes", source = "ind_notes")
    @Mapping(target = "indState", constant = "0") // 假设初始状态
    @Mapping(target = "foodQualityScore", ignore = true) // 评分可能稍后添加
    @Mapping(target = "serviceQualityScore", ignore = true) // 评分可能稍后添加
    @Mapping(target = "pricePerformanceRatio", ignore = true) // 评分可能稍后添加
    @Mapping(target = "deliveryMethod", source = "delivery_method")
    @Mapping(target = "deliveryAddress", source = "delivery_address")
    @Mapping(target = "deliveryAltitude", source = "delivery_altitude")
    @Mapping(target = "deliveryLongitude", source = "delivery_longitude")

    IndentEntity dtoToEntity(GenerateIndentRequestDTO dto);
}
