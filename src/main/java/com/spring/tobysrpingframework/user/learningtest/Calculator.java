package com.spring.tobysrpingframework.user.learningtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException{
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(filepath));
            Integer sum =0;
            String line = null;
            while((line = br.readLine()) != null){
                sum += Integer.valueOf(line);
            }

        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(br != null){
                try{ br.close();}
                catch (IOException e){
                    e.printStackTraceq();
                }

            }
        }









        return  sum;
    }



}
