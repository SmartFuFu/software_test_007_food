package org.tju.food_007.dto.com;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommodityCategoriesResponseDTO {
    private String com_category;
    public CommodityCategoriesResponseDTO(String com_category) {
        this.com_category = com_category;
    }
}
