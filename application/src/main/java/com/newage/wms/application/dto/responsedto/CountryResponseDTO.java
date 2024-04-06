package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDTO {

    private Long id;
    private String code;
    private String name;
    private String status;
    private String iso3;
    private String capital;
    private String nativeName;
    private String flag;
    private String flagUnicode;
    private String phoneCode;
    private RegionMasterDTO region;
    private CurrencyMasterDTO currency;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegionMasterDTO {

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
