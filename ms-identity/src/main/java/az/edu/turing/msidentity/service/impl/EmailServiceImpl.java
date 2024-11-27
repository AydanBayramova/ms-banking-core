package az.edu.turing.msidentity.service.impl;


import az.edu.turing.msidentity.model.MailBody;
import az.edu.turing.msidentity.service.inter.EmailService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final MailSender mailSender;

    public EmailServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSimpleMail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("ismayilzadetural712@gmail.com");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.body());
        mailSender.send(message);

    }
}