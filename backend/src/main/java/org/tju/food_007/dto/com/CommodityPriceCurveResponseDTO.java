package org.tju.food_007.dto.com;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommodityPriceCurveResponseDTO {
    private String com_pc_time;
    private double com_pc_price;

    public CommodityPriceCurveResponseDTO(String com_pc_time, double com_pc_price) {
        this.com_pc_time = com_pc_time;
        this.com_pc_price = com_pc_price;
    }
}
