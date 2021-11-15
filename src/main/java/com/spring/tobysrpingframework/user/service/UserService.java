package com.spring.tobysrpingframework.user.service;

import com.spring.tobysrpingframework.user.dao.UserDao;
import com.spring.tobysrpingframework.user.domain.Level;
import com.spring.tobysrpingframework.user.domain.User;

import java.util.List;

public class UserService {

   UserDao userDao;
   public static final int MIN_LOGCOUNT_FOR_SLIVER = 50;
   public static final int MIN_RECCOMEND_FOR_GOLD = 30;

   public void setUserDao(UserDao userDao) {
       this.userDao = userDao;
   }

   public void upgradeLevels(){

      List<User> users = userDao.getAll();
      for(User user : users){
         if(canUpgradeLevel(user)){
            upgradeLevel(user);
         }
      }

   }

   private void upgradeLevel(User user) {
      user.upgradeLevel();
      userDao.update(user);
   }

   private boolean canUpgradeLevel(User user) {
      Level currentLevel = user.getLevel();
      switch(currentLevel){
         case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SLIVER);
         case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
         case GOLD: return false;
         default: throw new IllegalArgumentException("Uknown Level : " + currentLevel);
      }
   }

   public void add(User user) {
     if(user.getLevel() == null) user.setLevel(Level.BASIC);
     userDao.add(user);
   }
}
















