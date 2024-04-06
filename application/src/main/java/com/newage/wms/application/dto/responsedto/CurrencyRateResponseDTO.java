package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRateResponseDTO {

    private Long id;

    private CompanyMasterDTO company;

    private CurrencyMasterDTO accountingCurrency;

    private CurrencyMasterDTO toCurrency;

    private String currencyDate;

    private Double sellRate;

    private Double buyRate;

    private Double revaluationRate;

    private String createdBy;

    private Date createdDate;

    private String lastModifiedBy;

    private Date lastModifiedDate;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor

    public static class CompanyMasterDTO {
        private Long id;
        private String code;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor

    public static class CurrencyMasterDTO {
        private Long id;
        private String code;
        private String name;
    }


}
