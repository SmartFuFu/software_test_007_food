package org.tju.food_007.dto.sto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.sto.StoCategoriesOnsaleResponseDTO;
import org.tju.food_007.model.CommodityCategoriesEntity;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StoCategoriesOnsaleMapper {
    StoCategoriesOnsaleMapper INSTANCE =
            Mappers.getMapper(StoCategoriesOnsaleMapper.class);




    public default StoCategoriesOnsaleResponseDTO mapStoCat(List<CommodityCategoriesEntity> commodities){
        if(commodities== null){
            return null;
        }
        List<String> categories =new ArrayList<String>();
        for(CommodityCategoriesEntity commodity : commodities){
            if(!categories.contains(commodity.getComCategory())){
                categories.add(commodity.getComCategory());
            }
        }
        StoCategoriesOnsaleResponseDTO response =new StoCategoriesOnsaleResponseDTO();
        response.setCom_category(categories);

        return response;
    }
}
