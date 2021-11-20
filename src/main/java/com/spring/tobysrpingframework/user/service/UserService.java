package com.spring.tobysrpingframework.user.service;

import com.spring.tobysrpingframework.user.dao.UserDao;
import com.spring.tobysrpingframework.user.domain.Level;
import com.spring.tobysrpingframework.user.domain.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import java.util.List;

public class UserService {

   public static final int MIN_LOGCOUNT_FOR_SLIVER = 50;
   public static final int MIN_RECCOMEND_FOR_GOLD = 30;

   UserDao userDao;
   PlatformTransactionManager transactionManager;
   private MailSender mailSender;

   public void setMailSender(MailSender mailSender) {
      this.mailSender = mailSender;
   }
   public void setUserDao(UserDao userDao) {
      this.userDao = userDao;
   }
   public void setTransactionManager(PlatformTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }

   public void upgradeLevels() throws Exception {

      TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

      try{

         upgradeLevelsInternal();
         this.transactionManager.commit(status);

      } catch (RuntimeException e){
         this.transactionManager.rollback(status);
         throw e;
      }

   }

   private void upgradeLevelsInternal() {
      List<User> users = userDao.getAll();
      for(User user : users){
         if(canUpgradeLevel(user)){
            upgradeLevel(user);
         }
      }
   }

   protected void upgradeLevel(User user) {
      user.upgradeLevel();
      userDao.update(user);
      sendUpgradeEMail(user);
   }

   private void sendUpgradeEMail(User user) {

      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(user.getEmail());
      mailMessage.setFrom("useradmin@ksug.org");
      mailMessage.setSubject("Upgrade 안내");
      mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

      this.mailSender.send(mailMessage);
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
















