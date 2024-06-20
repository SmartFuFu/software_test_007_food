package org.tju.food_007.service.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tju.food_007.dto.sto.UserUploadLogoImageRequestDTO;

import org.tju.food_007.model.CustomerEntity;
import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.sto.UserInformatonDetailRepository;
import org.tju.food_007.repository.sto.UserUploadLogoImageCusRepository;
import org.tju.food_007.repository.sto.UserUploadLogoImageStoreRepository;
import org.tju.food_007.repository.sto.UserUploadLogoImageUserRepository;

import java.io.IOException;
import java.io.InputStream;

import static org.tju.food_007.utils.ObsOperationTool.uploadInputStream;

/**
 * @author WGY
 * @create 2024-04-17-14:52
 */
@Service
public class UserUploadLogoImageService {
    @Autowired
    UserUploadLogoImageUserRepository userUploadLogoImageUserRepository;

    @Autowired
    UserUploadLogoImageStoreRepository userUploadLogoImageStoreRepository;

    @Autowired
    UserUploadLogoImageCusRepository userUploadLogoImageCusRepository;

    @Autowired
    UserInformatonDetailRepository userInformatonDetailRepository;
    public String userUploadLogo(UserUploadLogoImageRequestDTO requestDTO) throws IOException {
        UserEntity aimed_user=userInformatonDetailRepository.findByUserId(requestDTO.getUser_id());
        if(aimed_user==null)
            return "用户id不存在";
        InputStream image_obs=requestDTO.getImage().getInputStream();
        String file_name=String.valueOf(requestDTO.getUser_id())+"_UserLogo.jpg";
        UserEntity targetUser= userUploadLogoImageUserRepository.findByUserId(requestDTO.getUser_id());
        if (targetUser.getUserType()==0){
            CustomerEntity newCus=userUploadLogoImageCusRepository.findByCusId(requestDTO.getUser_id());
            uploadInputStream("cus_logo/",file_name,image_obs);
            newCus.setUserLogo("cus_logo/"+file_name);
            userUploadLogoImageCusRepository.save(newCus);
        }
        else {
            StoreEntity newSto = userUploadLogoImageStoreRepository.findByStoId(requestDTO.getUser_id());
            uploadInputStream("sto_logo/", file_name, image_obs);
            newSto.setStoLogo("sto_logo/" + file_name);
            userUploadLogoImageStoreRepository.save(newSto);
        }

        return "success";
    }
}
