package com.pan.solver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolverApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void sendMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("13631558898@163.com");
        helper.setTo("13631558898@163.com");
        helper.setSubject("发邮件的测试");
        helper.setText("测试的邮件");
        javaMailSender.send(mimeMessage);
    }

}
