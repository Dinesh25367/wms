package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class InventoryStatusResponseDTO extends DefaultFieldsResponseDTO {

    private Long id;
    private String name;
    private String code;
    private  String Description;
    private String note;
    private String isSaleable;

}
