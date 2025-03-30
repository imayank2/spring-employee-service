package com.project.service.impl;

import com.project.exception.EmailSendException;
import com.project.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImp implements EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(String toEmail, String subject, String body) {
        sendEmail(new String[]{toEmail}, subject, body);
    }

    @Override
    @Async
    public void sendEmail(String[] toEmails, String subject, String body) {
        try {
            validateInput(toEmails, subject, body);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmails);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
            log.info("Email sent successfully to {}", (toEmails.length == 1) ? toEmails[0] : "multiple recipients");
        } catch (MailException ex) {
            log.error("Failed to send email to {}: {}", toEmails, ex.getMessage());
            throw new EmailSendException("Failed to send email", ex);
        } catch (IllegalArgumentException ex) {
            log.error("Invalid email parameters: {}", ex.getMessage());
            throw ex;
        }
    }

    private void validateInput(String[] toEmails, String subject, String body) {
        if (toEmails == null || toEmails.length == 0) {
            throw new IllegalArgumentException("Recipient email addresses cannot be empty");
        }
        for (String email : toEmails) {
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email address cannot be null or blank");
            }
        }
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Email subject cannot be empty");
        }
        if (body == null || body.isBlank()) {
            throw new IllegalArgumentException("Email body cannot be empty");
        }
    }
}