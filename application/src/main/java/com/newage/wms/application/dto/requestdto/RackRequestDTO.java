package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Component
@NoArgsConstructor
public class RackRequestDTO {

    @NotNull
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

    @NotNull
    private String code;

    @NotNull
    private String name;

    @Valid
    @NotNull
    private WareHouseDTO wareHouse;

    @Valid
    @NotNull
    private AisleDTO aisle;

    private String side;

    @Valid
    @NotNull
    private StorageAreaDTO storageArea;

    @Valid
    @NotNull
    private StorageTypeDTO storageType;

    @NotNull
    private String status;

    private String note;


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
    public static class AisleDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;
        private String status;
        private String note;
        private String wareHouseId;
        private String zoneId;
        private String storageAreaId;
        private String storageTypeId;

    }

}
