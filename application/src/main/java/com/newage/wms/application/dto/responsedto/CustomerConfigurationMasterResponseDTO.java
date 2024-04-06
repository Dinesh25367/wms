package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CustomerConfigurationMasterResponseDTO extends DefaultFieldsResponseDTO {

    private Long configurationId;
    private Long id;
    private String module;
    private String configurationFlag;
    private String value;
    private String dataType;
    private String isMandatory;
    private String note;
    private CustomerMasterDTO customer;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CustomerMasterDTO{

        Long id;
        String name;
        String code;
    }

}
