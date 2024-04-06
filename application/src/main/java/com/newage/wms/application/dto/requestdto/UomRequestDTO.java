package com.newage.wms.application.dto.requestdto;

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
@Component
@NoArgsConstructor
public class UomRequestDTO {

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

    @Size(max = 50)
    @NotNull
    private String description;

    @Size(max = 10)
    private String reference;

    @NotNull
    @Pattern(regexp = "^(Active|Inactive)$")
    private String status;

    private Double ratio;

    @Valid
    private CategoryDTO category;

    private Long decimalPlaces;

    @Size(max = 10)
    private String restrictionOfUom;


    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CategoryDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;
        private String status;

    }

}

