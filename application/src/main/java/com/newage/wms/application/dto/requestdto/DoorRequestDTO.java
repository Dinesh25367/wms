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
public class DoorRequestDTO {

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
    @Size(max = 30)
    private String code;

    @NotNull
    @Size(max = 50)
    private String name;

    @Valid
    @NotNull
    private WareHouseDTO wareHouse;

    @NotNull
    @Pattern(regexp = "^(Active|Inactive)$")
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

}

