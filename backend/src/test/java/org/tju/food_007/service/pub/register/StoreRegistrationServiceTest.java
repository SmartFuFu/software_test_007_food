package org.tju.food_007.service.pub.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.tju.food_007.dto.pub.register.CustomRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationResponseDTO;
import org.tju.food_007.dto.pub.register.StoreRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.mapper.StoreRegistrationRequestMapper;
import org.tju.food_007.model.CustomerEntity;
import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.pub.register.CustomRegistrationRepository;
import org.tju.food_007.repository.pub.register.StoreRegistrationRepository;
import org.tju.food_007.repository.pub.register.UserRegistrationRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreRegistrationServiceTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserRegistrationRepository userRegistrationRepository;
    @Mock
    private StoreRegistrationRepository storeRegistrationRepository;
    @Mock
    private final StoreRegistrationRequestMapper storeRegistrationRequestMapper =
            StoreRegistrationRequestMapper.INSTANCE;

    @InjectMocks
    private StoreRegistrationService storeRegistrationService;

    @BeforeEach
    public void setUp() {
        System.out.println("开始测试");
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "测试用例 {0}")
    @MethodSource("userRegistrationProvider")
    @DisplayName("测试用户登录服务类")
    public void testStoreRegister(String testCaseId, StoreRegistrationRequestDTO requestDTO, CustomRegistrationResponseDTO expectedResult) throws JsonProcessingException {
        mockRepositories(requestDTO);

        CustomRegistrationResponseDTO realResult = storeRegistrationService.StoreRegister(requestDTO);
        String expectedJson = objectMapper.writeValueAsString(expectedResult);
        String actualJson = objectMapper.writeValueAsString(realResult);

        assertEquals(expectedJson, actualJson);
    }

    private void mockRepositories(StoreRegistrationRequestDTO requestDTO) {
        UserEntity userEntity = new UserEntity();
        StoreEntity storeEntity = new StoreEntity();

        if ("13837829689".equals(requestDTO.getUser_phone())) {
            userEntity.setUserId(1);
            userEntity.setUserType(0);
            userEntity.setUserPhone("13837829689");
            userEntity.setUserPassword("123456");
            when(userRegistrationRepository.existsByUserPhone(requestDTO.getUser_phone())).thenReturn(true);
            when(userRegistrationRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
//            when(userRegistrationRepository.existsByUserId(1)).thenReturn(true);
            when(storeRegistrationRepository.save(storeEntity)).thenReturn(storeEntity);
            when(storeRegistrationRequestMapper.requestToUserEntity(requestDTO)).thenReturn(userEntity);
            when(storeRegistrationRequestMapper.requestToStoEntity(requestDTO)).thenReturn(storeEntity);
        } else if ("15821295304".equals(requestDTO.getUser_phone())) {
            userEntity.setUserId(2);
            userEntity.setUserType(1);
            userEntity.setUserPhone("15821295304");
            userEntity.setUserPassword("123456");
            when(userRegistrationRepository.existsByUserPhone(requestDTO.getUser_phone())).thenReturn(true);
            when(userRegistrationRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
//            when(userRegistrationRepository.existsByUserId(2)).thenReturn(true);
            when(storeRegistrationRepository.save(storeEntity)).thenReturn(storeEntity);
            when(storeRegistrationRequestMapper.requestToUserEntity(requestDTO)).thenReturn(userEntity);
            when(storeRegistrationRequestMapper.requestToStoEntity(requestDTO)).thenReturn(storeEntity);
        } else {
            userEntity.setUserId(3);
            userEntity.setUserType(0);
            userEntity.setUserPhone("15899583310");
            userEntity.setUserPassword("123456");
            System.out.println("新顾客注册，当前时间为: " + userEntity.getUserId());
            when(userRegistrationRepository.existsByUserPhone(requestDTO.getUser_phone())).thenReturn(false);
            when(userRegistrationRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
            when(storeRegistrationRepository.save(storeEntity)).thenReturn(storeEntity);
            when(storeRegistrationRequestMapper.requestToUserEntity(requestDTO)).thenReturn(userEntity);
            when(storeRegistrationRequestMapper.requestToStoEntity(requestDTO)).thenReturn(storeEntity);
        }
    }

    private static Stream<Arguments> userRegistrationProvider() throws IOException {
        return readTestCasesFromCsv("src/test/resources/Unit_002_003_001.csv");
    }

    private static Stream<Arguments> readTestCasesFromCsv(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        return reader.lines()
                .skip(1) // skip header
                .map(line -> {
                    String[] parts = line.split(",");
                    String testCaseId = parts[0];
                    StoreRegistrationRequestDTO requestDTO = new StoreRegistrationRequestDTO();
                    requestDTO.setUser_phone(parts[1]);
                    requestDTO.setUser_password(parts[2]);
                    requestDTO.setUser_address(parts[3]);
                    requestDTO.setUser_gender(Integer.valueOf(parts[4]));
                    requestDTO.setSto_name(parts[5]);
                    requestDTO.setSto_introduction(parts[6]);
                    requestDTO.setSto_openingTime(Time.valueOf(parts[7] + ":00"));
                    requestDTO.setSto_closingTime(Time.valueOf(parts[8]+ ":00"));
                    requestDTO.setSto_latitude(parts[9]);
                    requestDTO.setSto_longitude(parts[10]);
                    CustomRegistrationResponseDTO expectedResult = new CustomRegistrationResponseDTO();
                    expectedResult.setMsg(parts[11]);
                    expectedResult.setUser_type(Integer.valueOf(parts[12]));
                    expectedResult.setUser_id(Integer.valueOf(parts[13]));
                    return Arguments.of(testCaseId, requestDTO, expectedResult);
                });
    }
}