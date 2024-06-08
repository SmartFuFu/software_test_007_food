package org.tju.food_007.dto.pub.register.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.tju.food_007.dto.pub.register.StoreRegistrationRequestDTO;

import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.model.UserEntity;

/**
 * @author WGY
 * @create 2024-03-10-11:33
 */
@Mapper
public interface StoreRegistrationRequestMapper {
    StoreRegistrationRequestMapper INSTANCE = Mappers.getMapper(StoreRegistrationRequestMapper.class);
    // target的字段和entity中的相同，source的字段和dto中的相同
    @Mapping(target ="userId",ignore = true)
    @Mapping(target="userAddress",source = "user_address")
    @Mapping(target="userPhone",source = "user_phone")
    @Mapping(target="userPassword",source = "user_password")
    @Mapping(target="userState",ignore = true)
    @Mapping(target="userBalance",ignore = true)
    @Mapping(target="userRegTime",ignore = true)
    @Mapping(target = "userType",ignore = true)
    @Mapping(target="userGender",source = "user_gender")
    UserEntity requestToUserEntity(StoreRegistrationRequestDTO request);

    @Mapping(target="stoId",ignore = true)
    @Mapping(target = "stoName",source = "sto_name")
    @Mapping(target = "stoIntroduction",source = "sto_introduction")
    @Mapping(target = "stoState",ignore = true)
    @Mapping(target = "stoOpeningTime",source = "sto_openingTime")
    @Mapping(target = "stoClosingTime",source = "sto_closingTime")
    @Mapping(target = "stoLatitude",source = "sto_latitude")
    @Mapping(target = "stoLongitude",source = "sto_longitude")
    StoreEntity requestToStoEntity(StoreRegistrationRequestDTO request);

}