package org.tju.food_007.service.pub.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tju.food_007.dto.pub.login.UserLoginRequestDTO;
import org.tju.food_007.dto.pub.login.UserLoginResponseDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationResponseDTO;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.pub.login.UserLoginRepository;
import org.tju.food_007.repository.pub.register.CustomRegistrationRepository;
import org.tju.food_007.repository.pub.register.UserRegistrationRepository;
import org.tju.food_007.service.pub.login.UserLoginService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerRegistrationServiceTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserRegistrationRepository userRegistrationRepository;
    @Mock
    private CustomRegistrationRepository customRegistrationRepository;

    @InjectMocks
    private CustomerRegistrationService customerRegistrationService;

    @BeforeEach
    public void setUp() {
        System.out.println("开始测试");
        MockitoAnnotations.openMocks(this);
    }

//    @ParameterizedTest(name = "测试用例 {0}")
//    @MethodSource("userLoginProvider")
//    @DisplayName("测试用户登录服务类")
//    public void testUserRegister(String testCaseId, CustomRegistrationRequestDTO requestDTO, CustomRegistrationResponseDTO expectedResult) throws JsonProcessingException {
//        mockUserRegistrationRepository(requestDTO);
//        mockCustomRegistrationRepository(requestDTO);
//
//        CustomRegistrationResponseDTO realResult = customerRegistrationService.UserRegister(requestDTO);
//        String expectedJson = objectMapper.writeValueAsString(expectedResult);
//        String actualJson = objectMapper.writeValueAsString(realResult);
//
//        assertEquals(expectedJson, actualJson);
//    }

//    private void mockUserRegistrationRepository(CustomRegistrationRequestDTO requestDTO) {
//        UserEntity userEntity = new UserEntity();
//        if ("13837829689".equals(requestDTO.getUser_phone())) {
//            userEntity.setUserId(1);
//            userEntity.setUserType(0);
//            userEntity.setUserPhone("13837829689");
//            userEntity.setUserPassword("123456");
//            when(userLoginRepository.findByUserPhone(requestDTO.getUser_phone())).thenReturn(userEntity);
//        } else if ("15821295304".equals(requestDTO.getUser_phone())) {
//            userEntity.setUserId(2);
//            userEntity.setUserType(1);
//            userEntity.setUserPhone("15821295304");
//            userEntity.setUserPassword("123456");
//            when(userLoginRepository.findByUserPhone(requestDTO.getUser_phone())).thenReturn(userEntity);
//        } else {
//            when(userLoginRepository.findByUserPhone(requestDTO.getUser_phone())).thenReturn(null);
//        }
//    }

}