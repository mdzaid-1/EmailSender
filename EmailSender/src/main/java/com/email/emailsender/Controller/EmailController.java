package com.email.emailsender.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.email.emailsender.Services.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-multiple")
    public String sendEmailMultiple(@RequestParam("to") String to,
                                    @RequestParam("subject") String subject,
                                    @RequestParam("text") String text) {
        return emailService.sendEmailToMultiple(to.split(","), subject, text);
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam("to") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("text") String text) {
        return emailService.sendSimpleEmail(to, subject, text);
    }


    // Send email with attachment
    @PostMapping("/send-with-attachment")
    public String sendEmailWithAttachment(@RequestParam("to") String to,
                                          @RequestParam("subject") String subject,
                                          @RequestParam("text") String text,
                                          @RequestParam(value = "file", required = false) MultipartFile file) throws MessagingException, IOException {
        return emailService.sendEmailWithAttachment(to, subject, text, file);
    }
}

