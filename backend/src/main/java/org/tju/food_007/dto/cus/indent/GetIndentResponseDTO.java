package org.tju.food_007.dto.cus.indent;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-03-17-21:11
 */
@Getter
@Setter
public class GetIndentResponseDTO {
    private Integer ind_id;
    private Integer cus_id;
    private BigDecimal ind_money;
    private String ind_creationTime; // 日期时间字符串
    private String ind_verificationCode; // 可为空
    private String ind_notes;
    private Integer ind_state;
    private BigDecimal food_quality_score;
    private BigDecimal service_quality_score;
    private BigDecimal price_performance_ratio;
    private Integer delivery_method;
    private String delivery_address;
    private BigDecimal delivery_altitude;
    private BigDecimal delivery_longitude;
    private ArrayList<IndentComInfo> commodities; // 商品信息列表
}
