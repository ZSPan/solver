package com.pan.solver.service.impl;

import com.pan.solver.event.EmailEvent;
import com.pan.solver.service.EmailService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author yemingfeng
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService, DisposableBean {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final LinkedBlockingQueue<EmailEvent> emailEvents;
    private final ExecutorService executorService;
    private boolean stop;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        this.emailEvents = new LinkedBlockingQueue<>();
        this.executorService = Executors.newSingleThreadExecutor();
        this.stop = false;

        CompletableFuture.runAsync(this::work, executorService);
    }

    @Override
    public void sendAsync(EmailEvent emailEvent) {
      try {
        emailEvents.put(emailEvent);
      } catch (InterruptedException e) {
        log.error("put: {} error", emailEvent, e);
      }
    }

    @Override
    public void sendSync(EmailEvent emailEvent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(emailEvent.getTo());
        helper.setSubject(emailEvent.getSubject());
        helper.setText(emailEvent.getText());
        javaMailSender.send(mimeMessage);
    }

    private void work() {
        log.info("start send emails from queue");
        while(!stop) {
            try {
                EmailEvent emailEvent = emailEvents.take();
                sendSync(emailEvent);
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