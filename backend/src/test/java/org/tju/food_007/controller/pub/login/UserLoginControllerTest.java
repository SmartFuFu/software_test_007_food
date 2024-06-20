//package org.tju.food_007.controller.pub.login;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.tju.food_007.dto.pub.login.UserLoginRequestDTO;
//import org.tju.food_007.dto.pub.login.UserLoginResponseDTO;
//import org.tju.food_007.model.UserEntity;
//import org.tju.food_007.repository.pub.login.UserLoginRepository;
//import org.tju.food_007.repository.pub.register.UserRegistrationRepository;
//import org.tju.food_007.service.pub.login.UserLoginService;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.regex.Pattern;
//import java.util.stream.Stream;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserLoginController.class)
//public class UserLoginControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserLoginService userLoginService;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    public void setUp() {
//        System.out.println("开始测试");
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @ParameterizedTest(name = "测试用例 {0}")
//    @MethodSource("userLoginProvider")
//    @DisplayName("测试用户登录控制器类")
//    public void testUserLogin(String testCaseId, UserLoginRequestDTO requestDTO, UserLoginResponseDTO expectedResult) throws Exception {
//        // 模拟 UserLoginService 的行为
//        mockUserLoginService(requestDTO);
//        // 将请求 DTO 转换为 JSON 字符串
//        String requestJson = objectMapper.writeValueAsString(requestDTO);
//        // 将期望的响应 DTO 转换为 JSON 字符串
//        String expectedJson = objectMapper.writeValueAsString(expectedResult);
//        // 发送 POST 请求并验证响应
//        mockMvc.perform(post("/api/pub/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isOk())
//                .andExpect(content().json(expectedJson));
//    }
//
//    private void mockUserLoginService(UserLoginRequestDTO requestDTO) throws Exception {
//        UserLoginResponseDTO responseDTO = createUserLoginResponse(requestDTO);
//
//        when(userLoginService.UserLogin(requestDTO)).thenReturn(responseDTO);
//    }
//
//    private UserLoginResponseDTO createUserLoginResponse(UserLoginRequestDTO requestDTO) {
//        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
//
//        if (!isValidPhoneNumber(requestDTO.getUser_phone())) {
//            responseDTO.setUser_type(-1);
//            responseDTO.setMsg("电话格式错误");
//            responseDTO.setUser_id(-1);
//            return responseDTO;
//        }
//        UserEntity targetUser = null;
//        if (requestDTO.getUser_phone() == "15821295304") {
//            targetUser = new UserEntity();
//            targetUser.setUserType(1);
//            targetUser.setUserPhone("15821295304");
//            targetUser.setUserId(2);
//            targetUser.setUserPassword("123456");
//        } else if (requestDTO.getUser_phone() == "13837829689") {
//            targetUser = new UserEntity();
//            targetUser.setUserType(0);
//            targetUser.setUserPhone("13837829689");
//            targetUser.setUserId(1);
//            targetUser.setUserPassword("123456");
//        }
//
//        if (targetUser != null) {
//            if (targetUser.getUserPassword().equals(requestDTO.getUser_password())) {
//                responseDTO.setUser_type(targetUser.getUserType());
//                responseDTO.setMsg("登录成功");
//                responseDTO.setUser_id(targetUser.getUserId());
//            } else {
//                responseDTO.setUser_type(-1);
//                responseDTO.setMsg("密码错误");
//                responseDTO.setUser_id(-1);
//            }
//        } else {
//            responseDTO.setUser_type(-1);
//            responseDTO.setMsg("账号不存在");
//            responseDTO.setUser_id(-1);
//        }
//        return responseDTO;
//    }
//
//    private boolean isValidPhoneNumber(String phoneNumber) {
//        Pattern PHONE_PATTERN = Pattern.compile("^\\d{11}$");
//        return PHONE_PATTERN.matcher(phoneNumber).matches();
//    }
//
//    private static Stream<Arguments> userLoginProvider() throws IOException {
//        return readTestCasesFromCsv("src/test/resources/Unit_001_002_001.csv");
//    }
//
//    private static Stream<Arguments> readTestCasesFromCsv(String filePath) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader(filePath));
//        return reader.lines()
//                .skip(1) // 跳过头部
//                .map(line -> {
//                    String[] parts = line.split(",");
//                    String testCaseId = parts[0];
//                    UserLoginRequestDTO requestDTO = new UserLoginRequestDTO();
//                    requestDTO.setUser_phone(parts[1]);
//                    requestDTO.setUser_password(parts[2]);
//                    UserLoginResponseDTO expectedResult = new UserLoginResponseDTO();
//                    expectedResult.setMsg(parts[3]);
//                    expectedResult.setUser_type(Integer.valueOf(parts[4]));
//                    expectedResult.setUser_id(Integer.valueOf(parts[5]));
//                    return Arguments.of(testCaseId, requestDTO, expectedResult);
//                });
//    }
//}