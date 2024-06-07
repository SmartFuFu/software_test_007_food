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

/**
 * @author WGY
 * @create 2024-03-17-16:00
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;



@Service
public class GenerateIndentService {
    @Autowired
    GenerateIndentRepository generateIndentRepository;
    @Autowired
    GenerateIndentComRepository generateIndentComRepository;
    @Autowired
    UserLoginRepository userLoginRepository;
    @Autowired
    UserUploadLogoImageUserRepository userUploadLogoImageUserRepository;
    @Autowired
    CommodityDetailRepository commodityDetailRepository;
    private final GenerateIndentComRequstMapper generateIndentComRequstMapper=GenerateIndentComRequstMapper.INSTANCE;
    private final GenerateIndentRequestMapper generateIndentRequestMapper=GenerateIndentRequestMapper.INSTANCE;

    public static String generateVerificationCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    @Transactional
    public void ChangeIndentState(ChangeIndStateRequestDTO requestDTO){
        IndentEntity aimed_ind=generateIndentRepository.findByIndId(requestDTO.getInd_id());
        aimed_ind.setIndState(requestDTO.getInd_state());
        generateIndentRepository.save(aimed_ind);
    }

    @Transactional
    public String GenerateIndent(GenerateIndentRequestDTO requestDTO){
        UserEntity targetUser=userUploadLogoImageUserRepository.findByUserId(Integer.valueOf(requestDTO.getCus_Id()));
        if(targetUser.getUserBalance().doubleValue()<requestDTO.getInd_money()){
            System.out.println("余额不足");
            return "余额不足";
        }
        String telephoneNumber=String.valueOf(targetUser.getUserPhone().subSequence(7,11));
        String teleCode=telephoneNumber+"-"+String.valueOf(generateVerificationCode().subSequence(0,4));
        IndentEntity newIndent=generateIndentRequestMapper.dtoToEntity(requestDTO);
        newIndent.setIndVerificationCode(teleCode);
        newIndent.setFoodQualityScore(BigDecimal.valueOf(5.0));
        newIndent.setServiceQualityScore(BigDecimal.valueOf(5.0));
        newIndent.setPricePerformanceRatio(BigDecimal.valueOf(5.0));
        if(newIndent.getDeliveryMethod()==0){
            newIndent.setIndState(2);
        }
        else if(newIndent.getDeliveryMethod()==1){
            newIndent.setIndState(0);
        }
        else {
            System.out.println("出现了问题");
        }
        CommodityEntity aimed_com=commodityDetailRepository.findByComId(Integer.valueOf(requestDTO.getCom_arr().getFirst().getCom_id()));
        newIndent.setStoId(aimed_com.getStoId());
        IndentEntity inserted_ind=generateIndentRepository.save(newIndent);
        for (IndentCommodity com : requestDTO.getCom_arr()) {
            IndentCommodityEntity newCom= generateIndentComRequstMapper.dtoToEntity(com);
            System.out.println(newCom.getComId());
            newCom.setIndId(inserted_ind.getIndId());
            newCom.setRatingType(1);
            CommodityEntity aim_com=commodityDetailRepository.findByComId(newCom.getComId());
            aim_com.setComLeft(aim_com.getComLeft()-Integer.parseInt(com.getInd_quantity()));
            commodityDetailRepository.save(aim_com);
            generateIndentComRepository.save(newCom);
        }

        return "订单生成成功";
    }

    @Transactional
    public String ModifyCode(){
        ArrayList<IndentEntity> indents=generateIndentRepository.findAll();
        for(IndentEntity ind : indents){
            UserEntity user=userLoginRepository.findByUserId(ind.getCusId());
            String telephoneNumber=String.valueOf(user.getUserPhone().subSequence(7,11));
            String teleCode=telephoneNumber+"-"+String.valueOf(generateVerificationCode().subSequence(0,4));
            ind.setIndVerificationCode(teleCode);
            generateIndentRepository.save(ind);
        }
        return "success";
    }

    @Transactional
    public String ModifyStoID(){
        ArrayList<IndentEntity> indents=generateIndentRepository.findAll();
        for(IndentEntity ind : indents){
            ArrayList<IndentCommodityEntity> list= generateIndentComRepository.findByIndId(ind.getIndId());
            CommodityEntity com= commodityDetailRepository.findByComId(list.getFirst().getComId());
            ind.setStoId(com.getStoId());
        }
        return "success";
    }
}
