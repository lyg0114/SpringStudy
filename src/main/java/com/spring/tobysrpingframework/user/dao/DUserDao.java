package com.spring.tobysrpingframework.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDao{

    protected Connection getConnection() throws ClassNotFoundException,
            SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://lizcalendal-mysql.canpeabsn7mi.ap-northeast-2.rds.amazonaws.com:3306/spring_study",
                "kylelovelizzy",
                "kylelovelizzy");
        return c;
    }
}