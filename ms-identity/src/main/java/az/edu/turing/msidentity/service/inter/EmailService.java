package az.edu.turing.msidentity.service.inter;

import az.edu.turing.msidentity.model.MailBody;

public interface EmailService {
    public void sendSimpleMail(MailBody mailBody);
}
