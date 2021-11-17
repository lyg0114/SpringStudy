package com.spring.tobysrpingframework.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String name;
    private String password;
    Level level;
    int login;
    int recommend;
    private String email;


    public void upgradeLevel(){

        Level nextLevel = this.level.nextLevel();
        if(nextLevel == null){
            throw new IllegalStateException(this.level + "은 업그레이드가 불가능 합니다.");
        }else{
            this.level = nextLevel;
        }
    }

}
