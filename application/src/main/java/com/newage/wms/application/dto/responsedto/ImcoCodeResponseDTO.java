package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ImcoCodeResponseDTO extends DefaultFieldsResponseDTO {
    private Long id;
    private String code;
    private String name;
    private String note;
    private String status;

}
