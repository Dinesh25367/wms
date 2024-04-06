package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ConfigurationMasterResponseDTO extends DefaultFieldsResponseDTO{

    private Long id;
    private String module;
    private String screen;
    private String tab;
    private String configurationFlag;
    private String description;
    private String value;
    private String dataType;
    private String note;
    private String status;

}

