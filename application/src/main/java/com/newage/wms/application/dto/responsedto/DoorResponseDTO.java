
package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DoorResponseDTO extends DefaultFieldsResponseDTO{

    private Long id;
    private String code;
    private String name;
    private String status;
    private String note;
    private WareHouseDTO wareHouse;


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

}

