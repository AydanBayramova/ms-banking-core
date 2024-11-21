package az.edu.turing.msidentity.client;

import java.math.BigDecimal;

public enum BankType {

    BANK_OF_BAKU(new BigDecimal("0.04"), new BigDecimal("0.03")),
    KAPITAL_BANK(new BigDecimal("0.015"), new BigDecimal("0.025")),
    GUNAY_BANK(new BigDecimal("0.00"), new BigDecimal("0.00")),
    NERGIZ_BANK(new BigDecimal("0.01"), new BigDecimal("0.015"));

    private final BigDecimal outgoingFee;
    private final BigDecimal incomingFee;

    BankType(BigDecimal outgoingFee, BigDecimal incomingFee) {
        this.outgoingFee = outgoingFee;
        this.incomingFee = incomingFee;
    }
}