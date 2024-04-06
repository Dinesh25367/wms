package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RackResponseDTO extends DefaultFieldsResponseDTO{

    private Long id;
    private String code;
    private String name;
    private String side;
    private String status;
    private String note;
    private WareHouseDTO wareHouse;
    private AisleDTO aisle;
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
    public static class AisleDTO{

        private Long id;
        private String code;
        private String name;
        private String status;
        private String note;
        private Long wareHouseId;
        private Long zoneId;
        private Long storageAreaId;
        private Long storageTypeId;

    }

}

