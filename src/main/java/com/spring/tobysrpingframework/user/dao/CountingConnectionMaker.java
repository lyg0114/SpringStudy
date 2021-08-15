package com.spring.tobysrpingframework.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker{

    int count =0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) { //Dependency Injection
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.count ++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter(){
        return this.count;
    }

}
