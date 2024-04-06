package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CurrencyResponseDTO {

    private Long id;
    private String code;
    private String name;
    private String prefix;
    private String suffix;
    private Integer decimalPoints;
    private String symbol;
    private String status;
    private CountryMasterDTO country;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountryMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

}
