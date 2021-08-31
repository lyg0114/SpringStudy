package com.spring.tobysrpingframework.user.learningtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException{

        BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingithReader(BufferedReader br) throws IOException{
                Integer sum = 0;
                String line = null;
                while((line = br.readLine()) != null){
                    sum += Integer.valueOf(line);
                }
                return sum;
            }
        };

        return fildReadTemplate(filepath, sumCallback);

    }


    public Integer fildReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException{
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(filepath));
            int ret = callback.doSomethingithReader(br);
            return ret;
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
