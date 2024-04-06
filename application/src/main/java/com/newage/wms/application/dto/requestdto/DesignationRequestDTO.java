package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DesignationRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotNull
    private String status;

    private Long departmentId;
    private String note;

    @NotNull
    private String type;

}
