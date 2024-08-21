package project.gourmetinventoryproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.gourmetinventoryproject.domain.ContactForm;
import project.gourmetinventoryproject.domain.Email;
import project.gourmetinventoryproject.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Email email) {
        try {
            emailService.sendEmail(email);
            return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to send email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<String> handleContactForm(@RequestBody ContactForm contactForm) {
        try {
            // Formate o corpo do e-mail com os dados recebidos
            String subject = "Contato de " + contactForm.fullName();
            String body = String.format(
                    "Nome: %s\nEmail: %s\nTelefone: %s\nEmpresa: %s",
                    contactForm.fullName(),
                    contactForm.email(),
                    contactForm.phone(),
                    contactForm.company()
            );

            // Enviar e-mail usando o EmailService
            Email email = new Email("gourmetinventorysmtp@gmail.com", subject, body);
            emailService.sendEmail(email);

            return new ResponseEntity<>("Contact form submitted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to process contact form: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
