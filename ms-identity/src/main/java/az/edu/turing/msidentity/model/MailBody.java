package az.edu.turing.msidentity.model;

import lombok.Builder;

@Builder
public record MailBody(String to,String subject, String body) {
}
