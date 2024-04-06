package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.Date;

@Getter
@Setter
@Component
public class ZoneWMSResponseDTO {

    private Long id;
    private Long version;
    private String createdUser;
    private Date createdDate;
    private String updatedUser;
    private Date updatedDate;
    private BranchDTO branch;
    private CompanyDTO company;
    private GroupCompanyDTO groupCompany;
    private String code;
    private String name;
    private String defaultZone;
    private DoorDTO doorId;
    private LocationDTO inLocationId;
    private LocationDTO outLocationId;
    private String status;
    private String note;
    private StorageAreaDTO storageArea;
    private StorageTypeDTO storageType;
    private WareHouseDTO wareHouse;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class BranchDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CompanyDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class GroupCompanyDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class WareHouseDTO {

        private Long id;
        private String wareHouseId;
        private String code;
        private String name;
        private String status;
        private String wareHouseLocationPrefix;


    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class StorageAreaDTO {

        private Long id;
        private String code;
        private String name;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class StorageTypeDTO {

        private Long id;
        private String code;
        private String name;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class LocationDTO {

        private Long id;
        private String locationUid;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class DoorDTO {

        private Long id;
        private String code;
        private String name;
        private String status;
        private String note;

    }

}
