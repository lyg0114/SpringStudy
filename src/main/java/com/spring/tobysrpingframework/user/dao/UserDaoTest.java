package com.spring.tobysrpingframework.user.dao;

import com.spring.tobysrpingframework.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao dao = new UserDao(connectionMaker);

        User user = new User();
        user.setId("yglee");
        user.setName("이초다");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + "등록성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회성공");
    }
}
