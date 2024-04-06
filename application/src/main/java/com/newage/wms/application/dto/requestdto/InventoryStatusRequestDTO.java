package com.newage.wms.application.dto.requestdto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStatusRequestDTO {

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
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 30)
    private String code;

    @NotNull
    @Pattern(regexp = "^(Yes|No)$")
    private String isSaleable;

    private String Description;

    private String note;
}
