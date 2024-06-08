package org.tju.food_007.service.pub.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.register.CustomRegistrationResponseDTO;
import org.tju.food_007.dto.pub.register.StoreRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.mapper.StoreRegistrationRequestMapper;
import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.StoreRegistrationRepository;
import org.tju.food_007.repository.pub.register.UserRegistrationRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Service
public class StoreRegistrationService {
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    StoreRegistrationRepository storeRegistrationRepository;

    private final StoreRegistrationRequestMapper storeRegistrationRequestMapper =
            StoreRegistrationRequestMapper.INSTANCE;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9\\W]{6,}$");

    @Transactional
    public CustomRegistrationResponseDTO StoreRegister(StoreRegistrationRequestDTO request) {
        CustomRegistrationResponseDTO response = new CustomRegistrationResponseDTO();

        if (!isValidPhoneNumber(request.getUser_phone())) {
            response.setMsg("电话号码格式不正确，必须为11位数字");
            return response;
        }

        if (!isValidPassword(request.getUser_password())) {
            response.setMsg("密码长度必须不低于6位，并且包含字母、数字和特殊符号");
            return response;
        }

        if (!isValidGender(request.getUser_gender())) {
            response.setMsg("性别必须为0或1");
            return response;
        }

        if (!isValidIntroduction(request.getSto_introduction())) {
            response.setMsg("介绍长度必须不超过50个字符");
            return response;
        }

        if (!isValidOpeningClosingTime(request.getSto_openingTime().toString(), request.getSto_closingTime().toString())) {
            response.setMsg("关门时间必须晚于开门时间");
            return response;
        }

        UserEntity newUser = storeRegistrationRequestMapper.requestToUserEntity(request);
        StoreEntity newSto = storeRegistrationRequestMapper.requestToStoEntity(request);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        System.out.println("新商家注册，当前时间为: " + formattedDateTime);

        newUser.setUserState(1);
        newUser.setUserBalance(BigDecimal.valueOf(0));
        newUser.setUserRegTime(Timestamp.valueOf(now));
        newUser.setUserType(1);

        if (userRegistrationRepository.existsByUserPhone(newUser.getUserPhone())) {
            System.out.println("该号码已存在：" + newUser.getUserPhone());
            response.setMsg("该号码已存在");
        } else {
            UserEntity temp = userRegistrationRepository.save(newUser);
            System.out.println("User已经存入，ID为" + temp.getUserId());
            response.setMsg("User成功注册");

            newSto.setStoId(temp.getUserId());
            newSto.setStoState(1);
            StoreEntity tempSto = storeRegistrationRepository.save(newSto);
            response.setMsg("Store成功注册");
            response.setUser_type(1);
            response.setUser_id(temp.getUserId());
        }
        return response;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches()&&password.length()<=12;
    }

    private boolean isValidGender(int gender) {
        return gender == 0 || gender == 1;
    }

    private boolean isValidIntroduction(String introduction) {
        return introduction != null && introduction.length() <= 50;
    }

    private boolean isValidOpeningClosingTime(String openingTime, String closingTime) {
        try {
            LocalTime open = LocalTime.parse(openingTime);
            LocalTime close = LocalTime.parse(closingTime);
            return close.isAfter(open);
        } catch (Exception e) {
            return false;
        }
    }
}
