package com.spring.sqlserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ErrorReturnTest {
    @Test
    void errorFailToSearchInDataBaseTest() {
        //获取当前控制台的输出
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ErrorReturn.errorFailToSearchInDataBase();
        Assertions.assertEquals("输入SQL语句不符合SQL语法\r\n", outContent.toString());
    }

    @Test
    void errorInputMismatchedTest(){
        //获取当前控制台的输出
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ErrorReturn.errorInputMismatched();
        Assertions.assertEquals("输入SQL和输入自然语言不匹配\r\n", outContent.toString());
    }
}
