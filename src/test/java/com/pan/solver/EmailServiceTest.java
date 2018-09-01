package com.pan.solver;

import com.pan.solver.event.EmailEvent;
import com.pan.solver.service.EmailService;
import javax.mail.MessagingException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendMailAsync() throws MessagingException {
        emailService.sendAsync(new EmailEvent("393162333@qq.com", "async test", "test"));
    }

    @Test
    public void sendMailSync() throws MessagingException {
        emailService.sendSync(new EmailEvent("393162333@qq.com", "sync test", "test"));
    }
}