package org.tju.food_007.dto.sto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class StoUploadRegularCommodityRequestDTO {
    private String com_name;
    private String com_introduction;
    private Integer com_left;
    private Integer sto_ID;
    private Integer com_type; // 0：普通商品，1：盲盒商品
    private Double com_oriPrice;
    private Double praise_rate;
    private String com_expirationDate; // 到期日期
    private String com_producedDate; // 生产日期
    private ArrayList<PriceCurveInfo> price_curve; // 价格曲线
    private ArrayList<ComCategory> categories; // 商品类别
}
