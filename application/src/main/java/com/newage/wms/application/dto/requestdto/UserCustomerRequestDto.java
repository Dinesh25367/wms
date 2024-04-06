package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
@Setter
@Component
public class UserCustomerRequestDto {
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

    @Valid
    @NotNull
    private CustomerDTO customer;

    @Valid
    @NotNull
    private UserNameDTO userName;


    @Pattern(regexp = "^(Yes|No)$")
    private String isPrimary;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^(Active|Inactive)$")
    private String status;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CustomerDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String name;
        private String code;
        private String shortName;
        private String customerStatus;
        private String customerAccessStatus;
        private String businessRelationStatus;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class UserNameDTO{
        private String id;
        private String userName;
        private String primaryRoleId;
    }
}
