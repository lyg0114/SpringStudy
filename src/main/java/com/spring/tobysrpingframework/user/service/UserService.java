package com.spring.tobysrpingframework.user.service;

import com.spring.tobysrpingframework.user.dao.UserDao;
import com.spring.tobysrpingframework.user.domain.Level;
import com.spring.tobysrpingframework.user.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserService {

   public static final int MIN_LOGCOUNT_FOR_SLIVER = 50;
   public static final int MIN_RECCOMEND_FOR_GOLD = 30;

   UserDao userDao;
   PlatformTransactionManager transactionManager;

   public void setUserDao(UserDao userDao) {
      this.userDao = userDao;
   }
   public void setTransactionManager(PlatformTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }

   public void upgradeLevels() throws Exception {

      TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

      try{
         List<User> users = userDao.getAll();
         for(User user : users){
            if(canUpgradeLevel(user)){
               upgradeLevel(user);
            }
         }
         this.transactionManager.commit(status);
      } catch (RuntimeException e){
         this.transactionManager.rollback(status);
         throw e;
      }

   }

   protected void upgradeLevel(User user) {
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
















