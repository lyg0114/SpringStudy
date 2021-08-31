package com.spring.tobysrpingframework.user.learningtest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {
     Calculator calculator;
     String filepath;

     @Before
     public void setup(){
         this.calculator = new Calculator();
         this.filepath = "/Users/iyeong-gyo/Desktop/TobySrpingFrameWork/src/main/java/com/spring/tobysrpingframework/user/learningtest/numbers.txt";
     }

    @Test
    public void sumOfNumbers() throws IOException{
        assertThat(calculator.calcSum(filepath), is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException{
        assertThat(calculator.calcMultiply(filepath), is(24));
    }

}
