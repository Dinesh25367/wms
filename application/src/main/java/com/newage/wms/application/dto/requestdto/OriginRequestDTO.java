package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OriginRequestDTO {

    private String code;
    private String name;
    private String status;
    private String transportMode;
    private String countryCode;
    private Long countryId;

}
