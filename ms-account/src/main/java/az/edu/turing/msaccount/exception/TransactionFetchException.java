package az.edu.turing.msaccount.exception;

import az.edu.turing.msaccount.model.response.AccountResponse;

public class TransactionFetchException extends RuntimeException {

    private final AccountResponse accountResponseWithoutTransactions;

    public TransactionFetchException(String message, AccountResponse accountResponse) {
        super(message);
        this.accountResponseWithoutTransactions = accountResponse;
    }

    public AccountResponse getAccountResponseWithoutTransactions() {
        return accountResponseWithoutTransactions;
    }
}
