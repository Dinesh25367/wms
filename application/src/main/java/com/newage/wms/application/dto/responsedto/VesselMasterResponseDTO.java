package com.newage.wms.application.dto.responsedto;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class VesselMasterResponseDTO {

    private Long id;
    private String name;
    private String shortName;
    private VesselCountryDTO vesselCountry;
    private String callSign;
    private String imoNumber;
    private String note;
    private String status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VesselCountryDTO {

        private Long id;
        private String code;
        private String name;
        private String flag;

    }

}
