package com.newage.wms.application.dto.requestdto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class VesselMasterRequestDTO {

    private String name;
    private String shortName;
    private Long vesselCountry;
    private String callSign;
    private String imoNumber;
    private String note;
    private String status;

}