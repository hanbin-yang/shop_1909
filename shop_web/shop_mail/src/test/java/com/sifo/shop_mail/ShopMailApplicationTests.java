package com.sifo.shop_mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@SpringBootTest
class ShopMailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

	@Test
	void contextLoads() throws MessagingException {
	    //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

        //标题
        mimeMessageHelper.setSubject("会员到期通知");

        //发送方
        mimeMessage.setFrom("verygoodwlk@sina.cn");
        //接收方
        mimeMessageHelper.setTo("386417255@qq.com");
        //内容
        mimeMessageHelper.setText("会员到期，请及时续费<img src='https://pic.sogou.com/pory=copyright_pc&mode=1#did=2&findex=undefined&groupId=null&query=' />",true);

        //发送附件
        mimeMessageHelper.addAttachment("附件.jpg",new File("D:\\yhb\\images\\mi1.jpg"));

        //发送时间
        mimeMessageHelper.setSentDate(new Date());

        //发送邮件
        javaMailSender.send(mimeMessage);
    }

}
