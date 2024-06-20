package org.tju.food_007.service.com.Information;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.qameta.allure.*;
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

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Epic("commodity information模块")
@Feature("CommodityInfomationService类")
class CommodityInfomationServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CommodityDetailRepository commodityDetailRepository;

    @InjectMocks
    private CommodityInfomationService commodityInfomationService;

    @BeforeEach
    @Step("Set up the test environment and initialize mocks")
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
    @Story("Unit_003_002_001")
    @Description("测试CommodityInfomationService.GetCommodityDetail方法")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Test Link", url = "http://testlink.com")
    @Step("Executing test case {0}")
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

    private Stream<Arguments> GetCommodityDetailProvider() throws IOException, CsvValidationException {
        String filePath = "src/test/resources/Unit_003_002_002.csv";
        List<Arguments> argumentsList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                if (values[0].equals("test_case_id")) {
                    continue; // Skip header row
                }
                String testCaseId = values[0];
                Integer input = Integer.parseInt(values[1]);

                CommodityDetailDTO output = new CommodityDetailDTO();
                output.setCom_ID(Integer.parseInt(values[2]));
                output.setCom_name(values[3].isEmpty() ? null : values[3]);
                output.setCom_introduction(values[4].isEmpty() ? null : values[4]);
                output.setCom_uploadDate(values[5].isEmpty() ? null : values[5]);
                output.setCom_left(Integer.parseInt(values[6]));
                output.setSto_ID(Integer.parseInt(values[7]));
                output.setCom_type(Integer.parseInt(values[8]));
                output.setCom_oriPrice(Double.parseDouble(values[9]));
                output.setPraise_rate(Double.parseDouble(values[10]));
                output.setCom_expirationDate(values[11].isEmpty() ? null : values[11]);
                output.setCom_producedDate(values[12].isEmpty() ? null : values[12]);
                output.setCommodityPriceCurve(values[13].isEmpty() ? null : parseCommodityPriceCurve(values[13]));
                output.setCommodityImage(values[14].isEmpty() ? null : parseCommodityImage(values[14]));
                output.setCommodity_categories(values[15].isEmpty() ? null : parseCommodityCategories(values[15]));
                output.setSto_openingTime(values[16].isEmpty() ? null : values[16]);
                output.setSto_closingTime(values[17].isEmpty() ? null : values[17]);

                argumentsList.add(Arguments.of(testCaseId, input, output));
            }
        }

        return argumentsList.stream();
    }
    private CommodityPriceCurveResponseDTO[] parseCommodityPriceCurve(String value) {
        if (value == null || value.isEmpty()) {
            return new CommodityPriceCurveResponseDTO[0];
        }

        // Example format: [CommodityPriceCurveResponseDTO(2024-03-15 00:00:00.0, 37.0), CommodityPriceCurveResponseDTO(2024-04-01 00:00:00.0, 27.0)]
        String[] parts = value.substring(1, value.length() - 1).split("\\), CommodityPriceCurveResponseDTO\\(");
        List<CommodityPriceCurveResponseDTO> list = new ArrayList<>();

        for (String part : parts) {
            String[] fields = part.replace("CommodityPriceCurveResponseDTO(", "").replace(")", "").split(", ");
            list.add(new CommodityPriceCurveResponseDTO(fields[0], Double.parseDouble(fields[1])));
        }

        return list.toArray(new CommodityPriceCurveResponseDTO[0]);
    }

    private CommodityImageResponseDTO[] parseCommodityImage(String value) {
        if (value == null || value.isEmpty()) {
            return new CommodityImageResponseDTO[0];
        }

        // Example format: [CommodityImageResponseDTO(commodity_image/112_0.jpg)]
        String[] parts = value.substring(1, value.length() - 1).split("\\), CommodityImageResponseDTO\\(");
        List<CommodityImageResponseDTO> list = new ArrayList<>();

        for (String part : parts) {
            String image = part.replace("CommodityImageResponseDTO(", "").replace(")", "");
            list.add(new CommodityImageResponseDTO(image));
        }

        return list.toArray(new CommodityImageResponseDTO[0]);
    }

    private CommodityCategoriesResponseDTO[] parseCommodityCategories(String value) {
        if (value == null || value.isEmpty()) {
            return new CommodityCategoriesResponseDTO[0];
        }

        // Example format: [CommodityCategoriesResponseDTO(零食)]
        String[] parts = value.substring(1, value.length() - 1).split("\\), CommodityCategoriesResponseDTO\\(");
        List<CommodityCategoriesResponseDTO> list = new ArrayList<>();

        for (String part : parts) {
            String category = part.replace("CommodityCategoriesResponseDTO(", "").replace(")", "");
            list.add(new CommodityCategoriesResponseDTO(category));
        }

        return list.toArray(new CommodityCategoriesResponseDTO[0]);
    }



}