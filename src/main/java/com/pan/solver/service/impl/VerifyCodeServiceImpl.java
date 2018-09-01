package com.pan.solver.service.impl;

import com.pan.solver.entity.VerifyCode;
import com.pan.solver.entity.VerifyCode.Type;
import com.pan.solver.event.EmailEvent;
import com.pan.solver.repository.VerifyCodeRepository;
import com.pan.solver.service.EmailService;
import com.pan.solver.service.VerifyCodeService;
import com.pan.solver.util.VerifyCodeUtil;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yemingfeng
 */
@Slf4j
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private final EmailService emailService;
    private final VerifyCodeRepository verifyCodeRepository;

    @Autowired
    public VerifyCodeServiceImpl(VerifyCodeRepository verifyCodeRepository,
        EmailService emailService) {
        this.emailService = emailService;
        this.verifyCodeRepository = verifyCodeRepository;
    }

    @Override
    public void sendVerifyCode(String email, Type type) {
        String code = VerifyCodeUtil.generateVerifyCode();

        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCode(code);
        verifyCode.setEmail(email);
        verifyCode.setType(type);
        verifyCode.setUsed(false);
        verifyCodeRepository.save(verifyCode);

        try {
            emailService.sendAsync(new EmailEvent(email, "register", code));
        } catch (MessagingException e) {
            log.error("", e);
        }
    }

    @Override
    public VerifyCode findLatestVerifyCode(String email, Type type) {
        return verifyCodeRepository.findFirstByEmailEqualsAndTypeEqualsOrderByCreationDesc(email, type);
    }
}