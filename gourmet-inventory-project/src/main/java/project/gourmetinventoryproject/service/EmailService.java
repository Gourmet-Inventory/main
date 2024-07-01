package project.gourmetinventoryproject.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Email;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("gourmetinventorysmtp@gmail.com"); // Certifique-se de que este endereço de e-mail seja válido
            message.setTo(email.to());
            message.setSubject(email.subject());
            message.setText(email.body());
            mailSender.send(message);
        } catch (MailException e) {
            // Log error and handle exception
            System.err.println("Error sending email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
