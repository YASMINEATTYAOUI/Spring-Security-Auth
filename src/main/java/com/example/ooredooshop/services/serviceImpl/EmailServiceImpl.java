package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.models.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender javaMailSender;
/*
    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP for Password Reset");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }

 */
    /*
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("");
        message.setSubject("Your OTP for Password Reset");
        message.setText("Your OTP is: " + text);
        javaMailSender.send(message);
}

     */
public void sendSimpleMessage(MailBody mailBody) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailBody.getTo());
    message.setFrom("yasmineatt23@gmail.com");
    message.setSubject(mailBody.getSubject());
    message.setText(mailBody.getText());
    javaMailSender.send(message);
}


}
