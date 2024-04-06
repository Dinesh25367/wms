package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@Component
public class LocationResponseDTO {

    private Long id;
    private Long version;
    private String createdUser;
    private Date createdDate;
    private String updatedUser;
    private Date updatedDate;
    private GroupCompanyDTO groupCompany;
    private CompanyDTO company;
    private BranchDTO branch;
    private WareHouseDTO wareHouse;
    private String locationUid;
    private String isBinLocation;
    private MasterLocation masterLocation;
    private ZoneDTO zone;
    private AisleDTO aisle;
    private RackDTO rack;
    private String columnCode;
    private String levelCode;
    private String levelOrder;
    private String position;
    private StorageAreaDTO storageArea;
    private StorageTypeDTO storageType;
    private LocTypeDTO locType;
    private LocationHandlingUomDTO locationHandlingUom;
    private String dimensionUnit;
    private String length;
    private String width;
    private String height;
    private String weight;
    private String volume;
    private String abc;
    private String mixedSkuAllowed;
    private String replenishmentAllowed;
    private String checkDigit;
    private String status;
    private Long locationPathSeq;
    private String deepSeq;
    private String note;

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
    public static class WareHouseDTO{

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
    public static class ZoneDTO{

        private Long id;
        private String code;
        private String name;
        private Long warehouseId;
        private Long storageAreaId;
        private Long storageTypeId;
        private String defaultZone;
        private Long doorId;
        private Long inLocationId;
        private Long outLocationId;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class AisleDTO {

        private Long id;
        private String code;
        private String name;
        private Long warehouseId;
        private Long zoneId;
        private Long storageAreaId;
        private Long storageTypeId;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class RackDTO{

        private Long id;
        private String code;
        private String name;
        private Long warehouseId;
        private Long aisleId;
        private String side;
        private Long storageAreaId;
        private Long storageTypeId;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class StorageAreaDTO{

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
    public static class StorageTypeDTO{

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
    public static class LocTypeDTO{

        private Long id;
        private String code;
        private String name;
        private String status;
        private String note;
        private String type;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class MasterLocation {

        private Long id;
        private String locationUid;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class LocationHandlingUomDTO{

        private String id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private Long groupCompany;
        private Long companyId;
        private Long branchId;
        private String code;
        private String name;
        private Long categoryId;
        private String reference;
        private Long ratio;
        private Long rounding;
        private String status;

    }

}
