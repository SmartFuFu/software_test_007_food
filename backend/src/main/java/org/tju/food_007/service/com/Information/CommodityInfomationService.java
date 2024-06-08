package org.tju.food_007.service.com.Information;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.tju.food_007.dto.com.*;
import org.tju.food_007.repository.com.Information.CommodityDetailRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class CommodityInfomationService {

    @Autowired
    CommodityDetailRepository commodityDetailRepository;


    /**
     * @author fjh
     * @date 2024/3/9
     * @Description 查询单个商品详情
     */
    public CommodityDetailDTO getCommodityDetail(Integer com_id){
        CommodityDetailDTO commodityDetailDTO = new CommodityDetailDTO();

        if(com_id<1){
            commodityDetailDTO.setCom_ID(-1);
            return commodityDetailDTO;
        }

        List<Object[]> objects = commodityDetailRepository.getCommodityDetail(com_id);

        if(objects.isEmpty()){
            commodityDetailDTO.setCom_ID(-1);
            return commodityDetailDTO;
        }

        if(objects.getFirst()[0]!=null)commodityDetailDTO.setCom_ID((Integer) objects.getFirst()[0]);
        if(objects.getFirst()[1]!=null)commodityDetailDTO.setCom_name(objects.getFirst()[1].toString());
        if(objects.getFirst()[2]!=null)commodityDetailDTO.setCom_introduction(objects.getFirst()[2].toString());
        if(objects.getFirst()[3]!=null)commodityDetailDTO.setCom_oriPrice(Double.parseDouble(objects.getFirst()[3].toString()));
        if(objects.getFirst()[4]!=null)commodityDetailDTO.setCom_type((Integer) objects.getFirst()[4]);
        if(objects.getFirst()[5]!=null)commodityDetailDTO.setCom_uploadDate(objects.getFirst()[5].toString());
        if(objects.getFirst()[6]!=null)commodityDetailDTO.setCom_left((Integer)objects.getFirst()[6]);
        if(objects.getFirst()[7]!=null)commodityDetailDTO.setPraise_rate(Double.parseDouble(objects.getFirst()[7].toString()));
        if(objects.getFirst()[8]!=null)commodityDetailDTO.setSto_ID((Integer)objects.getFirst()[8]);
        if(objects.getFirst()[10]!=null)commodityDetailDTO.setSto_openingTime(objects.getFirst()[10].toString());
        if(objects.getFirst()[11]!=null)commodityDetailDTO.setSto_closingTime(objects.getFirst()[11].toString());
        if(objects.getFirst()[12]!=null)commodityDetailDTO.setCom_expirationDate(objects.getFirst()[12].toString());
        if(objects.getFirst()[17]!=null)commodityDetailDTO.setCom_producedDate(objects.getFirst()[17].toString());
        if(objects.getFirst()[13]!=null){
            List<String> categories = Arrays.stream(objects.getFirst()[13].toString().split(",")).toList();
            List<CommodityCategoriesResponseDTO> categoriesResponseDTOS =new ArrayList<>();
            for(String category : categories){
                CommodityCategoriesResponseDTO commodityCategoriesResponseDTO =new CommodityCategoriesResponseDTO();
                commodityCategoriesResponseDTO.setCom_category(category);
                categoriesResponseDTOS.add(commodityCategoriesResponseDTO);
            }
            commodityDetailDTO.setCommodity_categories(categoriesResponseDTOS.toArray(new CommodityCategoriesResponseDTO[0]));
        }
        if(objects.getFirst()[14]!=null){
            List<String> images = Arrays.stream(objects.getFirst()[14].toString().split(",")).toList();
            List<CommodityImageResponseDTO> commodityImageResponseDTOS =new ArrayList<>();
            for(String image : images){
                CommodityImageResponseDTO commodityImageResponseDTO =new CommodityImageResponseDTO();
                commodityImageResponseDTO.setCom_image(image);
                commodityImageResponseDTOS.add(commodityImageResponseDTO);
            }
            commodityDetailDTO.setCommodityImage(commodityImageResponseDTOS.toArray(new CommodityImageResponseDTO[0]));

        }
        List<CommodityPriceCurveResponseDTO> commodityPriceCurveResponseDTOS = new ArrayList<>();
        for(Object[] tempObj : objects){
            CommodityPriceCurveResponseDTO tempDTO = new CommodityPriceCurveResponseDTO();
            tempDTO.setCom_pc_time(tempObj[15].toString());
            tempDTO.setCom_pc_price(Double.parseDouble(tempObj[16].toString()));
            commodityPriceCurveResponseDTOS.add(tempDTO);
        }
        commodityDetailDTO.setCommodityPriceCurve(commodityPriceCurveResponseDTOS.toArray(new CommodityPriceCurveResponseDTO[0]));


        return commodityDetailDTO;



    }


    /**
     * @author fjh
     * @date 2024/3/9
     * @Description 查询商家的商品列表
     */
    public List<CommodityDetailDTO> getCommodityList(GetCommodityListRequestDTO request){
        List<CommodityDetailDTO> response =new ArrayList<>();

        List<Object[]> objects;
        if(request.getCom_type() ==null){
            System.out.println("无商品类别查询");
            objects=commodityDetailRepository.getCommodityList(request.getSto_ID());
        }
        else {
            objects=commodityDetailRepository.getCommodityListByCat(request.getSto_ID(),request.getCom_type());
        }
        System.out.println("查询到总数objects为"+objects.size());
        for (Object[] objArray : objects) {
            for (Object obj : objArray) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }
        for(Object[] tempObj : objects){
            CommodityDetailDTO tempDetailDTO = new CommodityDetailDTO();
            if(tempObj[0]!=null)tempDetailDTO.setCom_ID((Integer) tempObj[0]);
            if(tempObj[1]!=null)tempDetailDTO.setCom_name(tempObj[1].toString());
            if(tempObj[2]!=null)tempDetailDTO.setCom_introduction(tempObj[2].toString());
            if(tempObj[3]!=null)tempDetailDTO.setCom_oriPrice(Double.parseDouble(tempObj[3].toString()));
            if(tempObj[4]!=null)tempDetailDTO.setCom_type((Integer)tempObj[4]);
            if(tempObj[5]!=null)tempDetailDTO.setCom_uploadDate(tempObj[5].toString());
            if(tempObj[6]!=null)tempDetailDTO.setCom_left((Integer)tempObj[6]);
            if(tempObj[7]!=null)tempDetailDTO.setPraise_rate(Double.parseDouble(tempObj[7].toString()));
            if(tempObj[8]!=null)tempDetailDTO.setSto_ID((Integer)tempObj[8]);
            if(tempObj[10]!=null)tempDetailDTO.setSto_openingTime(tempObj[10].toString());
            if(tempObj[11]!=null)tempDetailDTO.setSto_closingTime(tempObj[11].toString());
            if(tempObj[12]!=null)tempDetailDTO.setCom_expirationDate(tempObj[12].toString());
            if(tempObj[16]!=null)tempDetailDTO.setCom_producedDate(tempObj[16].toString());
            if(tempObj[13]!=null){
                List<String> categories = Arrays.stream(tempObj[13].toString().split(",")).toList();
                List<CommodityCategoriesResponseDTO> categoriesResponseDTOS =new ArrayList<>();
                for(String category : categories){
                    CommodityCategoriesResponseDTO commodityCategoriesResponseDTO =new CommodityCategoriesResponseDTO();
                    commodityCategoriesResponseDTO.setCom_category(category);
                    categoriesResponseDTOS.add(commodityCategoriesResponseDTO);
                }
                tempDetailDTO.setCommodity_categories(categoriesResponseDTOS.toArray(new CommodityCategoriesResponseDTO[0]));
            }
            if(tempObj[14]!=null){
                List<String> images = Arrays.stream(tempObj[14].toString().split(",")).toList();
                List<CommodityImageResponseDTO> commodityImageResponseDTOS =new ArrayList<>();
                for(String image : images){
                    CommodityImageResponseDTO commodityImageResponseDTO =new CommodityImageResponseDTO();
                    commodityImageResponseDTO.setCom_image(image);
                    commodityImageResponseDTOS.add(commodityImageResponseDTO);
                }
                tempDetailDTO.setCommodityImage(commodityImageResponseDTOS.toArray(new CommodityImageResponseDTO[0]));
            }
            if(tempObj[15]!=null){
                List<String> curves =Arrays.stream(tempObj[15].toString().split(",")).toList();
                List<CommodityPriceCurveResponseDTO> commodityPriceCurveResponseDTOS =new ArrayList<>();
                for(String curve : curves){
                    String[] cur =Arrays.stream(curve.split("#")).toList().toArray(new String[0]);
                    CommodityPriceCurveResponseDTO commodityPriceCurveResponseDTO = new CommodityPriceCurveResponseDTO();
                    commodityPriceCurveResponseDTO.setCom_pc_time(cur[0]);
                    commodityPriceCurveResponseDTO.setCom_pc_price(Double.parseDouble(cur[1]));
                    commodityPriceCurveResponseDTOS.add(commodityPriceCurveResponseDTO);
                }
                tempDetailDTO.setCommodityPriceCurve(commodityPriceCurveResponseDTOS.toArray
                        (new CommodityPriceCurveResponseDTO[0]));
            }
            response.add(tempDetailDTO);
        }

        if (request.getRemaining_proportion() != null) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 创建一个用于记录需要移除的DTO的迭代器
            Iterator<CommodityDetailDTO> iterator = response.iterator();

            while (iterator.hasNext()) {
                CommodityDetailDTO commodity = iterator.next();

                LocalDateTime expirationDate = LocalDateTime.parse(commodity.getCom_expirationDate().replace(".0", ""), formatter);
                LocalDateTime producedDate = LocalDateTime.parse(commodity.getCom_producedDate().replace(".0", ""), formatter);

                if(expirationDate.isBefore(currentDateTime)){
                    if(Math.abs(request.getRemaining_proportion()) > 1e-10){
                        iterator.remove();
                    }
                }
                else{
                    // 计算保质期和剩余保质期
                    Duration guaranteePeriod = Duration.between(producedDate, expirationDate);
                    Duration remainPeriod = Duration.between(currentDateTime, expirationDate);

                    // 计算保质期比例
                    long guaranteePeriodDays = guaranteePeriod.toDays();
                    long remainPeriodDays = remainPeriod.toDays();
                    double ratio = (double) remainPeriodDays / (double) guaranteePeriodDays;
                    System.out.println(commodity.getCom_name()+"当前剩余时间比例："+ratio);

                    // 比较保质期比例与剩余保质期比例
                    if (ratio > request.getRemaining_proportion()) {
                        iterator.remove();
                    }
                }

            }
        }

        int page_size = request.getPage_size();
        int page_num = request.getPage_num();
        System.out.println("查询到总数为" + response.size());
        if (response.size() <= page_size * (page_num - 1)) {
            response.clear();
            return response; // 如果请求页超出范围，则返回空列表
        }
        int beg = page_size * (page_num - 1); // 起始索引
        int end = Math.min(page_size * page_num, response.size()); // 结束索引（不包括）

        System.out.println("返回总数为" + (end - beg));

        return response.subList(beg, end);
    }


    public CommodityStatisticsDTO getCommodityStatistics(int sto_id){
        CommodityStatisticsDTO StatisticsResponse = new CommodityStatisticsDTO();

        int half_num=0;
        int quater_num =0;
        int out_num =0;
        List<ComPeriod> response =new ArrayList<>();

        List<Object[]> objects = commodityDetailRepository.getCommodityStatistics(sto_id);

        // 筛选出所有信息
        for(Object[] tempObj : objects){
            ComPeriod comPeriod = new ComPeriod();
            if(tempObj[0]!=null) comPeriod.setCom_id((Integer) tempObj[0]);
            if(tempObj[1]!=null) comPeriod.setCom_expirationDate(tempObj[1].toString());
            if(tempObj[2]!=null) comPeriod.setCom_producedDate(tempObj[2].toString());
            response.add(comPeriod);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 创建一个用于记录需要移除的DTO的迭代器
        Iterator<ComPeriod> iterator = response.iterator();

        while (iterator.hasNext()) {
            ComPeriod commodity = iterator.next();

            LocalDateTime expirationDate = LocalDateTime.parse(commodity.getCom_expirationDate().replace(".0", ""), formatter);
            LocalDateTime producedDate = LocalDateTime.parse(commodity.getCom_producedDate().replace(".0", ""), formatter);

            if(expirationDate.isBefore(currentDateTime)){
               out_num++;
            }
            else{
                // 计算保质期和剩余保质期
                Duration guaranteePeriod = Duration.between(producedDate, expirationDate);
                Duration remainPeriod = Duration.between(currentDateTime, expirationDate);

                // 计算保质期比例
                long guaranteePeriodDays = guaranteePeriod.toDays();
                long remainPeriodDays = remainPeriod.toDays();
                double ratio = (double) remainPeriodDays / (double) guaranteePeriodDays;

                // 比较保质期比例与剩余保质期比例
                if (ratio <= 0.5) {
                    half_num++;
                }
                if(ratio <= 0.25){
                    quater_num++;
                }
            }
        }

        StatisticsResponse.setHalf_num(half_num);
        StatisticsResponse.setQuater_num(quater_num);
        StatisticsResponse.setOut_num(out_num);

        return StatisticsResponse;
    }

    @Getter
    @Setter
    public static class ComPeriod
    {
        private int com_id;
        private String com_expirationDate;
        private String com_producedDate;
    }
}
