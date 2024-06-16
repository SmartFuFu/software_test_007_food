package org.tju.food_007.service.pub.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tju.food_007.dto.pub.login.UserLoginRequestDTO;
import org.tju.food_007.dto.pub.login.UserLoginResponseDTO;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.pub.login.UserLoginRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserLoginServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserLoginRepository userLoginRepository;

    @InjectMocks
    private UserLoginService userLoginService;

    @BeforeEach
    public void setUp() {
        System.out.println("开始测试");
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "测试用例 {0}")
    @MethodSource("userLoginProvider")
    @DisplayName("测试用户登录服务类")
    public void testUserLogin(String testCaseId, UserLoginRequestDTO requestDTO, UserLoginResponseDTO expectedResult) throws JsonProcessingException {
        mockUserLoginRepository(requestDTO);

        UserLoginResponseDTO realResult = userLoginService.UserLogin(requestDTO);
        String expectedJson = objectMapper.writeValueAsString(expectedResult);
        String actualJson = objectMapper.writeValueAsString(realResult);

        assertEquals(expectedJson, actualJson);
    }

    private void mockUserLoginRepository(UserLoginRequestDTO requestDTO) {
        UserEntity userEntity = new UserEntity();
        if ("13837829689".equals(requestDTO.getUser_phone())) {
            userEntity.setUserId(1);
            userEntity.setUserType(0);
            userEntity.setUserPhone("13837829689");
            userEntity.setUserPassword("123456");
            when(userLoginRepository.findByUserPhone(requestDTO.getUser_phone())).thenReturn(userEntity);
        } else if ("15821295304".equals(requestDTO.getUser_phone())) {
            userEntity.setUserId(2);
            userEntity.setUserType(1);
            userEntity.setUserPhone("15821295304");
            userEntity.setUserPassword("123456");
            when(userLoginRepository.findByUserPhone(requestDTO.getUser_phone())).thenReturn(userEntity);
        } else {
            when(userLoginRepository.findByUserPhone(requestDTO.getUser_phone())).thenReturn(null);
        }
    }

    private static Stream<Arguments> userLoginProvider() throws IOException {
        return readTestCasesFromCsv("src/test/resources/Unit_001_002_001.csv");
    }

    private static Stream<Arguments> readTestCasesFromCsv(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        return reader.lines()
                .skip(1) // skip header
                .map(line -> {
                    String[] parts = line.split(",");
                    String testCaseId = parts[0];
                    UserLoginRequestDTO requestDTO = new UserLoginRequestDTO();
                    requestDTO.setUser_phone(parts[1]);
                    requestDTO.setUser_password(parts[2]);
                    UserLoginResponseDTO expectedResult = new UserLoginResponseDTO();
                    expectedResult.setMsg(parts[3]);
                    expectedResult.setUser_type(Integer.valueOf(parts[4]));
                    expectedResult.setUser_id(Integer.valueOf(parts[5]));
                    return Arguments.of(testCaseId, requestDTO, expectedResult);
                });
    }
}
