package com.spring.tobysrpingframework.user.service;

import com.spring.tobysrpingframework.user.domain.Level;
import com.spring.tobysrpingframework.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserTest {

   User user;

    @Before
    public void setUp(){
       user = new User();
    }

    @Test
    public void uupgradeLevel(){
        Level[] levels = Level.values();
        for(Level level : levels){
            if(level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel(), is(level.nextLevel()));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void cannotUpgradeLevel(){
        Level[] levels = Level.values();
        for(Level level : levels){
            if(level.nextLevel() != null) continue;
            user.setLevel(level);
            user.upgradeLevel();
        }
    }




}

