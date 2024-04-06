package com.newage.wms.application.dto.requestdto;

import lombok.*;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class WareHouseRequestDTO {

    @Size(max = 30)
    private String user;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String groupCompanyId;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String companyId;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String branchId;

    @NotBlank
    @Size(max = 10)
    private String code;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^(Active|Inactive)$")
    private String status;

    @Size(max = 1000)
    private String note;

    @NotBlank
    @Size(max = 500)
    private String addressLine1;

    private PartyAddressDetailRequestDTO partyAddressDetail;

    @NotBlank
    @Size(max = 500)
    private String addressLine2;

    @NotBlank
    @Size(max = 500)
    private String addressLine3;

    private Date openDate;

    @Pattern(regexp = "^(Yes|No)$")
    private String isBonded;

    @Pattern(regexp = "^(Yes|No)$")
    private String isThirdPartyWareHouse;


    private LocationDTO crossDockLocation;

    @Size(max = 10)
    private String wareHouseLocationPrefix;


    @Valid
    private List<CustomerContactRequestDTOWareHouse> customerContactMasterList;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class LocationDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;
        private String locationUid;

    }
    @Getter
    @Setter
    @Component
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartyAddressDetailRequestDTO {

        private String addressLine1;
        private String addressLine2;
        private String addressLine3;

    }

}
