package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfigurationRequestDTO {

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

    @Size(max = 30)
    private String screen;

    @Size(max = 30)
    private String tab;

    @NotNull
    @Size(max = 50)
    private String configurationFlag;


    @Size(max = 50)
    private String description;

    @NotNull
    @Size(max = 5000)
    private String value;

    @NotNull
    private String dataType;

    @Size(max = 500)
    private String note;

    @Size(max = 30)
    private String status;

}

