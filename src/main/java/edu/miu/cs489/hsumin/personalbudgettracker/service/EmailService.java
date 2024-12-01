package edu.miu.cs489.hsumin.personalbudgettracker.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
