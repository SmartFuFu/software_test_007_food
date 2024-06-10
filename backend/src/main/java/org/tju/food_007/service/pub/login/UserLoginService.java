package org.tju.food_007.service.pub.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.login.UserLoginRequestDTO;
import org.tju.food_007.dto.pub.login.UserLoginResponseDTO;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.pub.login.UserLoginRepository;

import java.util.regex.Pattern;

/**
 * @author WGY
 * @create 2024-03-10-16:53
 */
@Service
public class UserLoginService {
    @Autowired
    UserLoginRepository userLoginRepository;
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{11}$");
    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }
    @Transactional
    public UserLoginResponseDTO UserLogin(UserLoginRequestDTO requestDTO){
        UserLoginResponseDTO responseDTO=new UserLoginResponseDTO();
        UserEntity targetUser=userLoginRepository.findByUserPhone(requestDTO.getUser_phone());
        if (!isValidPhoneNumber(requestDTO.getUser_phone())) {
            responseDTO.setUser_type(-1);
            responseDTO.setMsg("电话格式错误");
            responseDTO.setUser_id(-1);
            return responseDTO;
        }
        if (targetUser!=null){
            if(targetUser.getUserPassword().equals(requestDTO.getUser_password()))
            {
                responseDTO.setUser_type(targetUser.getUserType());
                responseDTO.setMsg("登录成功");
                responseDTO.setUser_id(targetUser.getUserId());
            }
            else
            {
                {
                    responseDTO.setUser_type(-1);
                    responseDTO.setMsg("密码错误");
                    responseDTO.setUser_id(-1);
                }

            }
        }
        else {
            {
                responseDTO.setUser_type(-1);
                responseDTO.setMsg("账号不存在");
                responseDTO.setUser_id(-1);
                
            }
        }
        return responseDTO;
    }
}
