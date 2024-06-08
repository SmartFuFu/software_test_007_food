package org.tju.food_007.service.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tju.food_007.dto.sto.*;
import org.tju.food_007.dto.sto.mapper.StoUploadMysteryBoxCommodityRequestMapper;
import org.tju.food_007.dto.sto.mapper.StoUploadRegularCommodityRequestMapper;
import org.tju.food_007.model.*;
import org.tju.food_007.repository.sto.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import static org.tju.food_007.utils.ObsOperationTool.uploadInputStream;

/**
 * @author WGY
 * @create 2024-03-20-11:21
 */
@Service
public class StoUploadCommodityService {
    @Autowired
    StoUploadMysteryBoxCommodityRepository stoUploadMysteryBoxCommodityRepository;
    @Autowired
    StoUploadRegularCommodityRepository stoUploadRegularCommodityRepository;
    @Autowired
    StoUploadCommodityRepository stoUploadCommodityRepository;
    @Autowired
    StoSetPriceRepository stoSetPriceRepository;
    @Autowired
    StoUploadComImageRepository stoUploadComImageRepository;
    @Autowired
    StoSetComCategoryRepository stoSetComCategoryRepository;

    private  final StoUploadMysteryBoxCommodityRequestMapper stoUploadMysteryBoxCommodityRequestMapper=
            StoUploadMysteryBoxCommodityRequestMapper.INSTANCE;

    private  final StoUploadRegularCommodityRequestMapper stoUploadRegularCommodityRequestMapper=
            StoUploadRegularCommodityRequestMapper.INSTANCE;

    @Transactional
    public  StoUploadMysteryCommodityResponseDTO UploadRegularCommodity(StoUploadRegularCommodityRequestDTO requestDTO) throws ParseException {
        CommodityEntity newCom=stoUploadRegularCommodityRequestMapper.dtoToComEntity(requestDTO);
        RegularCommodityEntity newRegularCom=new RegularCommodityEntity();
        LocalDateTime nowTime=LocalDateTime.now();
        newCom.setComUploadDate(Timestamp.valueOf(nowTime));
        newCom.setPraiseRate(BigDecimal.valueOf(100));
        CommodityEntity inserted_com=stoUploadCommodityRepository.save(newCom);
        newRegularCom.setRegularComId(inserted_com.getComId());
        newRegularCom.setComExpirationDate(StrDataToTimestamp(requestDTO.getCom_expirationDate()));
        newRegularCom.setComProducedDate(StrDataToTimestamp(requestDTO.getCom_producedDate()));
        RegularCommodityEntity inserted_regular_com=stoUploadRegularCommodityRepository.save(newRegularCom);
        for(PriceCurveInfo curveInfo : requestDTO.getPrice_curve()){
            CommodityPriceCurveEntity newPriceNode= new CommodityPriceCurveEntity();
            newPriceNode.setComId(inserted_com.getComId());
            newPriceNode.setComPcPrice(BigDecimal.valueOf(curveInfo.getCom_pc_price()));
            System.out.println("curveInfo.getCom_pc_time()"+curveInfo.getCom_pc_time());
            newPriceNode.setComPcTime(Timestamp.valueOf(curveInfo.getCom_pc_time()));
            stoSetPriceRepository.save(newPriceNode);
        }
        for(ComCategory comCategory : requestDTO.getCategories()){
            CommodityCategoriesEntity newComCategory=new CommodityCategoriesEntity();
            newComCategory.setComCategory(comCategory.getCom_category());
            newComCategory.setComId(inserted_com.getComId());
            stoSetComCategoryRepository.save(newComCategory);
        }

        StoUploadMysteryCommodityResponseDTO responseDTO=new StoUploadMysteryCommodityResponseDTO();
        responseDTO.setCom_Id(inserted_com.getComId());
        return responseDTO;
    }
    @Transactional
    public  StoUploadRegularCommodityResponseDTO UploadMysteryBoxCommodity(StoUploadMysteryBoxCommodityRequestDTO requestDTO){
        CommodityEntity  newCom=
                stoUploadMysteryBoxCommodityRequestMapper.dtoToComEntity(requestDTO);
        MysteryBoxEntity mysteryBox=
                stoUploadMysteryBoxCommodityRequestMapper.dtoToMysteryBoxEntity(requestDTO);
        LocalDateTime nowTime=LocalDateTime.now();
        newCom.setComUploadDate(Timestamp.valueOf(nowTime));
        newCom.setPraiseRate(BigDecimal.valueOf(100));
        CommodityEntity inserted_com=stoUploadCommodityRepository.save(newCom);
        mysteryBox.setMysteryBoxId(inserted_com.getComId());
        mysteryBox.setItemImage("待设置");
        stoUploadMysteryBoxCommodityRepository.save(mysteryBox);
        StoUploadRegularCommodityResponseDTO responseDTO=new StoUploadRegularCommodityResponseDTO();
        responseDTO.setCom_ID(inserted_com.getComId());
        return responseDTO;
    }

    @Transactional

    public void UploadCommodityImage(StoUploadImageRequestDTO formDTO) throws IOException {
        Integer com_image_index=0;
        System.out.println("formDTO.getCom_id() ： "+formDTO.getCom_id());

        for(MultipartFile image_file : formDTO.getImages()){
            InputStream image_obs=image_file.getInputStream();
            String file_name=String.valueOf(formDTO.getCom_id())+"_"+String.valueOf(com_image_index)+".jpg";
            com_image_index++;
            uploadInputStream("commodity_image/",file_name,image_obs);
            CommodityImageEntity commodityImage=new CommodityImageEntity();
            commodityImage.setComId(formDTO.getCom_id());
            commodityImage.setComImage("commodity_image/"+file_name);
            stoUploadComImageRepository.save(commodityImage);
        }
    }


    public Timestamp StrDataToTimestamp(String data) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedExDate = dateFormat.parse(data);
        return new Timestamp(parsedExDate.getTime());
    }

}

