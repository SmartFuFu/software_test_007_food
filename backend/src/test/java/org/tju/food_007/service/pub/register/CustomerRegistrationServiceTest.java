package org.tju.food_007.service.pub.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tju.food_007.dto.pub.login.UserLoginRequestDTO;
import org.tju.food_007.dto.pub.login.UserLoginResponseDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationResponseDTO;
import org.tju.food_007.dto.pub.register.mapper.CustomRegistrationRequestMapper;
import org.tju.food_007.model.CustomerEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.pub.login.UserLoginRepository;
import org.tju.food_007.repository.pub.register.CustomRegistrationRepository;
import org.tju.food_007.repository.pub.register.UserRegistrationRepository;
import org.tju.food_007.service.pub.login.UserLoginService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Epic("register模块")
@Feature("CustomerRegistrationService类")
class CustomerRegistrationServiceTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserRegistrationRepository userRegistrationRepository;
    @Mock
    private CustomRegistrationRepository customRegistrationRepository;
    @Mock
    private final CustomRegistrationRequestMapper customRegistrationRequestMapper =
            CustomRegistrationRequestMapper.INSTANCE;

    @InjectMocks
    private CustomerRegistrationService customerRegistrationService;


    @BeforeEach
    @Step("Set up the test environment and initialize mocks")
    public void setUp() {
        System.out.println("开始测试");
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "测试用例 {0}")
    @MethodSource("userRegistrationProvider")
    @DisplayName("测试用户登录服务类")
    @Story("Unit_002_001_001")
    @Description("测试CustomerRegistrationService.UserRegister方法")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Link", url = "http://testlink.com")
    @Step("Executing test case {0}")
    public void testUserRegister(String testCaseId, CustomRegistrationRequestDTO requestDTO, CustomRegistrationResponseDTO expectedResult) throws JsonProcessingException {
        mockRepositories(requestDTO);

        CustomRegistrationResponseDTO realResult = customerRegistrationService.UserRegister(requestDTO);
        String expectedJson = objectMapper.writeValueAsString(expectedResult);
        String actualJson = objectMapper.writeValueAsString(realResult);

        assertEquals(expectedJson, actualJson);
    }

    @Step("Mocking user login repository with request: {requestDTO}")
    private void mockRepositories(CustomRegistrationRequestDTO requestDTO) {
        UserEntity userEntity = new UserEntity();
        CustomerEntity customerEntity = new CustomerEntity();

        if ("13837829689".equals(requestDTO.getUser_phone())) {
            userEntity.setUserId(1);
            userEntity.setUserType(0);
            userEntity.setUserPhone("13837829689");
            userEntity.setUserPassword("123456");
            when(userRegistrationRepository.existsByUserPhone(requestDTO.getUser_phone())).thenReturn(true);
            when(userRegistrationRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
            when(userRegistrationRepository.existsByUserId(1)).thenReturn(true);
            when(customRegistrationRepository.save(customerEntity)).thenReturn(customerEntity);
            when(customRegistrationRequestMapper.requestToUserEntity(requestDTO)).thenReturn(userEntity);
        } else if ("15821295304".equals(requestDTO.getUser_phone())) {
            userEntity.setUserId(2);
            userEntity.setUserType(1);
            userEntity.setUserPhone("15821295304");
            userEntity.setUserPassword("123456");
            when(userRegistrationRepository.existsByUserPhone(requestDTO.getUser_phone())).thenReturn(true);
            when(userRegistrationRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
            when(userRegistrationRepository.existsByUserId(2)).thenReturn(true);
            when(customRegistrationRepository.save(customerEntity)).thenReturn(customerEntity);
            when(customRegistrationRequestMapper.requestToUserEntity(requestDTO)).thenReturn(userEntity);
        } else {
            userEntity.setUserId(3);
            userEntity.setUserType(0);
            userEntity.setUserPhone("15899583310");
            userEntity.setUserPassword("123456");
            System.out.println("新顾客注册，当前时间为: " + userEntity.getUserId());
            when(userRegistrationRepository.existsByUserPhone(requestDTO.getUser_phone())).thenReturn(false);
            when(userRegistrationRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
            when(userRegistrationRepository.existsByUserId(3)).thenReturn(true);
            when(customRegistrationRepository.save(customerEntity)).thenReturn(customerEntity);
            when(customRegistrationRequestMapper.requestToUserEntity(requestDTO)).thenReturn(userEntity);
        }
    }

    private static Stream<Arguments> userRegistrationProvider() throws IOException {
        return readTestCasesFromCsv("src/test/resources/Unit_002_002_001.csv");
    }

    @Step("Reading test cases from CSV file: {filePath}")
    private static Stream<Arguments> readTestCasesFromCsv(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        return reader.lines()
                .skip(1) // skip header
                .map(line -> {
                    String[] parts = line.split(",");
                    String testCaseId = parts[0];
                    CustomRegistrationRequestDTO requestDTO = new CustomRegistrationRequestDTO();
                    requestDTO.setUser_phone(parts[1]);
                    requestDTO.setUser_password(parts[2]);
                    requestDTO.setUser_gender(Integer.valueOf(parts[3]));
                    requestDTO.setCus_nickname(parts[4]);
                    requestDTO.setCus_payPassword(parts[5]);
                    CustomRegistrationResponseDTO expectedResult = new CustomRegistrationResponseDTO();
                    expectedResult.setMsg(parts[6]);
                    expectedResult.setUser_type(Integer.valueOf(parts[7]));
                    expectedResult.setUser_id(Integer.valueOf(parts[8]));
                    return Arguments.of(testCaseId, requestDTO, expectedResult);
                });
    }

}