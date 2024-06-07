package org.tju.food_007.dto.sto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-03-20-11:31
 */

@Getter
@Setter
public class StoUploadMysteryBoxCommodityRequestDTO {
    private String com_name;
    private String com_introduction;
    private String com_uploadDate; // 日期时间字符串
    private Integer com_left;
    private Integer sto_ID;
    private Integer com_type; // 0：普通商品，1：盲盒商品
    private BigDecimal com_oriPrice;
    private BigDecimal praise_rate;
    private BigDecimal highest_price;
    private BigDecimal lowest_price;
    private Integer category_count;
    private ArrayList<ComCategory> categories; // 商品类别

}





