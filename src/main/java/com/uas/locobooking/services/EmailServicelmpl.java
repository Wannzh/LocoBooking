package com.uas.locobooking.services;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServicelmpl implements EmailService {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    ThymeleafService thymeleafService;

    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    private final String ADMIN_EMAIL = "admin@locobooking.com";
    private final String ADMIN_PERSONAL = "Admin LocoBooking";

    @Override
    public void sendMailMessage(String to, String subject, String text) {

        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo(to);
            message.setSubject(subject);

            Map<String, Object> variables = new HashMap<>();

            variables.put("mail", text);

            message.setText(thymeleafService.createContext("inline/send-mail.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendResetPassword(String email, String OTP) {
        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo(email);
            message.setSubject("RESET PASSWORD | LOCOBOOKING");

            Map<String, Object> variables = new HashMap<>();

            variables.put("otp", OTP);

            message.setText(thymeleafService.createContext("inline/send-reset-mail.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, me.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    

    @Override
    public void sendRequestDeleteAccount() {
        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo("dedydarmawan876@gmail.com");
            message.setSubject("Account Deletion Request | Locobooking");

            Map<String, Object> variables = new HashMap<>();

            variables.put("email", "dedydarmawan876@gmail");

            message.setText(thymeleafService.createContext("send-delete-confirmation.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSimpleMailMessage(String to, String subject, String text) {
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@locobooking.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}

