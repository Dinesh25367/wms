package com.newage.wms.application.dto.requestdto;


import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class CustomerConfigurationRequestDTO {

    @Size(max = 30)
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
    private String module;

    @NotNull
    @Size(max = 50)
    private String configurationFlag;

    @NotNull
    @Size(max = 5000)
    private String value;

    @NotNull
    @Size(max = 10)
    private String dataType;

    @NotNull
    @Size(max = 15)
    private String isMandatory;

    @Size(max = 5000)
    private String note;

    @Valid
    @NotNull
    private CustomerMasterDTO customer;

    @Getter
    @Setter
    @NoArgsConstructor
    @Component
    public static class CustomerMasterDTO{

        private String id;
        private String name;
        private String code;

    }

}
