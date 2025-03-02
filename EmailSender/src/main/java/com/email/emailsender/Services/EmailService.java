package com.email.emailsender.Services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
            return "Email sent successfully!";
        } catch (MessagingException e) {
            return "Error sending email: " + e.getMessage();
        }
    }

    public String sendEmailToMultiple(String[] recipients, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setTo(recipients);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
            return "Emails sent successfully!";
        } catch (MessagingException e) {
            return "Error sending emails: " + e.getMessage();
        }
    }

    public String sendEmailWithAttachment(String to, String subject, String text, MultipartFile file) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); 

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);  

            if (file != null && !file.isEmpty()) {
                helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
            }

            mailSender.send(message);
            return "Email sent successfully with attachment!";
        } catch (MessagingException | IOException e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}


