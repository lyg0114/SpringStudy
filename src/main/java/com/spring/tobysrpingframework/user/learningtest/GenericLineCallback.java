package com.spring.tobysrpingframework.user.learningtest;

import java.io.IOException;

public interface GenericLineCallback<T> {

    T doSomethingWithLine(String line, T value);

}
