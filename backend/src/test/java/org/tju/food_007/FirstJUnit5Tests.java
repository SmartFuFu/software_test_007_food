package org.tju.food_007;

import org.junit.jupiter.api.*;
import org.testng.annotations.Test;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FirstJUnit5Tests {

    @BeforeAll
    public static void init() {
        System.out.println("初始化，准备测试信息");
    }

    @BeforeEach
    public void start() {
        System.out.println("开始测试...");
    }


    @Test
    @DisplayName("是否是狗")
    public void testIsDog() {
        String name = "dog";
        Assertions.assertEquals(name, "dog");
        System.out.println("is dog");
    }


    @Test
    @DisplayName("是否是猫")
    public void testIsCat() {
        String name = "cat";
        Assertions.assertEquals(name, "cat");
        System.out.println("is cat");
    }

    @AfterEach
    public void end() {
        System.out.println("测试完毕...");
    }

    @AfterAll
    public static void close() {
        System.out.println("结束，准备退出测试");
    }

}
