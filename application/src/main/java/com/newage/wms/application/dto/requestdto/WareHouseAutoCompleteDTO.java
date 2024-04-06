package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Component
public class WareHouseAutoCompleteDTO {

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String id;

    private String wareHouseId;
    private String code;
    private String name;
    private String status;
    private String wareHouseLocationPrefix;
    private Character isBonded;

}
