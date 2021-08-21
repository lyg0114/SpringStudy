package com.spring.tobysrpingframework.user.dao;

import com.spring.tobysrpingframework.user.domain.User;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {

        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataFactory.class);
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");


        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("yglee");
        user.setName("이초다");
        user.setPassword("springno1");

        dao.add(user);
        User user2 = dao.get(user.getId());
        assertThat(user2.getName(), is(user.getName()));
        assertThat(user2.getPassword(), is(user.getPassword()));

    }

    public static void main(String[] args) {
        JUnitCore.main("com.spring.tobysrpingframework.user.dao.UserDaoTest");
    }
}
