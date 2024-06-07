package org.tju.food_007.service.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.model.IndentCommodityEntity;

import org.tju.food_007.repository.sto.StoGeComListRepository;
import org.tju.food_007.repository.sto.StoGetIndListRepository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author WGY
 * @create 2024-04-01-18:52
 */
@Service
public class StoGetIndentListService {
    @Autowired
    StoGetIndListRepository stoGetIndListRepository;
    @Autowired
    StoGeComListRepository stoGeComListRepository;

     public ArrayList<String>StoGetInd(Integer sto_id){
        ArrayList<CommodityEntity> com_list= stoGeComListRepository.findCommodityEntitiesByStoId(sto_id);
        ArrayList<Integer> com_ids=new ArrayList<Integer>();
        ArrayList<String> ind_ids=new ArrayList<String>();
        for (CommodityEntity com :com_list){
            com_ids.add(com.getComId());
        }
        List<IndentCommodityEntity> indent_com_list=stoGetIndListRepository.findByComIdIn(com_ids);
        for (IndentCommodityEntity com : indent_com_list){
            String temp=String.valueOf(com.getIndId());

            ind_ids.add(temp);
        }
         Set<String> setWithoutDuplicates = new LinkedHashSet<>(ind_ids);

         // 将Set转换回ArrayList
         ArrayList<String> new_ind_ids= new ArrayList<>(setWithoutDuplicates);
        return  new_ind_ids;
    }
}
