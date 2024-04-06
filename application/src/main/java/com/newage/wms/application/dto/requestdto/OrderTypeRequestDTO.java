package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTypeRequestDTO {
    @Size(max = 30)
    private String user;

    @NotNull
    @Size(max = 30)
    private String code;

    @NotNull
    @Pattern(regexp = "^(Active|Inactive)$")
    private String status;

    @NotNull
    @Size(max = 100)
    private String description;

    @Size(max = 30)
    private String warehouseInOut;


    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String groupCompanyId;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String companyId;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String branchId;
}
