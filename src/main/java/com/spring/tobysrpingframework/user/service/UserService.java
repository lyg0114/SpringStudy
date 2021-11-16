package com.spring.tobysrpingframework.user.service;

import com.spring.tobysrpingframework.user.dao.UserDao;
import com.spring.tobysrpingframework.user.domain.Level;
import com.spring.tobysrpingframework.user.domain.User;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {

   public static final int MIN_LOGCOUNT_FOR_SLIVER = 50;
   public static final int MIN_RECCOMEND_FOR_GOLD = 30;

   UserDao userDao;
   private DataSource dataSource;


   public void setUserDao(UserDao userDao) {
       this.userDao = userDao;
   }

   public void setDataSource(DataSource dataSource){
     this.dataSource = dataSource;
   }

   public void upgradeLevels() throws Exception {

      PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
      TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

      try{
         List<User> users = userDao.getAll();
         for(User user : users){
            if(canUpgradeLevel(user)){
               upgradeLevel(user);
            }
         }
         transactionManager.commit(status);
      } catch (RuntimeException e){
         transactionManager.rollback(status);
         throw e;
      }

   }
   public void upgradeLevels2() throws Exception {

      TransactionSynchronizationManager.initSynchronization();
      Connection c = DataSourceUtils.getConnection(dataSource);
      c.setAutoCommit(false);

      try{
         List<User> users = userDao.getAll();
         for(User user : users){
            if(canUpgradeLevel(user)){
               upgradeLevel(user);
            }
         }
         c.commit();
      } catch (Exception e){
         c.rollback();
         throw e;
      } finally {
         DataSourceUtils.releaseConnection(c, dataSource);
         TransactionSynchronizationManager.unbindResource(this.dataSource);
         TransactionSynchronizationManager.clearSynchronization();
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
















