package org.tju.food_007.repository.cus.info;


import org.tju.food_007.dto.cus.info.GetCusInfoResponseDTO;
import org.tju.food_007.model.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GetCusInfoResponseMapper{

    GetCusInfoResponseMapper INSTANCE = Mappers.getMapper(GetCusInfoResponseMapper.class);

    @Mapping(source = "userPhone", target = "user_phone")
    @Mapping(source = "userAddress", target = "user_address")
    @Mapping(source = "userBalance", target = "user_balance")
    @Mapping(source = "userGender", target = "user_gender")
    @Mapping(target = "cus_nickname", ignore = true)
    @Mapping(target = "user_logo", ignore = true)
    GetCusInfoResponseDTO userEntityToDto(UserEntity userEntity);
}
