package com.gihae.mailserver.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    //회원가입 시 인증번호 발급
    @PostMapping("/verification-code")
    public ResponseEntity<String> sendCode(@RequestBody String email){
        mailService.sendCode(email);
        return ResponseEntity.ok("ok");
    }

    //첨부된 파일도 함께 전송
    @PostMapping("/file")
    public ResponseEntity<String> sendFile(@RequestParam String email,
                                           @RequestParam String text,
                                           @RequestPart MultipartFile file){
        mailService.sendFile(email, text, file);
        return ResponseEntity.ok("ok");
    }
}
