    package az.edu.turing.msaccount.model.response;


    import az.edu.turing.msaccount.enums.AccountType;
    import az.edu.turing.msaccount.enums.BankType;
    import az.edu.turing.msaccount.enums.CurrencyType;
    import lombok.*;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class AccountResponse {

        private String username;
        private String message;
        private String email;
        private Float balance;
        private BankType bank;
        private AccountType type;
        private CurrencyType currency;
        private String number;

    }
