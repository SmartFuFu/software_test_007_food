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
import org.tju.food_007.dto.com.CommodityCategoriesResponseDTO;
import org.tju.food_007.dto.com.CommodityDetailDTO;
import org.tju.food_007.dto.com.CommodityImageResponseDTO;
import org.tju.food_007.dto.com.CommodityPriceCurveResponseDTO;
import org.tju.food_007.repository.com.Information.CommodityDetailRepository;
import org.tju.food_007.repository.pub.login.UserLoginRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


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
        System.out.println("结束测试");
    }

    @ParameterizedTest(name = "测试用例 {0}")
    @MethodSource("GetCommodityDetailProvider")
    @DisplayName("Unit_003_002_001_测试获取商品详情")
    public void testGetCommodityDetail(String testCaseId, Integer com_id, CommodityDetailDTO expect_result) throws JsonProcessingException {
        mockCommodityDetailRepository(com_id);

        CommodityDetailDTO actual_result = commodityInfomationService.getCommodityDetail(com_id);
        String expectedJson = objectMapper.writeValueAsString(expect_result);
        String actualJson = objectMapper.writeValueAsString(actual_result);

        assertEquals(expectedJson,actualJson);

    }

    private void mockCommodityDetailRepository(Integer com_id){
        List<Object[]> mockData = new ArrayList<>();

        // 模拟空数据场景
        if (com_id < 1 || com_id > 1000000 || com_id == 5) {
            when(commodityDetailRepository.getCommodityDetail(com_id)).thenReturn(mockData);
        } else if (com_id == 112) {
            // 根据你之前的 @Query 结构构建模拟数据
            Object[] row = new Object[]{
                    112, // COM_ID
                    "脆升升香脆薯条20g*25袋鲜切薯片蜂", // COM_NAME
                    "脆升升香脆薯条是一款美味可口的零食选择。每袋20克的鲜切薯片，经过独特工艺制作，口感脆爽，外表金黄诱人。薯片以蜂蜜黄油调味，带来香甜可口的味道，让您享受到独特的美味体验。小包装设计，方便携带，是您随时随地享用的零食伴侣。无论是在工作间隙还是下午茶时光，脆升升香脆薯条都能为您带来愉悦的零食时刻。", // COM_INTRODUCTION
                    39.8, // COM_ORIPRICE
                    0, // COM_TYPE
                    "2024-04-14 12:14:19.0", // COM_UPLOADDATE
                    0, // COM_LEFT
                    100.0, // PRAISE_RATE
                    39, // STO_ID
                    "Store Name", // STO_NAME
                    "08:30:00", // STO_OPENINGTIME
                    "20:30:00", // STO_CLOSINGTIME
                    "2024-04-15 00:00:00.0", // COM_EXPIRATIONDATE
                    "零食", // Categories
                    "commodity_image/112_0.jpg", // Images
                    "2024-03-15 00:00:00.0", 37.0, // COM_PC_TIME, COM_PC_PRICE
                    "2023-04-15 00:00:00.0" // COM_PRODUCEDDATE
            };

            // 添加单个记录多次以模拟多个价格时间点
            mockData.add(row);
            mockData.add(new Object[]{
                    112,
                    "脆升升香脆薯条20g*25袋鲜切薯片蜂",
                    "脆升升香脆薯条是一款美味可口的零食选择。每袋20克的鲜切薯片，经过独特工艺制作，口感脆爽，外表金黄诱人。薯片以蜂蜜黄油调味，带来香甜可口的味道，让您享受到独特的美味体验。小包装设计，方便携带，是您随时随地享用的零食伴侣。无论是在工作间隙还是下午茶时光，脆升升香脆薯条都能为您带来愉悦的零食时刻。",
                    39.8,
                    0,
                    "2024-04-14 12:14:19.0",
                    0,
                    100.0,
                    39,
                    "Store Name",
                    "08:30:00",
                    "20:30:00",
                    "2024-04-15 00:00:00.0",
                    "零食",
                    "commodity_image/112_0.jpg",
                    "2024-04-01 00:00:00.0", 27.0,
                    "2023-04-15 00:00:00.0"
            });

            // 模拟数据返回
            when(commodityDetailRepository.getCommodityDetail(com_id)).thenReturn(mockData);
        }
    }

    private Stream<Arguments> GetCommodityDetailProvider() {

        return Stream.of(
                Arguments.of("Unit_003_002_001_001_001",Unit_003_002_001_001_001_input,Unit_003_002_001_001_001_output()),
                Arguments.of("Unit_003_002_001_001_002",Unit_003_002_001_001_002_input,Unit_003_002_001_001_002_output()),
                Arguments.of("Unit_003_002_001_002_001",Unit_003_002_001_002_001_input,Unit_003_002_001_002_001_output()),
                Arguments.of("Unit_003_002_001_003_001",Unit_003_002_001_003_001_input,Unit_003_002_001_003_001_output()),
                Arguments.of("Unit_003_002_001_004_001",Unit_003_002_001_004_001_input,Unit_003_002_001_004_001_output())
        );
    }


    private Integer Unit_003_002_001_001_001_input = 0;
    private Integer Unit_003_002_001_001_002_input = -1;
    private Integer Unit_003_002_001_002_001_input = 5;
    private Integer Unit_003_002_001_003_001_input = 1000001;
    private Integer Unit_003_002_001_004_001_input = 112;



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
    private static CommodityDetailDTO Unit_003_002_001_001_002_output() {
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
    private static CommodityDetailDTO Unit_003_002_001_002_001_output() {
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
    private static CommodityDetailDTO Unit_003_002_001_003_001_output() {
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
    private static CommodityDetailDTO Unit_003_002_001_004_001_output() {
        CommodityDetailDTO output = new CommodityDetailDTO();
        output.setCom_ID(112);
        output.setCom_name("脆升升香脆薯条20g*25袋鲜切薯片蜂");
        output.setCom_introduction("脆升升香脆薯条是一款美味可口的零食选择。每袋20克的鲜切薯片，经过独特工艺制作，口感脆爽，外表金黄诱人。薯片以蜂蜜黄油调味，带来香甜可口的味道，让您享受到独特的美味体验。小包装设计，方便携带，是您随时随地享用的零食伴侣。无论是在工作间隙还是下午茶时光，脆升升香脆薯条都能为您带来愉悦的零食时刻。");
        output.setCom_uploadDate("2024-04-14 12:14:19.0");
        output.setCom_left(0);
        output.setSto_ID(39);
        output.setCom_type(0);
        output.setCom_oriPrice(39.8);
        output.setPraise_rate(100.0);
        output.setCom_expirationDate("2024-04-15 00:00:00.0");
        output.setCom_producedDate("2023-04-15 00:00:00.0");

        CommodityPriceCurveResponseDTO[] priceCurve = {
                new CommodityPriceCurveResponseDTO("2024-03-15 00:00:00.0", 37.0),
                new CommodityPriceCurveResponseDTO("2024-04-01 00:00:00.0", 27.0)
        };
        output.setCommodityPriceCurve(priceCurve);

        CommodityImageResponseDTO[] images = {
                new CommodityImageResponseDTO("commodity_image/112_0.jpg")
        };
        output.setCommodityImage(images);

        CommodityCategoriesResponseDTO[] categories = {
                new CommodityCategoriesResponseDTO("零食")
        };
        output.setCommodity_categories(categories);

        output.setSto_openingTime("08:30:00");
        output.setSto_closingTime("20:30:00");

        return output;
    }


}