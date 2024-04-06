package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AisleResponseDTO extends DefaultFieldsResponseDTO {

    private Long id;
    private String code;
    private String name;
    private String status;
    private String note;
    private WareHouseDTO wareHouse;
    private ZoneDTO zone;
    private StorageAreaDTO storageArea;
    private StorageTypeDTO storageType;

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

}
