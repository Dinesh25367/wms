package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateRequestDTO {

    private Long companyId;

    private Long accountingCurrencyId;

    private Long toCurrencyId;

    private Date currencyDate;

    private Double sellRate;

    private Double buyRate;

    private Double revaluationRate;


}