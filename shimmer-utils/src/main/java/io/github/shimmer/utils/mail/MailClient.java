package io.github.shimmer.utils.mail;

import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

/**
 * <p>邮件</p>
 * Created on 2024-02-01 14:58
 *
 * @author yu_haiyang
 */
@Slf4j
public class MailClient {

    /**
     * 邮箱配置
     */
    private final Properties properties;

    /**
     * 发送人
     */
    private final String from;

    /**
     * 收件人，多个用逗号隔开
     */
    private final String to;
    /**
     * 抄送人，多个用逗号隔开
     */
    private final String cc;

    /**
     * 发件人邮箱授权码
     */
    private final String authCode;

    private final boolean debug;

    private MailClient(String from, String to, String cc, String authCode, boolean debug, Properties properties) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.authCode = authCode;
        this.debug = debug;
        this.properties = properties;
    }

    public void send(String subject, String mailContent, Attached... attached) throws MessagingException, IOException {
        log.debug("邮件配置：{}", properties);
        log.debug("发送人：{},", from);
        log.debug("接收人: {}", to);
        log.debug("抄送人：{}", cc);

        // 获取session
        Session session = Session.getDefaultInstance(properties);
        session.setDebug(debug);

        log.debug("session获取完成:{}", session);
        MimeMessage message = buildMessage(session, subject, mailContent, attached);
        log.debug("邮件主体构建完成:{}", message);


        // 获取Transport对象
        Transport transport = session.getTransport();
        log.debug("开始smtp认证");
        // smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
        transport.connect(from, authCode);
        //10：发送邮件
        log.debug("开始发送邮件");
        transport.sendMessage(message, message.getAllRecipients());
        log.debug("邮件发送完毕");
        transport.close();
    }

    private MimeMessage buildMessage(Session session, String subject, String mailContent, Attached... attached) throws MessagingException, IOException {
        MimeMessage message = new MimeMessage(session);
        // 发件人
        message.setFrom(new InternetAddress(from));
        // 收件人
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        // 抄送
        if (Objects.nonNull(cc) && !cc.isEmpty()) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
        }
        // 设置邮件主题
        message.setSubject(subject, "UTF-8");
        // 邮件发送时间
        message.setSentDate(new Date());

        // 构建一个多资源的邮件块 用来把 文本内容资源 和 图片资源关联；related代表多资源关联
        MimeMultipart mimeMultipart = new MimeMultipart("related");

        // 设置邮件内容
        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setContent(mailContent, "text/html;charset=UTF-8");
        mimeMultipart.addBodyPart(textBody);

        // 添加附件
        for (Attached file : attached) {
            MimeBodyPart attachedPart = new MimeBodyPart();
            ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(file.getInputStream(), "application/octet-stream");
            attachedPart.setDataHandler(new DataHandler(byteArrayDataSource));
            attachedPart.setFileName(MimeUtility.encodeText(file.getFileName()));
            mimeMultipart.addBodyPart(attachedPart);
        }

        // 设置我们处理好的资源（存放到Message）
        message.setContent(mimeMultipart);

        // 保存上面设置的邮件内容
        message.saveChanges();

        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Properties properties = new Properties();
        private String from;
        private String to;
        private String cc;
        private String authCode;

        private String protocol;
        private String host;
        private int port;
        private boolean auth = false;
        private boolean ssl = false;

        private boolean debug = false;

        public Builder mailTransportProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder mailSmtpAuth(boolean auth) {
            this.auth = auth;
            return this;
        }

        public Builder mailSmtpHost(String host) {
            this.host = host;
            return this;
        }

        public Builder mailSmtpPort(int port) {
            this.port = port;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder cc(String cc) {
            this.cc = cc;
            return this;
        }

        public Builder authCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public Builder ssl() {
            this.ssl = true;
            return this;
        }

        public Builder debug() {
            return debug(true);
        }

        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public MailClient build() {
            this.properties.setProperty("mail.transport.protocol", protocol);
            this.properties.setProperty("mail.smtp.auth", String.valueOf(auth));
            this.properties.setProperty("mail.smtp.host", host);
            this.properties.setProperty("mail.smtp.port", String.valueOf(port));
            if (ssl) {
                properties.setProperty("mail.smtp.ssl.enable", "true");
                properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));//设置ssl端口
                properties.setProperty("mail.smtp.socketFactory.fallback", "false");
                properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }
            return new MailClient(from, to, cc, authCode, debug, properties);
        }
    }
}
