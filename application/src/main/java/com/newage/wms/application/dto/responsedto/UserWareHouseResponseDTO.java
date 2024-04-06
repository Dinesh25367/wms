package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserWareHouseResponseDTO  extends DefaultFieldsResponseDTO {

    private Long id;
    private WareHouseDTO wareHouse;
    private String isPrimary;
    private String status;
    private UserNameDTO userName;

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
    public static class UserNameDTO{
        private String id;
        private String userName;
        private String primaryRoleId;
    }

}
