package az.edu.turing.mstransfer.exception;

public class CurrencyRateNotFounException extends RuntimeException {
    public CurrencyRateNotFounException(String message) {
        super(message);
    }
}
