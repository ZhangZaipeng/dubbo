package com.example.common.email;

import com.example.common.bean.CoreConfigConstant;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class EmailSessionFactory {

  public static EmailEntity getEmailEntity() {
    EmailEntity emailEntity = new EmailEntity();
    emailEntity.setEmailUser(CoreConfigConstant.emailAccount);
    emailEntity.setEmailPassword(CoreConfigConstant.emailPwd);
    emailEntity.setType(Integer.valueOf(CoreConfigConstant.emailType));
    return emailEntity;
  }

  /**
   * 从缓存中获取邮箱获取邮箱session
   */
  public static Session getEmailSession(EmailEntity emailEntity) {
    Session session = null;
    if (1 == emailEntity.getType()) {
      session = createGmailSession(emailEntity);
    } else if (2 == emailEntity.getType()) {
      session = createEnterQQSession(emailEntity);
    } else if (3 == emailEntity.getType()) {
      session = create163Session(emailEntity);
    } else if (4 == emailEntity.getType()) {
      session = createAliyunSession(emailEntity);
    } else if (5 == emailEntity.getType()) {
      session = createQQSession(emailEntity);
    }
    if (session != null) {
      session.setDebug(false);
    }
    return session;
  }

  /**
   * 创建Session对象，此时需要配置传输的协议，是否身份认证
   */
  private static Session createEnterQQSession(EmailEntity emailEntity) {
    // 创建Properties 类用于记录邮箱的一些属性
    Properties props = new Properties();
    // 表示SMTP发送邮件，必须进行身份验证
    props.put("mail.smtp.auth", "true");
    //此处填写SMTP服务器
    props.put("mail.smtp.host", "smtp.exmail.qq.com");
    //端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
    props.put("mail.smtp.port", "587");
    // 此处填写你的账号
    props.put("mail.user", emailEntity.getEmailUser());
    // 此处的密码就是前面说的16位STMP口令
    props.put("mail.password", emailEntity.getEmailPassword());
    // 构建授权信息，用于进行SMTP进行身份验证
    Authenticator authenticator = new Authenticator() {

      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        // 用户名、密码
        //String userName = props.getProperty("mail.user");
        //String password = props.getProperty("mail.password");
        return new PasswordAuthentication(emailEntity.getEmailUser(),
            emailEntity.getEmailPassword());
      }
    };
    // 使用环境属性和授权信息，创建邮件会话
    Session mailSession = Session.getInstance(props, authenticator);
    return mailSession;
  }

  /**
   * 创建Session对象，此时需要配置传输的协议，是否身份认证
   */
  private static Session createQQSession(EmailEntity emailEntity) {
    // 创建Properties 类用于记录邮箱的一些属性
    Properties props = new Properties();
    // 表示SMTP发送邮件，必须进行身份验证
    props.put("mail.smtp.auth", "true");
    //此处填写SMTP服务器
    props.put("mail.smtp.host", "smtp.qq.com");
    //端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
    props.put("mail.smtp.port", "587");
    // 此处填写你的账号
    props.put("mail.user", emailEntity.getEmailUser());
    // 此处的密码就是前面说的16位STMP口令
    props.put("mail.password", emailEntity.getEmailPassword());
    // 构建授权信息，用于进行SMTP进行身份验证
    Authenticator authenticator = new Authenticator() {

      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        // 用户名、密码
        //String userName = props.getProperty("mail.user");
        //String password = props.getProperty("mail.password");
        return new PasswordAuthentication(emailEntity.getEmailUser(),
            emailEntity.getEmailPassword());
      }
    };
    // 使用环境属性和授权信息，创建邮件会话
    Session mailSession = Session.getInstance(props, authenticator);
    return mailSession;
  }

  /**
   * 创建gmail
   */
  private static Session createGmailSession(EmailEntity emailEntity) {
    // 创建Properties 类用于记录邮箱的一些属性
    Properties props = new Properties();
    props.put("mail.debug", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    //props.put("mail.smtp.ssl.enable", "true");
    //  props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
    props.put("mail.smtp.port", "587");
    // props.put("mail.smtp.socketFactory.port", "465");
    // props.put("mail.smtp.socketFactory.fallback", "false");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    // 此处填写你的账号
    props.put("mail.user", emailEntity.getEmailUser());
    // 此处的密码就是前面说的16位STMP口令
    props.put("mail.password", emailEntity.getEmailPassword());
    // 构建授权信息，用于进行SMTP进行身份验证
    Authenticator authenticator = new Authenticator() {

      protected PasswordAuthentication getPasswordAuthentication() {
        // 用户名、密码
        // String userName = props.getProperty("mail.user");
        //String password = props.getProperty("mail.password");
        //String userName = "hxguang666@gmail.com";
        //String password = "HelloWorld1234";
        return new PasswordAuthentication(emailEntity.getEmailUser(),
            emailEntity.getEmailPassword());
      }
    };
    // 使用环境属性和授权信息，创建邮件会话
    Session mailSession = Session.getDefaultInstance(props, authenticator);
    return mailSession;
  }

  private static Session create163Session(EmailEntity emailEntity) {
    // 创建Properties 类用于记录邮箱的一些属性
    Properties props = new Properties();
    props.put("mail.debug", "true");
    props.put("mail.smtp.host", "smtp.163.com");
    //props.put("mail.smtp.ssl.enable", "true");
    //  props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
//    props.put("mail.smtp.port", "25");
//     props.put("mail.transport.protocol", "smtp");
    // props.put("mail.smtp.socketFactory.fallback", "false");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    // 此处填写你的账号
    props.put("mail.user", emailEntity.getEmailUser());
    // 此处的密码就是前面说的16位STMP口令
    props.put("mail.password", emailEntity.getEmailPassword());
    // 构建授权信息，用于进行SMTP进行身份验证
    Authenticator authenticator = new Authenticator() {

      protected PasswordAuthentication getPasswordAuthentication() {
        // 用户名、密码
        // String userName = props.getProperty("mail.user");
        //String password = props.getProperty("mail.password");
        //String userName = "hxguang666@gmail.com";
        //String password = "HelloWorld1234";
        return new PasswordAuthentication(emailEntity.getEmailUser(),
            emailEntity.getEmailPassword());
      }
    };
    // 使用环境属性和授权信息，创建邮件会话
    Session mailSession = Session.getDefaultInstance(props, authenticator);
    return mailSession;
  }

  private static Session createAliyunSession(EmailEntity emailEntity) {
    // 创建Properties 类用于记录邮箱的一些属性
    Properties props = new Properties();
    // 表示SMTP发送邮件，必须进行身份验证
    props.put("mail.smtp.auth", "true");
    //此处填写SMTP服务器
    props.put("mail.smtp.host", "42.120.226.4");
    //端口号
    props.put("mail.smtp.port", "25");
    // 此处填写你的账号
    props.put("mail.user", emailEntity.getEmailUser());
    // 此处的密码就是前面说的16位STMP口令
    props.put("mail.password", emailEntity.getEmailPassword());
    // 构建授权信息，用于进行SMTP进行身份验证
    Authenticator authenticator = new Authenticator() {

      protected PasswordAuthentication getPasswordAuthentication() {
        // 用户名、密码
        //String userName = props.getProperty("mail.user");
        //String password = props.getProperty("mail.password");
        return new PasswordAuthentication(emailEntity.getEmailUser(),
            emailEntity.getEmailPassword());
      }
    };
    // 使用环境属性和授权信息，创建邮件会话
    Session mailSession = Session.getInstance(props, authenticator);
    return mailSession;
  }
}
