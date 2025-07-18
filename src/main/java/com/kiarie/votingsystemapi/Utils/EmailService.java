package com.kiarie.votingsystemapi.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("victorkiariem@gmail.com"); // replace the email with your email for testing
        message.setSubject("Your Login OTP");
        message.setText("Your login OTP is: " + otp + "\nIt will expire in 5 minutes.");
        mailSender.send(message);
    }
}
