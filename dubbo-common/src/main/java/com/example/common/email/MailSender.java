package com.example.common.email;


import com.alibaba.fastjson.JSONObject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义发送邮件验证
 */
public class MailSender {

  private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
  public static String MAIL_AUTH_CODE = "<p>&nbsp;&nbsp;您好，您的验证码为：${code}</p></br><p>&nbsp;&nbsp;该验证码5分钟内有效，勿重复发送。</p></br><p>若该邮件不是由您本人主动申请发送，则您的帐号信息可能已泄露，请尽快修改帐号密码。</p>";
  public static String MAIL_MODEL = "<p>请点击&nbsp;&nbsp;<u><a href='${url}'>完成注册</a></u>&nbsp;&nbsp;,如非本人操作请忽略此次邮件</p></br>如网页未成功跳转,请复制链接地址在浏览器中打开:${url}";
  public static String CASH_MODEL = "<p>点击&nbsp;&nbsp;<u><a href='${url}'>确认提现操作</a></u>&nbsp;&nbsp;,如非本人操作请尽快修改密码，加强认证</p></br>如网页未成功跳转,请复制链接地址在浏览器中打开:${url}";


  /**
   * 传入Session、MimeMessage对象，创建 Transport 对象发送邮件
   */
  public static void sendMail(Session session, MimeMessage msg) throws Exception {
    // 由 Session 对象获得 Transport 对象
    Transport.send(msg, msg.getRecipients(Message.RecipientType.TO));
  }

  /**
   * 发送邮件
   *
   * @param mailContent 邮件内容实体
   */
  public static boolean sendSingleMail(MailContent mailContent) {

    EmailEntity emailEntity = EmailSessionFactory.getEmailEntity();

    logger.info("emailEntity" + JSONObject.toJSONString(emailEntity));
    mailContent.setUserForm(emailEntity.getEmailUser());
    // 指定使用SMTP协议来创建Session对象
    Session session = EmailSessionFactory.getEmailSession(emailEntity);
    try {
      // 使用邮件创建类获得Message对象
      MimeMessage mail = new WithAttachmentMessage().createMessage(session, mailContent);
      // 发送邮件
      MailSender.sendMail(session, mail);

      return true;
    } catch (Exception e) {
      logger.info("error", e);
    }
    return false;
  }

  public static boolean sendWarningMail(String title, String body) {
    try {
      MailContent mailContent = new MailContent();
      mailContent.setUserTo(new String[]{"1209028017@qq.com"});
      mailContent.setTitle(title);
      mailContent.setBody(body);
      // 指定使用SMTP协议来创建Session对象
      EmailEntity emailEntity = EmailSessionFactory.getEmailEntity();
      mailContent.setUserForm(emailEntity.getEmailUser());
      Session session = EmailSessionFactory.getEmailSession(emailEntity);
      // 使用邮件创建类获得Message对象
      MimeMessage mail = new WithAttachmentMessage().createMessage(session, mailContent);
      // 发送邮件
      MailSender.sendMail(session, mail);

      return true;
    } catch (Exception e) {
      logger.info("发送邮件失败", e);
    }

    return false;
  }


  /**
   * 队列发送邮件
   *
   * @param mailContent 邮件内容实体
   */
  public static boolean senderQueueMail(MailContent mailContent) throws Exception {
    // 判断邮件是否发送成功
    boolean flag = true;
    // 指定使用SMTP协议来创建Session对象
    EmailEntity emailEntity = EmailSessionFactory.getEmailEntity();
    mailContent.setUserForm(emailEntity.getEmailUser());
    Session session = EmailSessionFactory.getEmailSession(emailEntity);
    try {
      // 使用邮件创建类获得Message对象
      MimeMessage mail = new WithAttachmentMessage().createMessage(session, mailContent);
      // 发送邮件
      MailSender.sendMail(session, mail);
    } catch (Exception e) {
      flag = false;
      logger.info("send email error", e);
    }
    return flag;
  }

  public static void main(String[] args) {
    MailContent mailContent = new MailContent();
    mailContent.setUserTo(new String[]{"282354184@qq.com"});
    mailContent.setTitle("完成用户注册");
    mailContent.setBody(MailSender.MAIL_AUTH_CODE.replace("${url}", "www.baidu.com"));
    MailSender.sendSingleMail(mailContent);
  }
}
