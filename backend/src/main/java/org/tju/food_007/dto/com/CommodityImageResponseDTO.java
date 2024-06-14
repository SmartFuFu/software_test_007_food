package org.tju.food_007.dto.com;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class CommodityImageResponseDTO {
    private String com_image;

    public CommodityImageResponseDTO(String com_image) {
        this.com_image = com_image;
    }
}

