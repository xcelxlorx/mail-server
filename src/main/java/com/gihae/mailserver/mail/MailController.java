package com.gihae.mailserver.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MailController {

    private final MailService mailService;

    //회원가입 시 인증번호 발급
    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendCode(@RequestParam String email){
        mailService.sendCode(email);
        return ResponseEntity.ok().build();
    }
}
