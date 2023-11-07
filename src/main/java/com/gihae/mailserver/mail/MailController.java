package com.gihae.mailserver.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/auth")
public class MailController {

    private final MailService mailService;

    //회원가입 시 인증번호 발급
    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendCode(){
        String code = mailService.sendCode();
        return ResponseEntity.ok().body(code);
    }

    //임시 비밀번호 발급
    @PostMapping("/send-password")
    public ResponseEntity<String> sendReset(){
        String password = mailService.sendPassword();
        return ResponseEntity.ok().body(password);
    }
}
