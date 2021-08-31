package com.spring.tobysrpingframework.user.learningtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException{

        GenericLineCallback<Integer> sumCallback = new GenericLineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value){
                return value + Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, sumCallback,0);
    }

    public Integer calcMultiply(String filepath) throws IOException{

        GenericLineCallback<Integer> multiflyCallback = new GenericLineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value){
                return value * Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, multiflyCallback, 1);
    }

    public String concatenate(String filepath) throws IOException {

        GenericLineCallback<String> concatenateCallback = new GenericLineCallback<String>() {

            @Override
            public String doSomethingWithLine(String line, String value){
                return value + line;
            }
        };
        return lineReadTemplate(filepath, concatenateCallback, "");

    }

    public <T> T lineReadTemplate(String filepath, GenericLineCallback<T> callback, T initValue) throws IOException{
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(filepath));
            T resultValue = initValue;
            String line = null;
            while((line = br.readLine()) != null){
                resultValue = callback.doSomethingWithLine(line, resultValue);
            }
            return resultValue;

        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(br != null){
                try{ br.close();}
                catch (IOException e){
                    e.printStackTrace();
                }

            }
        }


    }

}
