package az.edu.turing.mstransfer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CurrencyRateDto {

    private String base;

    private String target;

    private double rate;

    private double commission;
}
