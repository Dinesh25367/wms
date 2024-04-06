package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SkuPackDetailResponseDTO extends DefaultFieldsResponseDTO{

    private Long id;
    private String uomType;
    private UomDTO uom;
    private String ratio;
    private String unit;
    private String length;
    private String width;
    private String height;
    private String nw;
    private String gw;
    private String vol;
    private String actual;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class UomDTO{

        private Long id;
        private String code;
        private String status;
        private String reference;
        private Double ratio;
        private Long decimalPlaces;
        private CategoryDTO category;

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

}
