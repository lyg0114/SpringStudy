package com.spring.tobysrpingframework.user.learningtest;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {
    private String filepath = "/Users/iyeong-gyo/Desktop/TobySrpingFrameWork/src/main/java/com/spring/tobysrpingframework/user/learningtest";


    @Test
    public void sumOfNumbers() throws IOException{
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(filepath);
        assertThat(sum, is(10));

    }

}
