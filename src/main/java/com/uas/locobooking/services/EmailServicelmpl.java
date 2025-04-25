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

import com.uas.locobooking.models.Booking;
import com.uas.locobooking.models.Customer;
import com.uas.locobooking.models.JustLogged;
import com.uas.locobooking.repositories.BookingRepository;
import com.uas.locobooking.repositories.CustomersRepository;
import com.uas.locobooking.repositories.JustLoggedRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServicelmpl implements EmailService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomersRepository customerRepository;

    @Autowired
    private JustLoggedRepository justLoggedRepository;

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
    public void successCancel(){
        try{

            JustLogged justLogged = justLoggedRepository.findFirstByOrderByIdAsc();

            Booking booking = bookingRepository.findByCustomer(justLogged.getCustomer())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking tidak ditemukan"));


            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo(justLogged.getCustomer().getEmail());
            message.setSubject("PEMBATALAN TIKET SUCCESS");

            Map<String, Object> variables = new HashMap<>();

            variables.put("booking", booking);

            message.setText(thymeleafService.createContext("inline/cancel-success-mail.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        
    }

   @Override
    public void successfulTicketBooking() {
    try {

        JustLogged justLogged = justLoggedRepository.findFirstByOrderByIdAsc();

        // 2. Cari booking milik customer itu (ambil yang terbaru misalnya)
        Booking booking = bookingRepository.findByCustomer(justLogged.getCustomer())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking tidak ditemukan"));

            System.out.println("nani "+ justLogged);
        
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(
                mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
        message.setTo(justLogged.getCustomer().getEmail());
        message.setSubject("JADWAL TIKET ANDA, SILAHKAN LAKUKAN PEMBAYARAN");

        Map<String, Object> variables = new HashMap<>();

        variables.put("booking", booking);

        message.setText(thymeleafService.createContext("inline/success-booking-mail.html", variables), true);
        emailSender.send(mimeMessage);
        // String htmlContent = thymeleafService.createContext("inline/success-booking-mail.html", variables);
        // message.setText(htmlContent, true);
        // emailSender.send(mimeMessage);

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

