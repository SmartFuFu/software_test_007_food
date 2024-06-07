package org.tju.food_007.dto.cus.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.cus.indent.IndentCommodity;
import org.tju.food_007.model.IndentCommodityEntity;


import java.math.BigDecimal;

/**
 * @author WGY
 * @create 2024-03-17-19:52
 */

@Mapper
public interface GenerateIndentComRequstMapper {
    GenerateIndentComRequstMapper INSTANCE = Mappers.getMapper(GenerateIndentComRequstMapper.class);
    @Mapping(target = "indId", ignore = true)
    @Mapping(target = "comId", expression = "java(stringToInt(dto.getCom_id()))") // 假设comId是由数据库自动生成的
    @Mapping(target = "indQuantity", expression = "java(stringToInt(dto.getInd_quantity()))")
    @Mapping(target = "commodityMoney", expression = "java(stringToBigDecimal(dto.getCommodity_money()))")
    @Mapping(target = "ratingType", ignore = true)
    IndentCommodityEntity dtoToEntity(IndentCommodity dto);
    default int stringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // 这里可以根据实际情况处理异常，比如返回默认值或者抛出
            return 0; // 或者抛出自定义异常
        }
    }

    default BigDecimal stringToBigDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return BigDecimal.ZERO; // 或者返回null，取决于你的需求
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            // 处理异常，比如返回默认值或者抛出
            return BigDecimal.ZERO; // 或者抛出自定义异常
        }
    }

}
