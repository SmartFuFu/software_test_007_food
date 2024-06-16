package org.tju.food_007.service.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.cus.indent.ChangeIndStateRequestDTO;
import org.tju.food_007.dto.cus.indent.GenerateIndentRequestDTO;
import org.tju.food_007.dto.cus.indent.IndentCommodity;
import org.tju.food_007.dto.cus.mapper.GenerateIndentComRequstMapper;
import org.tju.food_007.dto.cus.mapper.GenerateIndentRequestMapper;
import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.model.IndentCommodityEntity;
import org.tju.food_007.model.IndentEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.com.Information.CommodityDetailRepository;
import org.tju.food_007.repository.cus.indent.GenerateIndentComRepository;
import org.tju.food_007.repository.cus.indent.GenerateIndentRepository;
import org.tju.food_007.repository.pub.login.UserLoginRepository;
import org.tju.food_007.repository.sto.UserUploadLogoImageUserRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class GenerateIndentService {
    @Autowired
    GenerateIndentRepository generateIndentRepository;
    @Autowired
    GenerateIndentComRepository generateIndentComRepository;
    @Autowired
    UserUploadLogoImageUserRepository userUploadLogoImageUserRepository;
    @Autowired
    CommodityDetailRepository commodityDetailRepository;
    private final GenerateIndentComRequstMapper generateIndentComRequstMapper = GenerateIndentComRequstMapper.INSTANCE;
    private final GenerateIndentRequestMapper generateIndentRequestMapper = GenerateIndentRequestMapper.INSTANCE;

    public static String generateVerificationCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional
    public void ChangeIndentState(ChangeIndStateRequestDTO requestDTO) {
        IndentEntity aimed_ind = generateIndentRepository.findByIndId(requestDTO.getInd_id());
        aimed_ind.setIndState(requestDTO.getInd_state());
        generateIndentRepository.save(aimed_ind);
    }

    @Transactional
    public String GenerateIndent(GenerateIndentRequestDTO requestDTO) {
        // 综合判断逻辑
        String validationError = validateGenerateIndentRequest(requestDTO);
        if (validationError != null) {
            return validationError;
        }

        UserEntity targetUser = userUploadLogoImageUserRepository.findByUserId(Integer.valueOf(requestDTO.getCus_Id()));
        if (targetUser == null) {
            return "顾客ID不存在";
        }

        if(targetUser.getUserType()==1){
            return "顾客ID不存在";
        }

        if (targetUser.getUserBalance().doubleValue() < requestDTO.getInd_money()) {
            System.out.println("余额不足");
            return "余额不足";
        }

        String telephoneNumber = String.valueOf(targetUser.getUserPhone().subSequence(7, 11));
        String teleCode = telephoneNumber + "-" + String.valueOf(generateVerificationCode().subSequence(0, 4));
        IndentEntity newIndent = generateIndentRequestMapper.dtoToEntity(requestDTO);
        newIndent.setIndVerificationCode(teleCode);
        newIndent.setFoodQualityScore(BigDecimal.valueOf(5.0));
        newIndent.setServiceQualityScore(BigDecimal.valueOf(5.0));
        newIndent.setPricePerformanceRatio(BigDecimal.valueOf(5.0));
        if (newIndent.getDeliveryMethod() == 0) {
            newIndent.setIndState(2);
        } else if (newIndent.getDeliveryMethod() == 1) {
            newIndent.setIndState(0);
        } else {
            System.out.println("出现了问题");
            return "出现了问题";
        }
        CommodityEntity aimed_com = commodityDetailRepository.findByComId(Integer.valueOf(requestDTO.getCom_arr().getFirst().getCom_id()));
        if (aimed_com == null) {
            return "商品ID不存在";
        }
        newIndent.setStoId(aimed_com.getStoId());
        IndentEntity inserted_ind = generateIndentRepository.save(newIndent);

        for (IndentCommodity com : requestDTO.getCom_arr()) {
            IndentCommodityEntity newCom = generateIndentComRequstMapper.dtoToEntity(com);
            System.out.println(newCom.getComId());
            newCom.setIndId(inserted_ind.getIndId());
            newCom.setRatingType(1);
            CommodityEntity aim_com = commodityDetailRepository.findByComId(newCom.getComId());
            if (aim_com == null) {
                return "商品ID不存在";
            }
            aim_com.setComLeft(aim_com.getComLeft() - Integer.parseInt(com.getInd_quantity()));
            commodityDetailRepository.save(aim_com);
            generateIndentComRepository.save(newCom);
        }

        return "订单生成成功";
    }

    private String validateGenerateIndentRequest(GenerateIndentRequestDTO requestDTO) {
        try {
            int cusId = Integer.parseInt(requestDTO.getCus_Id());
            if (cusId <= 0||cusId>1000000) throw new NumberFormatException("无效的客户ID");
        } catch (NumberFormatException e) {
            return "无效的客户ID";
        }

        if (requestDTO.getInd_notes().length() > 50) {
            return "备注长度超出限制";
        }

        if (requestDTO.getDelivery_method() < 0 || requestDTO.getDelivery_method() > 1) {
            return "无效的配送方式";
        }

        if (requestDTO.getInd_money() < 0 || requestDTO.getInd_money() >= 10000) {
            return "无效的订单金额";
        }

        if (requestDTO.getDelivery_altitude() < 0 || requestDTO.getDelivery_altitude() > 90 ||
                requestDTO.getDelivery_longitude() < 0 || requestDTO.getDelivery_longitude() > 180) {
            return "无效的配送位置坐标";
        }

        for (IndentCommodity com : requestDTO.getCom_arr()) {
            try {
                int comId = Integer.parseInt(com.getCom_id());
                if (comId <= 0||comId>1000000) throw new NumberFormatException("无效的商品ID");
            } catch (NumberFormatException e) {
                return "无效的商品ID";
            }

            try {
                int quantity = Integer.parseInt(com.getInd_quantity());
                if (quantity <= 0 || quantity >= 10000) throw new NumberFormatException("无效的商品数量");
            } catch (NumberFormatException e) {
                return "无效的商品数量";
            }

            try {
                double money = Double.parseDouble(com.getCommodity_money());
                if (money < 0 || money >= 10000) throw new NumberFormatException("无效的商品金额");
            } catch (NumberFormatException e) {
                return "无效的商品金额";
            }
        }

        return null;
    }
}
