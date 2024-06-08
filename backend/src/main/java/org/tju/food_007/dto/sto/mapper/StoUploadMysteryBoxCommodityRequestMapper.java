package org.tju.food_007.dto.sto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.sto.StoUploadMysteryBoxCommodityRequestDTO;

import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.model.MysteryBoxEntity;

/**
 * @author WGY
 * @create 2024-03-20-11:35
 */
@Mapper
public interface StoUploadMysteryBoxCommodityRequestMapper {
    StoUploadMysteryBoxCommodityRequestMapper INSTANCE = Mappers.getMapper(StoUploadMysteryBoxCommodityRequestMapper.class);

    @Mapping(source = "com_name", target = "comName")
    @Mapping(source = "com_introduction", target = "comIntroduction")
    @Mapping(source = "com_left", target = "comLeft")
    @Mapping(source = "sto_ID", target = "stoId")
    @Mapping(source = "com_type", target = "comType")
    @Mapping(source = "com_oriPrice", target = "comOriPrice")
    @Mapping(source = "praise_rate", target = "praiseRate")
    @Mapping(target = "comUploadDate", ignore = true)
    CommodityEntity dtoToComEntity(StoUploadMysteryBoxCommodityRequestDTO dto);

    @Mapping(source = "highest_price", target = "highestPrice")
    @Mapping(source = "lowest_price", target = "lowestPrice")
    @Mapping(source = "category_count", target = "categoryCount")
    MysteryBoxEntity dtoToMysteryBoxEntity(StoUploadMysteryBoxCommodityRequestDTO dto);

}
