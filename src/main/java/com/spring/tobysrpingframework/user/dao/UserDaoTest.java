package com.spring.tobysrpingframework.user.dao;

import com.spring.tobysrpingframework.user.domain.User;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {

        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataFactory.class);
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user1 = new User("kyle1","이초다1","password1");
        User user2 = new User("kyle2","이초다2","password2");

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);

        assertThat(dao.getCount(), is(2));

        User userGet1 = dao.get(user1.getId());
        assertThat(userGet1.getName(), is(user1.getName()));
        assertThat(userGet1.getPassword(), is(user1.getPassword()));

        User userGet2 = dao.get(user2.getId());
        assertThat(userGet2.getName(), is(user2.getName()));
        assertThat(userGet2.getPassword(), is(user2.getPassword()));


    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataFactory.class);
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user1 = new User("kyle1","이초다1","password1");
        User user2 = new User("kyle2","이초다2","password2");
        User user3 = new User("kyle3","이초다3","password3");

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test(expected= EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException {

        GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        User user1 = new User("kyle1","이초다1","password1");
        dao.get(user1.getId());
    }




    public static void main(String[] args) {
        JUnitCore.main("com.spring.tobysrpingframework.user.dao.UserDaoTest");
    }
}
