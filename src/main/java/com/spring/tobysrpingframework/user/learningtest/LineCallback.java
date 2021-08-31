package com.spring.tobysrpingframework.user.learningtest;

import java.io.BufferedReader;
import java.io.IOException;

public interface LineCallback {

    Integer doSomethingithLine(String line, Integer value) throws IOException;

}
