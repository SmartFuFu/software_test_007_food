package org.tju.food_007.dto.cus.indent;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetIndResponseDTO {
    private int ind_ID;
    private int cus_ID;
    private double ind_money;
    private String ind_creationTime;
    private String ind_verificationCode;
    private String ind_notes;
    private int ind_state; //0：未收货/1：确认收货/2：待取货/3：已核销/4：超期未取/5：待评价
    private double food_quality_score;
    private double service_quality_score;
    private double price_performance_ratio;
    private int delivery_method;  //0：用户自提/1：商家配送
    private String delivery_address;
    private String com_position;
    private IndComDTO[] commodities;
    private Integer sto_ID;


    @Getter
    @Setter
    public static class IndComDTO{
        private int com_ID;
        private int ind_quantity;
        private double commodity_money;
        private int rating_type;
        private String com_name;
        private String com_image;
    }
}
