package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Component
@NoArgsConstructor
public class LocationRequestDTO {

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

    @Valid
    @NotNull
    private WareHouseDTO wareHouse;

    @NotNull
    @Size(max = 30,message = "Exceeds size limit")
    private String locationUid;

    @Pattern(regexp = "^(Yes|No)$",message = "Accepts only Yes or No")
    @NotNull
    private String isBinLocation;

    @Valid
    private MasterLocationDTO masterLocation;

    @Valid
    private ZoneDTO zone;

    @Valid
    private AisleDTO aisle;

    @Valid
    private RackDTO rack;

    @Size(max = 5,message = "Exceeds size limit")
    private String columnCode;

    @Size(max = 5,message = "Exceeds size limit")
    private String levelCode;

    @Size(max = 2,message = "Exceeds size limit")
    @Pattern(regexp = "^[0-9]*$",message = "Must be a number")
    private String levelOrder;

    @Size(max = 3,message = "Exceeds size limit")
    @Pattern(regexp = "^[0-9]*$",message = "Must be a number")
    private String position;

    @Valid
    @NotNull
    private StorageAreaDTO storageArea;

    @Valid
    @NotNull
    private StorageTypeDTO storageType;

    @Valid
    @NotNull
    private LocTypeDTO locType;

    private LocationHandlingUomDTO locationHandlingUom;

    @Size(max = 30,message = "Exceeds size limit")
    @NotNull
    private String dimensionUnit;

    @Pattern(regexp = "^[0-9.]+$",message = "Must be a number")
    @Size(max = 10,message = "Exceeds size limit")
    @NotNull
    private String length;

    @Pattern(regexp = "^[0-9.]+$",message = "Must be a number")
    @NotNull
    @Size(max = 10,message = "Exceeds size limit")
    private String width;

    @Pattern(regexp = "^[0-9.]+$",message = "Must be a number")
    @NotNull
    @Size(max = 10,message = "Exceeds size limit")
    private String height;

    @Pattern(regexp = "^[0-9.]+$",message = "Must be a number")
    @NotNull
    @Size(max = 15,message = "Exceeds size limit")
    private String weight;

    @Pattern(regexp = "^[0-9.]+$",message = "Must be a number")
    @NotNull
    @Size(max = 15,message = "Exceeds size limit")
    private String volume;

    @Size(max = 20,message = "Exceeds size limit")
    @NotNull
    private String abc;

    @Pattern(regexp = "^(Yes|No)$",message = "Accepts only Yes or No")
    @NotNull
    private String mixedSkuAllowed;

    @Pattern(regexp = "^(Yes|No)$",message = "Accepts only Yes or No")
    @NotNull
    private String replenishmentAllowed;

    @Size(max = 10,message = "Exceeds size limit")
    private String checkDigit;

    @Size(max = 10,message = "Exceeds size limit")
    @NotNull
    private String status;

    @Size(max = 10,message = "Exceeds size limit")
    @Pattern(regexp = "^[0-9]*$",message = "Must be a number")
    private String locationPathSeq;

    @Size(max = 30)
    private String deepSeq;

    @Size(max = 4000,message = "Exceeds size limit")
    private String note;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class MasterLocationDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String locationUid;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class WareHouseDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

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

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;
        private Long warehouseId;
        private String defaultZone;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class AisleDTO {

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

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

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

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

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

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

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

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

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

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
    public static class LocationHandlingUomDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;
        private String status;
        private String note;
        private String type;

    }

}
