package com.uas.locobooking.services;

public interface EmailService {

    void sendSimpleMailMessage(String to, String subject, String text);

    void sendMailMessage(String to, String subject, String text);

    void sendResetPassword(String email, String oTP);

    void sendRequestDeleteAccount();

    void successfulTicketBooking();

    void successCancel();

}
