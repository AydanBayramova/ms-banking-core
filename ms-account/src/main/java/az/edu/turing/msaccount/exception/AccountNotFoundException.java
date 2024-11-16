package az.edu.turing.msaccount.exception;

import java.io.Serial;

public class AccountNotFoundException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(String message) {
        super(message);
    }

}

