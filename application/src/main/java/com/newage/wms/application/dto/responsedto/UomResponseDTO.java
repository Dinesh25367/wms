package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.Date;

@Getter
@Setter
@Component
public class UomResponseDTO {


    private Long id;
    private Long version;
    private String createdUser;
    private Date createdDate;
    private String updatedUser;
    private Date updatedDate;
    private GroupCompanyDTO groupCompany;
    private CompanyDTO company;
    private BranchDTO branch;
    private String code;
    private String description;
    private String reference;
    private String status;
    private Double ratio;
    private CategoryResponseDTO category;
    private Long decimalPlaces;
    private String restrictionOfUom;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class BranchDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CompanyDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class GroupCompanyDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CategoryResponseDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

}
