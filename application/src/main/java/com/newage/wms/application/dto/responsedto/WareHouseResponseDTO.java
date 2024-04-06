package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class WareHouseResponseDTO {

    private Long id;
    private String wareHouseId;
    private String code;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private LocationDTO crossDockLocation;
    private String wareHouseLocationPrefix;
    private String note;
    private Date openDate;
    private String status;
    private String isThirdPartyWareHouse;
    private String isBonded;
    private Long version;
    private String createdUser;
    private Date createdDate;
    private String updatedUser;
    private Date updatedDate;
    private Long groupCompanyId;
    private Long companyId;
    private BranchResponseDTO branchMaster;
    private List<CustomerContactResponseDtoWareHouse> customerContactMasterList;



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

}
