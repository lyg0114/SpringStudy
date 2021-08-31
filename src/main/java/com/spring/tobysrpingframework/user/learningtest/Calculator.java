package com.spring.tobysrpingframework.user.learningtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException{

        LineCallback sumCallback = new LineCallback() {

            @Override
            public Integer doSomethingithLine(String line, Integer value) throws IOException{
                value += Integer.valueOf(line);
                return value;
            }
        };

        return lineFildReadTemplate(filepath, sumCallback,0);

    }


    public Integer calcMultiply(String filepath) throws IOException{

        LineCallback multiflyCallback = new LineCallback() {

            @Override
            public Integer doSomethingithLine(String line, Integer value) throws IOException{
                value *= Integer.valueOf(line);
                return value;
            }
        };

        return lineFildReadTemplate(filepath, multiflyCallback, 1);

    }


    public Integer lineFildReadTemplate(String filepath, LineCallback callback, Integer initValue) throws IOException{
        BufferedReader br = null;

        try{
            Integer resultValue = initValue;
            String line = null;

            br = new BufferedReader(new FileReader(filepath));
            while((line = br.readLine()) != null){
                resultValue = callback.doSomethingithLine(line, resultValue);
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
