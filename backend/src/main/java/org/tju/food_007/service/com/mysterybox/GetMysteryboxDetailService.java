package org.tju.food_007.service.com.mysterybox;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tju.food_007.dto.com.mysterybox.GetMysteryboxRequestDTO;
import org.tju.food_007.dto.com.mysterybox.GetMysteryboxResponseDTO;
import org.tju.food_007.dto.com.mysterybox.mapper.GetMysteryboxResponseMapper;
import org.tju.food_007.model.*;
import org.tju.food_007.repository.com.mysterybox.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GetMysteryboxDetailService {

     private final GetMysteryboxResponseMapper getMysteryboxResponseMapper=
             GetMysteryboxResponseMapper.INSTANCE;

     @Autowired
     CommodityImageRepository commodityImageRepository;

     @Autowired
     CommodityRepository commodityRepository;

     @Autowired
     MysteryBoxRepository mysteryBoxRepository;

     @Autowired
     StoreReposirory storeReposirory;

     @Autowired
     UserRepository userRepository;


    public List<GetMysteryboxResponseDTO> getMysteryboxList(GetMysteryboxRequestDTO request){
        List<GetMysteryboxResponseDTO> response =new ArrayList<>();

        //返回单个盲盒信息
        if(request.getMystery_box_ID()!=null){
            CommodityEntity commodity =commodityRepository.findFirstByComId(request.getMystery_box_ID());
            List<CommodityImageEntity> ComImages = commodityImageRepository.findAllByComId(request.getMystery_box_ID());
            MysteryBoxEntity mysteryBox=mysteryBoxRepository.findFirstByMysteryBoxId(request.getMystery_box_ID());
            StoreEntity store =storeReposirory.findFirstByStoId(commodity.getStoId());
            UserEntity user =userRepository.findFirstByUserId(commodity.getStoId());
            List<GetMysteryboxResponseDTO.ImageDTO> imageDTOS=new ArrayList<>();
            for(CommodityImageEntity image :ComImages){
                GetMysteryboxResponseDTO.ImageDTO imageDTO=new GetMysteryboxResponseDTO.ImageDTO();
                imageDTO.setCom_image(image.getComImage());
                imageDTOS.add(imageDTO);
            }
            GetMysteryboxResponseDTO dto = getMysteryboxResponseMapper.entityToResponse(
                    mysteryBox,commodity,store,user,imageDTOS.toArray(new GetMysteryboxResponseDTO.ImageDTO[0]));
            response.add(dto);

            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            for(GetMysteryboxResponseDTO res : response){
                String opentime = res.getSto_openingTime();
                String closetime = res.getSto_closingTime();

                // 解析时间字符串
                LocalDateTime openDateTime = LocalDateTime.parse(opentime, inputFormatter);
                LocalDateTime closeDateTime = LocalDateTime.parse(closetime, inputFormatter);

                // 格式化为所需的字符串形式
                String formattedOpenTime = openDateTime.format(outputFormatter);
                String formattedCloseTime = closeDateTime.format(outputFormatter);

                // 更新对象的时间属性
                res.setSto_openingTime(formattedOpenTime);
                res.setSto_closingTime(formattedCloseTime);

            }

            return response;
        }

        //返回所有盲盒
        List<CommodityEntity> commodities ;

        if(request.getSto_ID()!=null){
            commodities=commodityRepository.findAllByStoIdAndComType(request.getSto_ID(),1);
        }
        else {
            commodities=commodityRepository.findAllByComType(1);
        }

        for(CommodityEntity commodity:commodities){
            List<CommodityImageEntity> ComImages = commodityImageRepository.findAllByComId(commodity.getComId());
            MysteryBoxEntity mysteryBox=mysteryBoxRepository.findFirstByMysteryBoxId(commodity.getComId());
            StoreEntity store =storeReposirory.findFirstByStoId(commodity.getStoId());
            UserEntity user =userRepository.findFirstByUserId(commodity.getStoId());
            List<GetMysteryboxResponseDTO.ImageDTO> imageDTOS=new ArrayList<>();
            for(CommodityImageEntity image :ComImages){
                GetMysteryboxResponseDTO.ImageDTO imageDTO=new GetMysteryboxResponseDTO.ImageDTO();
                imageDTO.setCom_image(image.getComImage());
                imageDTOS.add(imageDTO);
            }
            GetMysteryboxResponseDTO dto = getMysteryboxResponseMapper.entityToResponse(
                    mysteryBox,commodity,store,user,imageDTOS.toArray(new GetMysteryboxResponseDTO.ImageDTO[0]));
            response.add(dto);
        }

        //对盲盒进行排序
        if(request.getSort_by()!=null && request.getSort_order()!=null){
            //价格升序
            if(request.getSort_by()==0 && request.getSort_order()==0){
                response.sort(new Comparator<GetMysteryboxResponseDTO>() {
                    @Override
                    public int compare(GetMysteryboxResponseDTO o1, GetMysteryboxResponseDTO o2) {
                        return Double.compare(o1.getCom_oriPrice(),o2.getCom_oriPrice());
                    }
                });
            }
            //价格降序
            if(request.getSort_by()==0 && request.getSort_order()==1){
                response.sort(new Comparator<GetMysteryboxResponseDTO>() {
                    @Override
                    public int compare(GetMysteryboxResponseDTO o1, GetMysteryboxResponseDTO o2) {
                        return Double.compare(o2.getCom_oriPrice(),o1.getCom_oriPrice());
                    }
                });
            }
            //评分升序
            if(request.getSort_by()==1 && request.getSort_order()==0){
                response.sort(new Comparator<GetMysteryboxResponseDTO>() {
                    @Override
                    public int compare(GetMysteryboxResponseDTO o1, GetMysteryboxResponseDTO o2) {
                        return Double.compare(o1.getPraise_rate(),o2.getPraise_rate());
                    }
                });
            }
            //评分降序
            if(request.getSort_by()==1 && request.getSort_order()==1){
                response.sort(new Comparator<GetMysteryboxResponseDTO>() {
                    @Override
                    public int compare(GetMysteryboxResponseDTO o1, GetMysteryboxResponseDTO o2) {
                        return Double.compare(o2.getPraise_rate(),o1.getPraise_rate());
                    }
                });
            }

        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for(GetMysteryboxResponseDTO res : response){
            String opentime = res.getSto_openingTime();
            String closetime = res.getSto_closingTime();

            // 解析时间字符串
            LocalDateTime openDateTime = LocalDateTime.parse(opentime, inputFormatter);
            LocalDateTime closeDateTime = LocalDateTime.parse(closetime, inputFormatter);

            // 格式化为所需的字符串形式
            String formattedOpenTime = openDateTime.format(outputFormatter);
            String formattedCloseTime = closeDateTime.format(outputFormatter);

            // 更新对象的时间属性
            res.setSto_openingTime(formattedOpenTime);
            res.setSto_closingTime(formattedCloseTime);

        }

        //裁剪
        if(request.getPage_size()!=null &&request.getPage_num()!=null){
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


        return response;

    }
}
