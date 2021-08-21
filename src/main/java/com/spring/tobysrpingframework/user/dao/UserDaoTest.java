package com.spring.tobysrpingframework.user.dao;

import com.spring.tobysrpingframework.user.domain.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

//TESTCODE

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataFactory.class);
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("yglee");
        user.setName("이초다");
        user.setPassword("married");
        dao.add(user);
        User user2 = dao.get(user.getId());

        if(!user.getName().equals(user2.getName())){
            System.out.println("테스트 실패 (name)");
        }
        else if(!user.getPassword().equals(user2.getPassword())){
            System.out.println("테스트 실패 (password)");
        }
        else{
            System.out.println("조회 테스트 성공");
        }

    }
}
