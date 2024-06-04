package org.tju.food_007.service.com.Information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.model.CommoditiesCategoriesEntity;
import org.tju.food_007.repository.com.GetCommodityCategoryRepository;

import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-03-24-20:39
 */
@Service
public class GetCommodityCategoryService {
    @Autowired
    GetCommodityCategoryRepository getCommodityCategoryRepository;
    public ArrayList<String> getCommodityCategory(){
        ArrayList<CommoditiesCategoriesEntity> list= (ArrayList<CommoditiesCategoriesEntity>) getCommodityCategoryRepository.findAll();
        ArrayList<String> str_list=new ArrayList<String>();
        for (CommoditiesCategoriesEntity categories : list){
            str_list.add(categories.getComCategory());
        }
        return str_list;
    }
}
