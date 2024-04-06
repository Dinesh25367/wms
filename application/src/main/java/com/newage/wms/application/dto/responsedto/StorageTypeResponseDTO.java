package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class StorageTypeResponseDTO extends DefaultFieldsResponseDTO{

    private Long id;
    private String code;
    private String name;
    private String status;
    private String note;

}
