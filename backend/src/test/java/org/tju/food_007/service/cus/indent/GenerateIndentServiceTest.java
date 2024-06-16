package org.tju.food_007.service.cus.indent;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
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
import org.tju.food_007.dto.cus.indent.GenerateIndentRequestDTO;
import org.tju.food_007.dto.cus.indent.IndentCommodity;
import org.tju.food_007.dto.cus.mapper.GenerateIndentComRequstMapper;
import org.tju.food_007.dto.cus.mapper.GenerateIndentRequestMapper;
import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.model.IndentCommodityEntity;
import org.tju.food_007.model.IndentEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.com.Information.CommodityDetailRepository;
import org.tju.food_007.repository.cus.indent.GenerateIndentComRepository;
import org.tju.food_007.repository.cus.indent.GenerateIndentRepository;
import org.tju.food_007.repository.sto.UserUploadLogoImageUserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenerateIndentServiceTest {
    @Mock
    private GenerateIndentRepository generateIndentRepository;
    @Mock
    private GenerateIndentComRepository generateIndentComRepository;
    @Mock
    private UserUploadLogoImageUserRepository userUploadLogoImageUserRepository;
    @Mock
    private CommodityDetailRepository commodityDetailRepository;

    @Mock
    private GenerateIndentComRequstMapper generateIndentComRequstMapper;
    @Mock
    GenerateIndentRequestMapper generateIndentRequestMapper;

    @InjectMocks
    private GenerateIndentService generateIndentService;



    @BeforeEach
    public void setUp() {
        System.out.println("开始测试");
        MockitoAnnotations.openMocks(this); //打开Mock
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest(name = "测试用例 {0}")
    @MethodSource("GenerateIndentProvider")
    @DisplayName("Unit_004_002_001_生成订单测试用例")
    public void testGenerateIndent(String testCaseId,GenerateIndentRequestDTO input ,String expect_output, boolean isBalance){
        mockUserUploadLogoImageUserRepository(Integer.parseInt(input.getCus_Id()),isBalance);
        mockGenerateIndentRepository();
        mockGenerateIndentComRepository();
        mockCommodityDetailRepository(Integer.parseInt(input.getCom_arr().getFirst().getCom_id()));
        mockGenerateIndentComRequestMapper(Integer.parseInt(input.getCus_Id()));
        mockGenerateIndentRequestMapper();

        String actual_output = generateIndentService.GenerateIndent(input);
        assertEquals(expect_output,actual_output);
    }


    private void mockGenerateIndentRepository(){
        IndentEntity indent = new IndentEntity();
        indent.setIndId(1);
        // 当调用save方法时，始终返回同一个IndentEntity实例
        when(generateIndentRepository.save(any(IndentEntity.class))).thenReturn(indent);
    }

    private void mockGenerateIndentComRepository(){
        IndentCommodityEntity indent = new IndentCommodityEntity();
        indent.setIndId(1);
        // 当调用save方法时，始终返回同一个IndentEntity实例
        when(generateIndentComRepository.save(any())).thenReturn(indent);
    }
    private void mockGenerateIndentRequestMapper(){
        IndentEntity indent =new IndentEntity();
        when(generateIndentRequestMapper.dtoToEntity(any())).thenReturn(indent);
    }

    private void mockUserUploadLogoImageUserRepository(Integer com_ID,boolean isBalance){
        UserEntity user;
        if(com_ID<1 || com_ID>1000000){
            when(userUploadLogoImageUserRepository.findByUserId(com_ID)).thenReturn(null);
        }
        else if(com_ID==10){
            when(userUploadLogoImageUserRepository.findByUserId(com_ID)).thenReturn(null);
        }
        else if(com_ID==48 && !isBalance){
            user = new UserEntity();
            user.setUserType(0);
            user.setUserPhone("18750570815");
            user.setUserBalance(BigDecimal.valueOf(50000));
            when(userUploadLogoImageUserRepository.findByUserId(com_ID)).thenReturn(user);
        }
        else if(com_ID==48 && isBalance){
            user = new UserEntity();
            user.setUserType(0);
            user.setUserPhone("18750570815");
            user.setUserBalance(BigDecimal.valueOf(49));
            when(userUploadLogoImageUserRepository.findByUserId(com_ID)).thenReturn(user);
        }
    }

    private void mockCommodityDetailRepository(Integer com_ID){
        CommodityEntity commodity =new CommodityEntity();
        if(com_ID<1||com_ID>1000000){
            when(commodityDetailRepository.getCommodityDetail(com_ID)).thenReturn(null);
        }
        else if(com_ID==1){
            when(commodityDetailRepository.getCommodityDetail(com_ID)).thenReturn(null);
        }
        else if(com_ID==116){
            commodity.setComId(116);
            commodity.setStoId(39);
            when(commodityDetailRepository.findByComId(com_ID)).thenReturn(commodity);
        }
    }

    private void mockGenerateIndentComRequestMapper(Integer com_ID){
        if(com_ID==116){
            IndentCommodityEntity indentCommodity =new IndentCommodityEntity();
            indentCommodity.setComId(116);
            when(generateIndentComRequstMapper.dtoToEntity(any(IndentCommodity.class))).thenReturn(indentCommodity);
        }

    }

    private Stream<Arguments> GenerateIndentProvider() throws IOException, CsvValidationException {
        String filePath = "src/test/resources/Unit_004_002_001.csv"; // 修改为你的CSV文件路径
        List<Arguments> argumentsList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                if (values[0].equals("test_case_id")) {
                    continue; // Skip header row
                }
                String testCaseId = values[0];
                GenerateIndentRequestDTO input = parseInput(values);
                String expect_output = values[17];
                boolean isBalance = Boolean.parseBoolean(values[18]);

                argumentsList.add(Arguments.of(testCaseId, input, expect_output, isBalance));
            }
        }

        return argumentsList.stream();
    }

    private GenerateIndentRequestDTO parseInput(String[] values) {
        GenerateIndentRequestDTO requestDTO = new GenerateIndentRequestDTO();
        requestDTO.setCus_Id(values[1]);

        List<IndentCommodity> commodities = new ArrayList<>();
        if (!values[2].isEmpty()) {
            commodities.add(new IndentCommodity(values[2], values[3], values[4]));
        }
        if (!values[5].isEmpty()) {
            commodities.add(new IndentCommodity(values[5], values[6], values[7]));
        }
        if (!values[8].isEmpty()) {
            commodities.add(new IndentCommodity(values[8], values[9], values[10]));
        }
        requestDTO.setCom_arr(commodities);

        requestDTO.setDelivery_method(Integer.parseInt(values[11]));
        requestDTO.setDelivery_address(values[12]);
        requestDTO.setDelivery_altitude(Double.parseDouble(values[13]));
        requestDTO.setDelivery_longitude(Double.parseDouble(values[14]));
        requestDTO.setInd_notes(values[15]);
        requestDTO.setInd_money(Double.parseDouble(values[16]));

        return requestDTO;
    }

//    private Stream<Arguments> GenerateIndentProvider() {
//        return Stream.of(
//                Arguments.of("Unit_004_002_001_001_001",Unit_004_002_001_001_001_input(),Unit_004_002_001_001_001_output,false)
//        );
//    }



//
//    private GenerateIndentRequestDTO Unit_004_002_001_001_001_input(){
//        GenerateIndentRequestDTO requestDTO = new GenerateIndentRequestDTO();
//        // 设置顾客ID
//        requestDTO.setCus_Id("48");
//        // 创建商品列表
//        List<IndentCommodity> commodities = new ArrayList<>();
//        commodities.add(new IndentCommodity("116", "10", "4.9"));
//        requestDTO.setCom_arr(commodities);
//        // 设置配送方式
//        requestDTO.setDelivery_method(0); // 假设配送方式为整型，0代表某种配送方式
//        // 设置配送地址
//        requestDTO.setDelivery_address("上海市曹安公路4800号同济大学");
//        // 设置经纬度（注意，这里需要将字符串转换为Double，如果原本设计就是Double则直接赋值）
//        requestDTO.setDelivery_altitude(31.38048);
//        requestDTO.setDelivery_longitude(121.27280);
//        // 设置订单备注
//        requestDTO.setInd_notes("我希望商品尽快送达，谢谢！");
//        // 设置订单金额
//        requestDTO.setInd_money(49.0);
//        return requestDTO;
//    }
//
//    private String Unit_004_002_001_001_001_output = "订单生成成功";
//    private String Unit_004_002_001_002_001_output = "无效的客户ID";
//    private String Unit_004_002_001_002_002_output = "无效的客户ID";
//    private String Unit_004_002_001_002_003_output = "无效的客户ID";
//    private String Unit_004_002_001_003_001_output = "顾客ID不存在";
//    private String Unit_004_002_001_004_001_output = "订单生成成功";
//    private String Unit_004_002_001_004_002_output = "订单生成成功";
//    private String Unit_004_002_001_004_003_output = "备注长度超出限制";
//    private String Unit_004_002_001_005_001_output = "无效的配送方式";
//    private String Unit_004_002_001_006_001_output = "订单生成成功";
//    private String Unit_004_002_001_006_002_output = "无效的订单金额";
//    private String Unit_004_002_001_007_001_output = "无效的配送位置坐标";
//    private String Unit_004_002_001_007_002_output = "无效的配送位置坐标";
//    private String Unit_004_002_001_007_003_output = "无效的配送位置坐标";
//    private String Unit_004_002_001_007_004_output = "无效的配送位置坐标";
//    private String Unit_004_002_001_008_001_output = "无效的商品ID";
//    private String Unit_004_002_001_008_002_output = "无效的商品ID";
//    private String Unit_004_002_001_008_003_output = "无效的商品ID";
//    private String Unit_004_002_001_008_004_output = "无效的商品ID";
//    private String Unit_004_002_001_009_001_output = "商品ID不存在";
//    private String Unit_004_002_001_010_001_output = "订单生成成功";
//    private String Unit_004_002_001_010_002_output = "余额不足";


}