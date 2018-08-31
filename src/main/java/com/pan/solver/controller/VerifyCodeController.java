package com.pan.solver.controller;

import com.pan.solver.entity.VerifyCode.Type;
import com.pan.solver.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yemingfeng
 */

@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {

  private final VerifyCodeService verifyCodeService;

  @Autowired
  public VerifyCodeController(VerifyCodeService verifyCodeService) {
    this.verifyCodeService = verifyCodeService;
  }

  @PostMapping("")
  public void sendVerifyCode(@RequestParam("email") String email,
      @RequestParam("type") Type type) {
    verifyCodeService.sendVerifyCode(email, type);
  }
}