package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class MovementTypeResponseDTO extends DefaultFieldsResponseDTO {
    private Long id;
    private String status;
    private String code;
    private String description;
    private String warehouseInOut;


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
}
