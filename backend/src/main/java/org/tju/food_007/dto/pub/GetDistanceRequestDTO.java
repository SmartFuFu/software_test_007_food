package org.tju.food_007.dto.pub;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


/**
 * @author WGY
 * @create 2024-03-10-16:10
 */
@Getter
@Setter
public class GetDistanceRequestDTO {
    private String user_latitude;
    private String user_longitude;
    private ArrayList<Integer> sto_id;
}
