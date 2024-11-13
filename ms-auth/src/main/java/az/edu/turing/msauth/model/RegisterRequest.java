package az.edu.turing.msauth.model;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
}
