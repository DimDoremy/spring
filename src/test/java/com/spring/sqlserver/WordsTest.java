package com.spring.sqlserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WordsTest {
    @Test
    void toStringTest(){
        Words words = new Words();
        Assertions.assertEquals(" ",words.toString());
    }
}
