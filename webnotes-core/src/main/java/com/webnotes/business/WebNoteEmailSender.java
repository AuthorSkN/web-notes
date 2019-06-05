package com.webnotes.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class WebNoteEmailSender {

    private static final String EMAIL = "author.skn@gmail.com";

    @Autowired
    public JavaMailSender emailSender;

    public void sendEmail(String text) {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(EMAIL);
        message.setSubject("VERY IMPORTANT MESSAGE");
        message.setText(text);

        // Send Message!
        this.emailSender.send(message);
        System.out.println("EMAIL to " + EMAIL + " sent");
    }
}
