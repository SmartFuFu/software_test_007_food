package org.tju.food_007.dto.sto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.sto.StoCategoriesInformationResponseDTO;
import org.tju.food_007.dto.sto.StoImageInfomationResponseDTO;
import org.tju.food_007.dto.sto.StoLicenseInformationResponseDTO;
import org.tju.food_007.dto.sto.StoinformationdetailResponseDTO;
import org.tju.food_007.model.*;


import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StoinformationdetailMapper {
    StoinformationdetailMapper INSTANCE =
            Mappers.getMapper(StoinformationdetailMapper.class);


    @Mapping(source = "user.userId", target = "user_ID")
    @Mapping(source = "user.userPhone", target = "user_phone")
    @Mapping(source = "user.userPassword", target = "user_password")
    @Mapping(source = "user.userAddress", target = "user_address")
    @Mapping(source = "user.userState", target = "user_state")
    @Mapping(source = "user.userBalance", target = "user_balance")
    @Mapping(source = "user.userRegTime", target = "user_regTime")
    @Mapping(source = "user.userType", target = "user_type")
    @Mapping(source = "user.userGender", target = "user_gender")
    @Mapping(source = "store.stoName", target = "sto_name")
    @Mapping(source = "store.stoIntroduction", target = "sto_introduction")
    @Mapping(source = "store.stoState", target = "sto_state")
    @Mapping(source = "store.stoOpeningTime", target = "sto_openingTime")
    @Mapping(source = "store.stoClosingTime", target = "sto_closingTime")
    @Mapping(source = "store.stoLatitude", target = "sto_latitude")
    @Mapping(source = "store.stoLongitude", target = "sto_longitude")
    @Mapping(source = "store.stoRating" ,target =  "sto_rating")
    @Mapping(source = "store.stoLogo",target = "sto_logo")
    @Mapping(expression = "java(mapStoCategories(storeCategories))", target = "store_categories")
    @Mapping(expression = "java(mapStoLicenses(storeLicenses))", target = "storeLicense")
    @Mapping(expression = "java(mapStoImages(storeImages))", target = "storeImage")
    @Mapping(target = "distance", ignore = true)
    StoinformationdetailResponseDTO entityToResponse
            (UserEntity user, StoreEntity store, StoreCategoriesEntity[] storeCategories,
             StoreImageEntity[] storeImages, StoreLicenseEntity[] storeLicenses);

    default StoImageInfomationResponseDTO[] mapStoImages(StoreImageEntity[] storeImages) {
        if (storeImages == null) {
            return null;
        }

        List<StoImageInfomationResponseDTO> dtoList = new ArrayList<>();
        for (StoreImageEntity storeImageEntity : storeImages) {
            StoImageInfomationResponseDTO dto = new StoImageInfomationResponseDTO();
            dto.setSto_image(storeImageEntity.getStoImage());
            dtoList.add(dto);
        }
        return dtoList.toArray(new StoImageInfomationResponseDTO[0]);
    }

    default StoCategoriesInformationResponseDTO[] mapStoCategories(StoreCategoriesEntity[] storeCategories) {
        if (storeCategories == null) {
            return null;
        }

        List<StoCategoriesInformationResponseDTO> dtoList = new ArrayList<>();
        for (StoreCategoriesEntity storeCategoriesEntity : storeCategories) {
            StoCategoriesInformationResponseDTO dto = new StoCategoriesInformationResponseDTO();
            dto.setCom_category(storeCategoriesEntity.getComCategory());
            dtoList.add(dto);
        }
        return dtoList.toArray(new StoCategoriesInformationResponseDTO[0]);
    }

    default StoLicenseInformationResponseDTO[] mapStoLicenses(StoreLicenseEntity[] storeLicenses) {
        if (storeLicenses == null) {
            return null;
        }

        List<StoLicenseInformationResponseDTO> dtoList = new ArrayList<>();
        for (StoreLicenseEntity storeLicenseEntity : storeLicenses) {
            StoLicenseInformationResponseDTO dto = new StoLicenseInformationResponseDTO();
            dto.setSto_license(storeLicenseEntity.getStoLicense());
            dtoList.add(dto);
        }
        return dtoList.toArray(new StoLicenseInformationResponseDTO[0]);
    }
}












