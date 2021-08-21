package com.spring.tobysrpingframework.user.dao;

import com.spring.tobysrpingframework.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("yglee");
        user.setName("이초다");
        user.setPassword("married");
        dao.add(user);

        User user2 = new User();
        user2.setId("yglee2");
        user2.setName("이초다2");
        user2.setPassword("married2");
        dao.add(user2);

        User user3 = new User();
        user3.setId("yglee3");
        user3.setName("이초다3");
        user3.setPassword("married3");
        dao.add(user3);



        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter : " + ccm.getCounter());

    }
}
