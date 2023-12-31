package com.gihae.mailserver.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789!@#$%";
    private static final int CODE_LENGTH = 8;

    //인증 번호 발송
    public void sendCode(String email){
        String code = createCode();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); //메일 수신자
        message.setSubject("회원가입 인증 번호입니다."); //메일 제목
        message.setText("인증 번호 : " + code); //메일 내용

        javaMailSender.send(message);
    }

    //파일 전송
    public void sendFile(String email, String text, MultipartFile file){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject("파일 첨부 메일입니다.");
            helper.setText(text);

            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes());
            helper.addAttachment(file.getOriginalFilename(), byteArrayResource);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createCode(){
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            StringBuilder code = new StringBuilder();

            for(int i = 0; i <= CODE_LENGTH; i++){
                int index = secureRandom.nextInt(CHARACTERS.length());
                char ch = CHARACTERS.charAt(index);
                code.append(ch);
            }

            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
