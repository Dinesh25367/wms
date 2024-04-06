package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityResponseDTO {

    private Long id;
    private String name;
    private CountryMasterDTO country;
    private StateMasterDTO state;
    private String status;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountryMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StateMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

}
