package az.edu.turing.mscurrency.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class CurrencyRateDto {

    private String base;
    private String target;
    private double rate;
}
