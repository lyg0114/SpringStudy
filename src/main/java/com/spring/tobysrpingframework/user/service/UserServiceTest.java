package com.spring.tobysrpingframework.user.service;

import com.spring.tobysrpingframework.user.dao.UserDao;
import com.spring.tobysrpingframework.user.domain.Level;
import com.spring.tobysrpingframework.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
            new User("kyle1","이초다1","password1", Level.BASIC, 49, 0),
            new User("kyle2","이초다2","password2", Level.BASIC, 50, 10),
            new User("kyle3","이초다3","password3", Level.SILVER, 60, 40),
            new User("kyle4","이초다4","password4", Level.SILVER, 60, 40),
            new User("kyle5","이초다5","password5", Level.GOLD, 100, 100)
        );
    }

    @Test
    public void upgradeLevels(){
        userDao.deleteAll();
        for(User user : users)
            userDao.add(user);
       userService.upgradeLevels();

    }


    private void checkLevel(User user, Level expectedLevel){
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }



}
