package com.spring.tobysrpingframework.user.service;

import com.spring.tobysrpingframework.user.dao.UserDao;
import com.spring.tobysrpingframework.user.domain.Level;
import com.spring.tobysrpingframework.user.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

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
      sendUpgradeEMail(user);
   }

   private void sendUpgradeEMail(User user) {
      Properties props = new Properties();
      props.put("mail.smtp.host", "mail.ksug.org");
      Session s = Session.getInstance(props, null);

      MimeMessage message = new MimeMessage(s);
      try{
         message.setFrom(new InternetAddress("useradmin@ksug.org"));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
         message.setSubject("Upgrade 안내");
         message.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");

         Transport.send(message);

      } catch (AddressException e){
         throw new RuntimeException(e);
      } catch (MessagingException e){
         throw new RuntimeException(e);
      }


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
















