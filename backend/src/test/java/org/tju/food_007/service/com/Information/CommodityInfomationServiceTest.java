package org.tju.food_007.service.com.Information;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tju.food_007.dto.com.CommodityDetailDTO;
import org.tju.food_007.repository.com.Information.CommodityDetailRepository;
import org.tju.food_007.repository.pub.login.UserLoginRepository;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommodityInfomationServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CommodityDetailRepository commodityDetailRepository;

    @InjectMocks
    private CommodityInfomationService commodityInfomationService;

    @BeforeEach
    public void setUp() {
        System.out.println("开始测试");
        MockitoAnnotations.openMocks(this); //打开Mock
    }

    @AfterEach
    void tearDown() {
    }


    @ParameterizedTest(name = "测试用例 {0}")
    @MethodSource("GetCommodityDetailProvider")
    @DisplayName("测试获取商品详情")
    public void testGetCommodityDetail(String testCaseId, Integer com_id, CommodityDetailDTO expert_result) throws JsonProcessingException {

        CommodityDetailDTO ground_result = commodityInfomationService.getCommodityDetail(com_id);
        String expectedJson = objectMapper.writeValueAsString(expert_result);
        String actualJson = objectMapper.writeValueAsString(ground_result);

        assertEquals(expectedJson,actualJson);

    }

    private Stream<Arguments> GetCommodityDetailProvider() {
        return Stream.of(
                Arguments.of("Unit_003_002_001_001_001",Unit_003_002_001_001_001_input,Unit_003_002_001_001_001_output()),
                Arguments.of("Unit_003_002_001_001_002",Unit_003_002_001_001_002_input,Unit_003_002_001_001_001_output())

        );
    }





    private Integer Unit_003_002_001_001_001_input = 0;
    private Integer Unit_003_002_001_001_002_input = -1;

    private static CommodityDetailDTO Unit_003_002_001_001_001_output() {
        CommodityDetailDTO output = new CommodityDetailDTO();
        output.setCom_ID(-1);
        output.setCom_name(null);
        output.setCom_introduction(null);
        output.setCom_uploadDate(null);
        output.setCom_left(0);
        output.setSto_ID(0);
        output.setCom_type(0);
        output.setCom_oriPrice(0.0);
        output.setPraise_rate(0.0);
        output.setCom_expirationDate(null);
        output.setCom_producedDate(null);
        output.setCommodityPriceCurve(null);
        output.setCommodityImage(null);
        output.setCommodity_categories(null);
        output.setSto_openingTime(null);
        output.setSto_closingTime(null);
        return output;
    }




}