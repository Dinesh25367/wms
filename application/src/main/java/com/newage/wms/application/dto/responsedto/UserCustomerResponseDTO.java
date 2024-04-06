package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Getter
@Setter
@Component
public class UserCustomerResponseDTO extends DefaultFieldsResponseDTO {

    private Long id;
    private CustomerDTO customer  ;
    private String isPrimary;
    private String status;
    private UserNameDTO Username;
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