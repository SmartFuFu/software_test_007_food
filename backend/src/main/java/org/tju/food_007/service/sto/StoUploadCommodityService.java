package org.tju.food_007.service.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import org.tju.food_007.dto.sto.*;
import org.tju.food_007.dto.sto.mapper.StoUploadMysteryBoxCommodityRequestMapper;
import org.tju.food_007.dto.sto.mapper.StoUploadRegularCommodityRequestMapper;
import org.tju.food_007.model.*;
import org.tju.food_007.repository.pub.register.StoreRegistrationRepository;
import org.tju.food_007.repository.sto.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.SyncFailedException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    StoreRegistrationRepository storeRegistrationRepository;

    String[] validCategories = {"便当", "水果", "肉类", "零食", "饮品"};


    private  final StoUploadMysteryBoxCommodityRequestMapper stoUploadMysteryBoxCommodityRequestMapper=
            StoUploadMysteryBoxCommodityRequestMapper.INSTANCE;

    private  final StoUploadRegularCommodityRequestMapper stoUploadRegularCommodityRequestMapper=
            StoUploadRegularCommodityRequestMapper.INSTANCE;

    @Transactional
    public StoUploadMysteryCommodityResponseDTO UploadRegularCommodity(StoUploadRegularCommodityRequestDTO requestDTO) throws ParseException {
        StoUploadMysteryCommodityResponseDTO responseDTO=new StoUploadMysteryCommodityResponseDTO();
        String validationError = validateUploadRegularCommodityRequest(requestDTO);
        if (validationError != null) {
            System.out.println(validationError);
            responseDTO.setCom_Id(-1);
            return responseDTO;
        }
        StoreEntity aimed_sto=storeRegistrationRepository.findByStoId(requestDTO.getSto_ID());
        if(aimed_sto==null) {
            System.out.println("商家不存在");
            responseDTO.setCom_Id(-1);
            return responseDTO;
        }

        CommodityEntity newCom=stoUploadRegularCommodityRequestMapper.dtoToComEntity(requestDTO);
        RegularCommodityEntity newRegularCom=new RegularCommodityEntity();
        LocalDateTime nowTime=LocalDateTime.now();
        newCom.setComUploadDate(Timestamp.valueOf(nowTime));
        newCom.setPraiseRate(BigDecimal.valueOf(100));
        CommodityEntity inserted_com=stoUploadCommodityRepository.save(newCom);
        newRegularCom.setRegularComId(inserted_com.getComId());
        newRegularCom.setComExpirationDate(StrDataToTimestamp(requestDTO.getCom_expirationDate()));
        newRegularCom.setComProducedDate(StrDataToTimestamp(requestDTO.getCom_producedDate()));
        stoUploadRegularCommodityRepository.save(newRegularCom);
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


        responseDTO.setCom_Id(inserted_com.getComId());
        return responseDTO;
    }
    @Transactional
    public StoUploadRegularCommodityResponseDTO UploadMysteryBoxCommodity(StoUploadMysteryBoxCommodityRequestDTO requestDTO){
        String validationError = validateUploadMysteryCommodityRequest(requestDTO);
        StoUploadRegularCommodityResponseDTO responseDTO=new StoUploadRegularCommodityResponseDTO();
        if (validationError != null) {
            System.out.println(validationError);
            responseDTO.setCom_ID(-1);
            return responseDTO;
        }
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


    private Timestamp StrDataToTimestamp(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new Timestamp(dateFormat.parse(dateStr).getTime());
    }
    private Timestamp stripTime(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new Timestamp(dateFormat.parse(dateFormat.format(timestamp)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误", e);
        }
    }
    private String validateUploadRegularCommodityRequest(StoUploadRegularCommodityRequestDTO requestDTO) {
        // 验证商品名称长度
        if (requestDTO.getCom_name().length() > 30) {
            return "商品名称不能超过30个字符";
        }

        // 验证商品介绍长度
        if (requestDTO.getCom_introduction().length() > 50) {
            return "商品介绍不能超过50个字符";
        }

        // 验证商品剩余数量
        if (requestDTO.getCom_left() < 0 || requestDTO.getCom_left() > 9999) {
            return "商品剩余数量必须在0到9999之间";
        }

        // 验证商品类型
        if (requestDTO.getCom_type() != 0) {
            return "商品类型错误";
        }

        // 验证商品过期日期和生产日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp expirationDate = stripTime(Timestamp.valueOf(requestDTO.getCom_expirationDate()));
        Timestamp producedDate = stripTime(Timestamp.valueOf(requestDTO.getCom_producedDate()));
        Timestamp now = stripTime(new Timestamp(System.currentTimeMillis()));

        if (expirationDate.before(producedDate)) {
            return "过期日期不能早于生产日期";
        }

        if (expirationDate.before(now)) {
            return "不能上传已过期的商品";
        }

        // 验证价格曲线时间
        List<PriceCurveInfo> priceCurve = requestDTO.getPrice_curve();
        if (!priceCurve.isEmpty()) {
            Timestamp firstCurveTime = stripTime(Timestamp.valueOf(priceCurve.get(0).getCom_pc_time()));
            Timestamp lastCurveTime = stripTime(Timestamp.valueOf(priceCurve.get(priceCurve.size() - 1).getCom_pc_time()));

            if (!firstCurveTime.equals(producedDate)) {
                System.out.println(firstCurveTime);
                System.out.println(producedDate);
                return "价格曲线的第一个节点必须是生产日期";
            }

            if (!lastCurveTime.equals(expirationDate)) {
                return "价格曲线的最后一个节点必须是过期日期";
            }

            for (int i = 1; i < priceCurve.size() - 1; i++) {
                Timestamp priceCurveTime = stripTime(Timestamp.valueOf(priceCurve.get(i).getCom_pc_time()));
                System.out.println();
                if (priceCurveTime.before(now) || priceCurveTime.after(expirationDate)) {
                    return "价格曲线时间必须在今天到过期日期之间";
                }
            }
        }

        // 验证商品种类
        String[] validCategories = {"便当", "水果", "肉类", "零食", "饮品"};
        for (ComCategory comCategory : requestDTO.getCategories()) {
            boolean valid = false;
            for (String category : validCategories) {
                if (category.equals(comCategory.getCom_category())) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                return "商品种类不合法";
            }
        }

        return null;
    }

    private String validateUploadMysteryCommodityRequest(StoUploadMysteryBoxCommodityRequestDTO requestDTO) {
        // 验证商品名称长度
        if (requestDTO.getCom_name().length() > 30) {
            return "商品名称不能超过30个字符";
        }

        // 验证商品介绍长度
        if (requestDTO.getCom_introduction().length() > 50) {
            return "商品介绍不能超过50个字符";
        }

        // 验证商品剩余数量
        if (requestDTO.getCom_left() < 1 || requestDTO.getCom_left() > 9999) {
            return "商品剩余数量必须在1到9999之间";
        }

        if (requestDTO.getHighest_price().doubleValue()< 1 || requestDTO.getHighest_price().doubleValue()> 9999) {
            return "商品最高价必须在1到9999之间";
        }
        if (requestDTO.getLowest_price().doubleValue()< 0 || requestDTO.getLowest_price().doubleValue()> 9999) {
            return "商品最低价必须在0到9999之间";
        }
        if(requestDTO.getHighest_price().doubleValue()<requestDTO.getLowest_price().doubleValue()){
            return "商品最低价必须低于最高价";
        }

        // 验证商品类型
        if (requestDTO.getCom_type() != 1) {
            return "商品类型错误";
        }

        // 验证商品过期日期和生产日期
        // 验证商品种类
        String[] validCategories = {"便当", "水果", "肉类", "零食", "饮品"};
        for (ComCategory comCategory : requestDTO.getCategories()) {
            boolean valid = false;
            for (String category : validCategories) {
                if (category.equals(comCategory.getCom_category())) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                return "商品种类不合法";
            }
        }

        return null;
    }
}

