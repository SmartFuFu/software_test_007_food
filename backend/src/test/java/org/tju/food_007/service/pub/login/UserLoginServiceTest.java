package org.tju.food_007.service.pub.login;

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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserLoginServiceTest {

    @Mock
    private UserLoginRepository userLoginRepository;

    @InjectMocks
    private UserLoginService userLoginService;

    @BeforeEach
    public void setUp() {
        System.out.println("开始测试");
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "测试用例 {index}: 手机号={0}, 密码={1}, 预期消息={2}")
    @MethodSource("userLoginProvider")
    @DisplayName("测试用户登录")
    public void testUserLogin(String userPhone, String userPassword, String expectedMsg, Integer expectedUserType, Integer expectedUserId) {
        mockUserLoginRepository(userPhone, userPassword);

        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO();
        requestDTO.setUser_phone(userPhone);
        requestDTO.setUser_password(userPassword);

        UserLoginResponseDTO responseDTO = userLoginService.UserLogin(requestDTO);

        assertEquals(expectedMsg, responseDTO.getMsg());
        assertEquals(expectedUserType, responseDTO.getUser_type());
        assertEquals(expectedUserId, responseDTO.getUser_id());
    }

    private void mockUserLoginRepository(String userPhone, String userPassword) {
        UserEntity userEntity = new UserEntity();
        if ("13837829689".equals(userPhone) && "123456".equals(userPassword)) {
            userEntity.setUserId(1);
            userEntity.setUserType(1);
            userEntity.setUserPhone(userPhone);
            userEntity.setUserPassword(userPassword);
            when(userLoginRepository.findByUserPhone(userPhone)).thenReturn(userEntity);
        } else if ("15821295304".equals(userPhone)) {
            userEntity.setUserId(2);
            userEntity.setUserType(2);
            userEntity.setUserPhone(userPhone);
            userEntity.setUserPassword("123456");
            when(userLoginRepository.findByUserPhone(userPhone)).thenReturn(userEntity);
        } else {
            when(userLoginRepository.findByUserPhone(userPhone)).thenReturn(null);
        }
    }

    private Stream<Arguments> userLoginProvider() {
        return Stream.of(
                Arguments.of("13837829689", "123456", "登录成功", 1, 1),
                Arguments.of("xxxxxxx", "123456", "账号不存在", -1, -1),
                Arguments.of("15821295304", "123456", "登录成功", 2, 2),
                Arguments.of("15821295304", "wrongpwd", "密码错误", -1, -1)
        );
    }





}
