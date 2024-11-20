package az.edu.turing.msaccount.entity;


import az.edu.turing.msaccount.enums.AccountStatus;
import az.edu.turing.msaccount.enums.AccountType;
import az.edu.turing.msaccount.enums.BankType;
import az.edu.turing.msaccount.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue
    private Long accountId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10, name = "account_number")
    private String number;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, name = "is_active")
    private AccountStatus active;

    @Column(nullable = false)
    private Float balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BankType bank;

    @Column(name = "opening_date", nullable = false)
    private LocalDateTime openingDate;

    @Column(nullable = false, length = 50, name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "is_account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "is_account_non_locked")
    private boolean accountNonLocked = true;
}
