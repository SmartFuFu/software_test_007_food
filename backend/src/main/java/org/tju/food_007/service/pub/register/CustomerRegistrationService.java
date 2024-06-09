package org.tju.food_007.service.pub.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.register.CustomRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationResponseDTO;
import org.tju.food_007.dto.pub.register.mapper.CustomRegistrationRequestMapper;
import org.tju.food_007.model.CustomerEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.pub.register.CustomRegistrationRepository;
import org.tju.food_007.repository.pub.register.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.repository.pub.register.CustomRegistrationRepository;
import org.tju.food_007.repository.pub.register.UserRegistrationRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author WGY
 * @create 2024-03-05-14:58
 */
@Service
public class CustomerRegistrationService {
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    CustomRegistrationRepository customRegistrationRepository;
    private final CustomRegistrationRequestMapper customRegistrationRequestMapper =
            CustomRegistrationRequestMapper.INSTANCE;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9\\W]{6,}$");
    private static final Pattern PAY_PASSWORD_PATTERN = Pattern.compile("^\\d{6,10}$");

    @Transactional
    public CustomRegistrationResponseDTO UserRegister(CustomRegistrationRequestDTO request) {
        UserEntity newUser = customRegistrationRequestMapper.requestToUserEntity(request);
        CustomRegistrationResponseDTO response=new CustomRegistrationResponseDTO();
        if (!isValidPhoneNumber(request.getUser_phone())) {
            response.setMsg("电话号码格式不正确，必须为11位数字");
            response.setUser_type(0);
            response.setUser_id(-1);
            return response;
        }

        if (!isValidPassword(request.getUser_password())) {
            response.setMsg("密码长度必须不低于6位，并且包含字母、数字和特殊符号");
            response.setUser_type(0);
            response.setUser_id(-1);
            return response;
        }

        if (!isValidGender(request.getUser_gender())) {
            response.setMsg("性别必须为0或1");
            response.setUser_type(0);
            response.setUser_id(-1);
            return response;
        }

        if (!isValidNickname(request.getCus_nickname())) {
            response.setMsg("昵称长度必须小于10");
            response.setUser_type(0);
            response.setUser_id(-1);
            return response;
        }

        if (!isValidPayPassword(request.getCus_payPassword())) {
            response.setMsg("支付密码长度必须为6到10位数字");
            response.setUser_type(0);
            response.setUser_id(-1);
            return response;
        }

        LocalDateTime now = LocalDateTime.now();

        // 设置日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化当前日期时间
        String formattedDateTime = now.format(formatter);

        // 输出格式化后的日期时间
        System.out.println("新顾客注册，当前时间为: " + formattedDateTime);

        newUser.setUserState(1);
        newUser.setUserBalance(BigDecimal.valueOf(0));

        newUser.setUserRegTime(Timestamp.valueOf(now));
        newUser.setUserType(0);
        if (userRegistrationRepository.existsByUserPhone(newUser.getUserPhone())) {
            System.out.println("该号码已存在："+newUser.getUserPhone());
            response.setMsg("该号码已存在");
            response.setUser_type(0);
            response.setUser_id(-1);
        }
        else {
            UserEntity temp=userRegistrationRepository.save(newUser);
            System.out.println("User已经存入，ID为" + temp.getUserId());
            response.setMsg("User成功注册");
            response.setUser_type(0);
            response.setUser_id(temp.getUserId());
        }

        CustomerEntity newCus = customRegistrationRequestMapper.requestToCusEntity(request);

        if(response.getUser_id()==-1){
            return response;
        }
        if(userRegistrationRepository.existsByUserId(response.getUser_id())){
            response.setMsg("对应ID的user存在:"+response);
            newCus.setCusId(response.getUser_id());
            newCus.setCusState(1);
            System.out.println("newCus:"+newCus.getCusId());
            customRegistrationRepository.save(newCus);
            response.setMsg("顾客成功注册");
            response.setUser_type(0);
            response.setUser_id(response.getUser_id());
        }
        else {
            response.setMsg("对应ID的user不存在:"+response);
            response.setUser_type(0);
            response.setUser_id(-1);
        }
        return response;
    }

//    @Transactional
//    public CustomRegistrationResponseDTO CusRegister(CustomRegistrationRequestDTO request,CustomRegistrationResponseDTO cus){
//        CustomerEntity newCus = customRegistrationRequestMapper.requestToCusEntity(request);
//        CustomRegistrationResponseDTO response=new CustomRegistrationResponseDTO();
//        if(cus.getUser_id()==-1){
//            response=cus;
//            return response;
//        }
//        if(userRegistrationRepository.existsByUserId(cus.getUser_id())){
//            response.setMsg("对应ID的user存在:"+cus);
//            newCus.setCusId(cus.getUser_id());
//            newCus.setCusState(1);
//            System.out.println("newCus:"+newCus.getCusId());
//            customRegistrationRepository.save(newCus);
//            response.setMsg("顾客成功注册");
//            response.setUser_type(0);
//            response.setUser_id(cus.getUser_id());
//        }
//        else {
//            response.setMsg("对应ID的user不存在:"+cus);
//            response.setUser_type(0);
//            response.setUser_id(-1);
//        }
//        return response;
//    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isValidPassword(String password) {
        // 确保密码仅包含字母、数字和特殊符号，且长度不低于6位
        return PASSWORD_PATTERN.matcher(password).matches() && !password.matches(".*[^a-zA-Z0-9\\W].*")&&password.length()<=12;
    }

    private boolean isValidGender(int gender) {
        return gender == 0 || gender == 1;
    }

    private boolean isValidNickname(String nickname) {
        return nickname != null && nickname.length() < 10;
    }

    private boolean isValidPayPassword(String payPassword) {
        return PAY_PASSWORD_PATTERN.matcher(payPassword).matches();
    }

}






















