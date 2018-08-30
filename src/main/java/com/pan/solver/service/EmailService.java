package com.pan.solver.service;

import com.pan.solver.event.EmailEvent;
import javax.mail.MessagingException;

/**
 * @author yemingfeng
 */
public interface EmailService {

    void sendAsync(EmailEvent emailEvent) throws MessagingException;

    void sendSync(EmailEvent emailEvent) throws MessagingException;

}
