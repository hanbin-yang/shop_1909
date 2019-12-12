package com.sifo.listener;

import com.sifo.entity.Email;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class RabbitMQListener {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @RabbitListener(queuesToDeclare = @Queue(name = "mail_queue"))
    public void msgHandler(Email email) throws MessagingException {
        //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

        //标题
        mimeMessageHelper.setSubject(email.getSubject());

        //发送方
        mimeMessage.setFrom(from);
        //接收方
        mimeMessageHelper.setTo(email.getTo());
        //内容
        mimeMessageHelper.setText(email.getContext(),true);

        //发送附件
        //mimeMessageHelper.addAttachment("附件.jpg",new File("D:\\yhb\\images\\mi1.jpg"));

        //发送时间
        mimeMessageHelper.setSentDate(new Date());

        //发送邮件
        javaMailSender.send(mimeMessage);
    }
}
