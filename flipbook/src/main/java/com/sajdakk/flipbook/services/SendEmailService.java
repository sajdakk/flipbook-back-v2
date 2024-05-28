package com.sajdakk.flipbook.services;

import com.sajdakk.flipbook.dtos.RegisterDto;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class SendEmailService {

    @Value("${EMAIL_LOGIN}")
    private String username;

    @Value("${EMAIL_PASSWORD}")
    private String password;

    final Properties prop;

    public SendEmailService() {
        this.prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }


    @RabbitListener(queues = "q.send-email")
    public void sendEmail(RegisterDto request) {

        log.info("Sending email to {}", request.getEmail());

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse( request.getEmail())
            );
            message.setSubject("Welcome to Flipbook!");

            String emailContent = String.format(
                    "Dear %s,\n\n" +
                            "Welcome to Flipbook!\n\n" +
                            "Thank you for registering on our platform. We are thrilled to have you with us.\n\n" +
                            "Flipbook is your ultimate destination for creating books and sharing your feedback about other positions. We hope you enjoy using our services.\n\n" +
                            "If you have any questions or need any assistance, feel free to reach out to our support team.\n\n" +
                            "Happy flipping!\n\n" +
                            "Best regards,\n" +
                            "The Flipbook Team",
                    request.getName()
            );

            message.setText(emailContent);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}