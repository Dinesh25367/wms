package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class
CarrierMasterContactResponseDTO {

    private Long id;
    private LocationMasterDTO branch;
    private AgentMasterDTO agentCode;
    private AgentMasterDTO agentName;
    private String agentEmail;
    private String accountNumber;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AgentMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

}
