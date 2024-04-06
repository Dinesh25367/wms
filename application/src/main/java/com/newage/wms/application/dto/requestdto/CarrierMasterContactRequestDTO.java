package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrierMasterContactRequestDTO {

    private Long id;
    private Long branch;
    private Long agentCode;
    private Long agentName;
    private String accountNumber;

}
