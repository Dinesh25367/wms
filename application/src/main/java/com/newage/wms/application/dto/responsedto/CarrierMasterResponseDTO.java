package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrierMasterResponseDTO {

    private Long id;
    private String transportMode;
    private String name;
    private String code;
    private CountryMasterDTO country;
    private String carrierPrefix;
    private String scacCode;
    private String localCarrier;
    private String status;
    private String image;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    List<CarrierMasterContactResponseDTO> contacts;

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

