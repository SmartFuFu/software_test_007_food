package org.tju.food_007.dto.cus.indent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.cus.indent.GetIndResponseDTO;

import org.tju.food_007.model.IndentEntity;
import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.model.UserEntity;

@Mapper
public interface GetIndResponseMapper {
    GetIndResponseMapper INSTANCE = Mappers.getMapper(GetIndResponseMapper.class);

    @Mapping(source = "ind.indId", target = "ind_ID")
    @Mapping(source = "ind.cusId", target = "cus_ID")
    @Mapping(source = "ind.indMoney", target = "ind_money")
    @Mapping(source = "ind.indCreationTime", target = "ind_creationTime")
    @Mapping(source = "ind.indVerificationCode", target = "ind_verificationCode")
    @Mapping(source = "ind.indNotes", target = "ind_notes")
    @Mapping(source = "ind.indState", target = "ind_state")
    @Mapping(source = "ind.foodQualityScore", target = "food_quality_score")
    @Mapping(source = "ind.serviceQualityScore", target = "service_quality_score")
    @Mapping(source = "ind.pricePerformanceRatio", target = "price_performance_ratio")
    @Mapping(source = "ind.deliveryMethod", target = "delivery_method")
    @Mapping(source = "ind.deliveryAddress", target = "delivery_address")
    @Mapping(source = "user.userAddress", target = "com_position")
    @Mapping(source ="ind_dto",target = "commodities")
    @Mapping(source = "store.stoId",target = "sto_ID")
    GetIndResponseDTO entityToResponse
            (IndentEntity ind , GetIndResponseDTO.IndComDTO[] ind_dto , UserEntity user, StoreEntity store);

}
