package org.tju.food_007.dto.pub.register;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;


/**
 * @author WGY
 * @create 2024-03-10-11:51
 */


@Getter
@Setter
public class StoreRegistrationRequestDTO {
    // 用户信息
    private String user_phone; // 用户电话
    private String user_password; // 用户密码
    private String user_address; // 用户（商家）地址
    private Integer user_gender; // 用户性别

    // 商店信息
    private String sto_name; // 商店名称
    private String sto_introduction; // 商店介绍

    @JsonFormat(pattern ="HH:mm")
    private Time sto_openingTime; // 开始营业时间，格式为HH:mm
    @JsonFormat(pattern ="HH:mm")
    private Time sto_closingTime; // 结束营业时间，格式为HH:mm

    private String sto_latitude; // GPS纬度
    private String sto_longitude; // GPS经度

    // getters and setters
}

