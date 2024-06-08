package org.tju.food_007.service.com.Recommend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.model.CommodityCategoriesEntity;
import org.tju.food_007.model.CommodityEntity;

import org.tju.food_007.model.IndentEntity;
import org.tju.food_007.repository.com.Recommend.RecommendCommodityCategoryRepository;
import org.tju.food_007.repository.com.Recommend.RecommendCommodityRepository;
import org.tju.food_007.repository.com.Recommend.RecommendIndentDetailRepository;
import org.tju.food_007.repository.com.Recommend.RecommendIndentRepositiry;

import java.util.*;

@Service
public class RecommendComService {

    @Autowired
    RecommendCommodityCategoryRepository recommendCommodityCategoryRepository;

    @Autowired
    RecommendCommodityRepository recommendCommodityRepository;

    @Autowired
    RecommendIndentRepositiry recommendIndentRepositiry;
    @Autowired
    RecommendIndentDetailRepository recommendIndentDetailRepository;
    public List<Integer> recommendCommodityByCom(Integer com_ID,Integer page_size,Integer page_num){
        List<Integer> Com_SameType_IDs = new ArrayList<>();
        List<Integer> Com_ALL_IDs = new ArrayList<>();
        List<Integer> response = new ArrayList<>();
        if(!recommendCommodityRepository.existsByComId(com_ID)){
            System.out.println("商品信息不存在");
            return response;
        }
        List<CommodityEntity> commodities = recommendCommodityRepository.findAllByComType(0);
        for(CommodityEntity commodity : commodities){
            Com_ALL_IDs.add(commodity.getComId());
        }
        List<CommodityCategoriesEntity> commodityBase = recommendCommodityCategoryRepository.findAllByComId(com_ID);
        List<String> categories = new ArrayList<>();
        for(CommodityCategoriesEntity cate : commodityBase){
            categories.add(cate.getComCategory());
        }
        for(String category : categories){
            List<CommodityCategoriesEntity> tempCatetoryTypes =
                    recommendCommodityCategoryRepository.findAllByComCategory(category);
            for (CommodityCategoriesEntity temp :tempCatetoryTypes){
                Com_SameType_IDs.add(temp.getComId());
            }
        }
        //对Com_Same_type进行去重
        Set<Integer> uniqueIds = new HashSet<>(Com_SameType_IDs);
        Com_SameType_IDs.clear();
        Com_SameType_IDs.addAll(uniqueIds);

        // 去除 Com_ALL_IDs 中含有 Com_SameType_IDs 的元素
        Com_ALL_IDs.removeAll(Com_SameType_IDs);


// 计算需要放入 response 列表的元素数量
        int numSameTypeIdsToResponse = (int) (Com_SameType_IDs.size() * 0.7);

// 计算从 Com_SameType_IDs 中需要随机选择的元素数量
        int numRandomIds = (int) (numSameTypeIdsToResponse * 0.7);

// 从 Com_SameType_IDs 中随机选择 numRandomIds 个元素放入 response 列表前面
        Collections.shuffle(Com_SameType_IDs);
        List<Integer> selectedIds = Com_SameType_IDs.subList(0, numRandomIds);
        response.addAll(selectedIds);

// 从 Com_SameType_IDs 中移除已经选择的元素
        Com_SameType_IDs.removeAll(selectedIds);

// 计算 Com_ALL_IDs 中需要放入 response 列表的元素数量
        int numAllIdsToResponse = Com_SameType_IDs.size() + Com_ALL_IDs.size();

// 从 Com_ALL_IDs 中随机选择 numAllIdsToResponse - numSameTypeIdsToResponse 个元素
        Collections.shuffle(Com_ALL_IDs);
        List<Integer> remainingIds = Com_ALL_IDs.subList(0, numAllIdsToResponse - numSameTypeIdsToResponse);

// 将剩余的元素与 Com_SameType_IDs 中剩余的元素混合并放入 response 列表后面
        remainingIds.addAll(Com_SameType_IDs);
        response.addAll(remainingIds);


        // 剪切返回
        System.out.println("推荐总数为" + response.size());
        if (response.size() <= page_size * (page_num - 1)) {
            response.clear();
            return response; // 如果请求页超出范围，则返回空列表
        }
        int beg = page_size * (page_num - 1); // 起始索引
        int end = Math.min(page_size * page_num, response.size()); // 结束索引（不包括）

        System.out.println("返回总数为" + (end - beg));

        return response.subList(beg, end);

    }


    public List<Integer> recommendCommodityByUser(Integer user_ID,Integer page_size,Integer page_num){
        List<Integer> response =new ArrayList<>();
        IndentEntity indent=recommendIndentRepositiry.findFirstByCusId(user_ID);
        if(indent==null){
            System.out.println("用户没有订单，无法推荐");
            List<CommodityEntity> commodities = recommendCommodityRepository.findAllByComType(0);
            for(CommodityEntity commodity : commodities){
                response.add(commodity.getComId());
            }
            System.out.println("推荐总数为" + response.size());
            if (response.size() <= page_size * (page_num - 1)) {
                response.clear();
                return response; // 如果请求页超出范围，则返回空列表
            }
            int beg = page_size * (page_num - 1); // 起始索引
            int end = Math.min(page_size * page_num, response.size()); // 结束索引（不包括）

            System.out.println("返回总数为" + (end - beg));

            return response.subList(beg, end);
        }
        Integer com_ID = recommendIndentDetailRepository.
                findFirstByIndId(indent.getIndId()).getComId();
        return recommendCommodityByCom(com_ID,page_size,page_num);
    }
}
