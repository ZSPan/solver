package com.pan.solver.service.impl;

import com.pan.solver.event.Email;
import com.pan.solver.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.*;

/**
 * @author yemingfeng
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService, DisposableBean {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final LinkedBlockingQueue<Email> emails;
    private final ExecutorService executorService;
    private boolean stop;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        this.emails = new LinkedBlockingQueue<>();
        this.executorService = Executors.newSingleThreadExecutor();
        this.stop = false;

        CompletableFuture.runAsync(this::work, executorService);
    }

    @Override
    public void sendAsync(Email email) {
        try {
            emails.put(email);
        } catch (InterruptedException e) {
            log.error("put: {} error", email, e);
        }
    }

    @Override
    public void sendSync(Email email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(email.getText());
        javaMailSender.send(mimeMessage);
    }

    private void work() {
        log.info("start send emails from queue");
        while (!stop) {
            try {
                Email email = emails.take();
                sendSync(email);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        this.stop = true;
        this.executorService.awaitTermination(1, TimeUnit.MINUTES);
        log.info("stop send email, and destroy bean...");
    }
}