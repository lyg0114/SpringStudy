package com.spring.tobysrpingframework.user.dao;

import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DataFactory {

    @Bean
    public UserDao userDao() {
        //UserDao dao = new UserDao(connectionMaker());
        UserDao dao = new UserDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://lizcalendal-mysql.canpeabsn7mi.ap-northeast-2.rds.amazonaws.com:3306/spring_study");
        dataSource.setUsername("kylelovelizzy");
        dataSource.setPassword("kylelovelizzy");

        return dataSource;

    }
}
