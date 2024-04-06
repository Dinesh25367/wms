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

public class ConfigurationFlagMasterRequestDTO {

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

    @Size(max = 30)
    private String screen;

    @NotNull
    @Size(max = 50)
    private String configurationFlag;


    @Size(max = 50)
    private String description;


    @Size(max = 30)
    private String status;


}
