package org.tju.food_007.service.cus.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.cus.info.GetCusInfoResponseDTO;
import org.tju.food_007.model.CustomerEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.cus.info.GetCusInfoRepository;
import org.tju.food_007.repository.cus.info.GetCusInfoResponseMapper;
import org.tju.food_007.repository.cus.info.GetUserInfoRepository;


/**
 * @author WGY
 * @create 2024-04-01-17:52
 */
@Service
public class GetCusInfoService {
    @Autowired
    GetCusInfoRepository getCusInfoRepository;
    @Autowired
    GetUserInfoRepository getUserInfoRepository;
    private final GetCusInfoResponseMapper getCusInfoResponseMapper
            =GetCusInfoResponseMapper.INSTANCE;
    public  GetCusInfoResponseDTO GetCusInfo(Integer cus_id){
        GetCusInfoResponseDTO responseDTO= new GetCusInfoResponseDTO();

        UserEntity target_user=getUserInfoRepository.findByUserId(cus_id);
        if(target_user!=null) {
            responseDTO = getCusInfoResponseMapper.userEntityToDto(target_user);
            CustomerEntity target_cus = getCusInfoRepository.findByCusId(cus_id);
            responseDTO.setUser_logo(target_cus.getUserLogo());
            responseDTO.setCus_nickname(target_cus.getCusNickname());
        }
        else{
            System.out.println("cus_id对应用户不存在");
        }
        return  responseDTO;
    }
}
