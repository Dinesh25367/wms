package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component

public class ConfigurationFlagMasterResponseDTO extends DefaultFieldsResponseDTO {

    private Long id;
    private String code;
    private String screen;
    private String configurationFlag;
    private String description;
    private String status;

}
