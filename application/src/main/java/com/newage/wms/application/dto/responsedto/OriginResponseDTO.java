package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OriginResponseDTO {

    private Long id;
    private String code;
    private String name;
    private String status;
    private String transportMode;
    private String countryCode;
    private CountryDTO country;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountryDTO {

        private Long id;
        private String code;
        private String name;

    }

}
