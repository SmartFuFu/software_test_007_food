package org.tju.food_007.dto.sto.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.sto.StoUploadRegularCommodityRequestDTO;
import org.tju.food_007.model.CommodityEntity;

@Mapper
public interface StoUploadRegularCommodityRequestMapper {
    StoUploadRegularCommodityRequestMapper INSTANCE = Mappers.getMapper(StoUploadRegularCommodityRequestMapper.class);
    @Mapping(target = "comId", ignore = true)
    @Mapping(source = "com_name", target = "comName")
    @Mapping(source = "com_introduction", target = "comIntroduction")
    @Mapping(source = "com_left", target = "comLeft")
    @Mapping(source = "sto_ID", target = "stoId")
    @Mapping(source = "com_type", target = "comType")
    @Mapping(source = "com_oriPrice", target = "comOriPrice")
    @Mapping(source = "praise_rate", target = "praiseRate")
    @Mapping(target = "comUploadDate", ignore = true) // 假设DTO没有上传日期信息，这里需要忽略
    CommodityEntity dtoToComEntity( StoUploadRegularCommodityRequestDTO dto);


}
