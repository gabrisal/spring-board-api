package com.gabrisal.api.mail.service;

import com.gabrisal.api.mail.vo.SendMailInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    @Value("${mail.sender}")
    private String sender;

    public void sendMail(SendMailInfo sendMailInfo) {
        try {
            // 단순 텍스트 구성 메시지 생성
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo((String[]) sendMailInfo
                                                .getReceiverMailList()
                                                .toArray(new String[sendMailInfo.getReceiverMailList().size()]));
            simpleMailMessage.setSubject(sendMailInfo.getSubject());
            simpleMailMessage.setText(sendMailInfo.getContent());
            mailSender.send(simpleMailMessage);
        } catch (MailException ex) {
            log.error("[ERROR] MailService :::");
            ex.printStackTrace();
        }
    }
}
