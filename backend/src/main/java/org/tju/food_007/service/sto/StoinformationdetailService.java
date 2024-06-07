package org.tju.food_007.service.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tju.food_007.dto.sto.StoinformationdetailResponseDTO;
import org.tju.food_007.dto.sto.mapper.StoinformationdetailMapper;
import org.tju.food_007.model.*;
import org.tju.food_007.repository.sto.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class StoinformationdetailService {
    @Autowired
    UserInformatonDetailRepository userInformatonDetailRepository;

    @Autowired
    StoinformationdetailRepository stoinformationdetailRepository;

    @Autowired
    StoCategoriesInformationRepository stoCategoriesInformationRepository;

    @Autowired
    StoImageInformationRepository stoImageInformationRepository;

    @Autowired
    StoLicenseInformationRepository stoLicenseInformationRepository;

    private final StoinformationdetailMapper stoinformationdetailMapper =
            StoinformationdetailMapper.INSTANCE;

    /**
     * @author fjh
     * @date 2024/3/8
     * @Description 获取商家商品详情
     */
    public StoinformationdetailResponseDTO stoinformationdetail(Integer id){
        StoinformationdetailResponseDTO response = new StoinformationdetailResponseDTO();

        boolean existUser =userInformatonDetailRepository.existsByUserId(id);
        boolean existSto =stoinformationdetailRepository.existsByStoId(id);

        if(!existSto || !existUser){
            response.setUser_ID(-1);
            return response;
        }

        Optional<UserEntity> user = userInformatonDetailRepository.findById(id);
        Optional<StoreEntity> store = stoinformationdetailRepository.findById(id);
        StoreCategoriesEntity[] storeCategoriesEntities =
                stoCategoriesInformationRepository.findAllByStoreId(id).toArray(new StoreCategoriesEntity[0]);
        StoreImageEntity[] storeImageEntities =
                stoImageInformationRepository.findAllByStoId(id).toArray(new StoreImageEntity[0]);
        StoreLicenseEntity[] storeLicenseEntities =
                stoLicenseInformationRepository.findAllByStoId(id).toArray(new StoreLicenseEntity[0]);

        response = stoinformationdetailMapper.entityToResponse
                (user.orElseThrow(),store.orElseThrow(),storeCategoriesEntities,storeImageEntities,storeLicenseEntities);

        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String opentime = response.getSto_openingTime();
        String closetime = response.getSto_closingTime();

        // 解析时间字符串
        LocalDateTime openDateTime = LocalDateTime.parse(opentime, inputFormatter);
        LocalDateTime closeDateTime = LocalDateTime.parse(closetime, inputFormatter);

        // 格式化为所需的字符串形式
        String formattedOpenTime = openDateTime.format(outputFormatter);
        String formattedCloseTime = closeDateTime.format(outputFormatter);

        // 更新对象的时间属性
        response.setSto_openingTime(formattedOpenTime);
        response.setSto_closingTime(formattedCloseTime);

        return response;

    }




















}
