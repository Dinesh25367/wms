package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UomSkuPackDTO {

    private Long id;
    private Long skuPackId;
    private String uomType;
    private String code;
    private String status;
    private String reference;
    private String restrictionOfUom;
    private Double ratio;
    private Long decimalPlaces;
    private SkuResponseDTO.SkuPackDetailDTO.UomDTO.CategoryDTO category;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CategoryDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

}

