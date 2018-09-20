package com.pan.solver.service;

import com.pan.solver.event.Email;

import javax.mail.MessagingException;

/**
 * @author yemingfeng
 */
public interface EmailService {

    void sendAsync(Email email) throws MessagingException;

    void sendSync(Email email) throws MessagingException;

}
