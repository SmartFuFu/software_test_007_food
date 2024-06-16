package org.tju.food_007.dto.cus.indent;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WGY
 * @create 2024-03-17-15:12
 */
@Getter
@Setter
public class IndentCommodity {
    private String com_id;
    private String ind_quantity;
    private String commodity_money;

    public IndentCommodity(String com_id, String ind_quantity, String commodity_money) {
        this.com_id = com_id;
        this.ind_quantity = ind_quantity;
        this.commodity_money = commodity_money;
    }
    // 构造方法、getter和setter省略
}
