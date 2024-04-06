package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CFSResponseDTO {

    private Long id;
    private String name;
    private String code;
    private CFSResponseDTO.PortMasterDTO port;
    private boolean isPrimary;
    private String address1;
    private String address2;
    private String address3;
    private StateMasterDTO state;
    private CityMasterDTO city;
    private CountryMasterDTO country;
    private String pinCode;
    private String contactPerson;
    private String phoneNumber;
    private String mobileNumber;
    private String email;
    private String transhipperBondCode;
    private String note;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PortMasterDTO {

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
        private String name;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CountryMasterDTO {

        private Long id;
        private String name;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CityMasterDTO {

        private Long id;
        private String name;

    }

}
