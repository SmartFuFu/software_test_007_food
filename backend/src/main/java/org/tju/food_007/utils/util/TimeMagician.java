package org.tju.food_007.utils.util;

/**
 * @author WGY
 * @create 2024-04-19-14:59
 */

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import org.tju.food_007.model.IndentEntity;
import org.tju.food_007.repository.cus.indent.GenerateIndentRepository;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
public class TimeMagician{
    @Autowired
    GenerateIndentRepository generateIndentRepository;
    @Scheduled(fixedRate = 3600000)
    public void reportCurrentTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        ArrayList<IndentEntity> indents= generateIndentRepository.findAll();
        System.out.println("修改订单状态，处理过期未取的订单");
        dateTime=dateTime.minusDays(1);
        for (IndentEntity ind : indents){
            Timestamp ind_create_time=ind.getIndCreationTime();
            LocalDateTime ind_create_time_local=ind_create_time.toLocalDateTime();
            if(ind_create_time_local.isBefore(dateTime)&&ind.getDeliveryMethod()==1&&ind.getIndState()==2){
                    ind.setIndState(4);
                    System.out.println(String.valueOf(ind.getIndId())+"-"+ind_create_time_local.toString());
                    generateIndentRepository.save(ind);
            }
        }
    }
}

