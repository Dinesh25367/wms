package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
public class SkuRequestDTO {

    @NotNull
    @Size(max = 30)
    private String user;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String userId;

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
    private SkuDetailsDTO skuDetails;

    @Valid
    @NotNull
    private PackDetailsDTO packDetails;

    @Valid
    private MoreDetailsDTO skuMoreDetails;

    @Valid
    private LotDetailsDTO skuLotDetails;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class SkuDetailsDTO{

        @NotNull
        @Size(max = 100)
        private String code;

        @NotNull
        @Size(max = 300)
        private String name;

        @Size(max = 100)
        private String eanUpc;

        @Valid
        @NotNull
        private CustomerDTO customer;

        @Size(max = 100)
        private String skuGroup;

        @Size(max = 100)
        private String skuSubGroup;

        @Valid
        @NotNull
        private StorageAreaDTO storageArea;

        @Valid
        @NotNull
        private StorageTypeDTO storageType;

        @NotNull
        @Size(max = 30)
        private String rotationBy;

        @NotNull
        @Size(max = 30)
        private String rotationMethod;

        @NotNull
        @Valid
        private UomDTO baseUnitOfMeasure;

        @NotNull
        @Pattern(regexp = "^(Yes|No)$")
        private String breakPackForPick;

        @Valid
        private HsCodeDTO hsCode;

        @Valid
        private ImcoCodeDTO imcoCode;

        @Valid
        private CurrencyDTO currency;

        private String purchasePrice;

        @NotNull
        private String lovStatus;

        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class PackDetailsDTO{

        @Valid
        @NotNull
        private List<SkuPackDetailDTO> skuPackDetailDTOList;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class MoreDetailsDTO{

        private String brandCode;

        private String plantCode;

        private String batchItem;

        private String serialItem;

        private String batch;

        private String serialNo;

        private String mfgDate;

        private String expDate;

        private String coo;

        private String boxId;

        private String lpnId;

        private String skuImage;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class LotDetailsDTO{

        @Valid
        private LotDTO lot01;
        @Valid
        private LotDTO lot02;
        @Valid
        private LotDTO lot03;
        @Valid
        private LotDTO lot04;
        @Valid
        private LotDTO lot05;
        @Valid
        private LotDTO lot06;
        @Valid
        private LotDTO lot07;
        @Valid
        private LotDTO lot08;
        @Valid
        private LotDTO lot09;
        @Valid
        private LotDTO lot10;
        @Valid
        private List<LotDTO> lotArray;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class LotDTO{

        @Pattern(regexp = "^[0-9]*$")
        private String id;
        private String name;
        private String label;
        private String isMandatory;

    }



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
    public static class StorageAreaDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class StorageTypeDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class UomDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String status;
        private String note;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class HsCodeDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class ImcoCodeDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CurrencyDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String id;

        private String code;
        private String name;
        private String symbol;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class SkuPackDetailDTO {

        @Pattern(regexp = "^[0-9]*$")
        private String id;

        private Long version;

        private String createdUser;

        private Date createdDate;

        private String updatedUser;

        private Date updatedDate;

        @NotNull
        @Size(max = 30)
        private String uomType;

        @NotNull
        @Valid
        private SkuRequestDTO.SkuPackDetailDTO.UomDTO uom;

        @NotNull
        @Pattern(regexp = "^[0-9.]+$")
        private String ratio;

        @NotNull
        private String unit;

        @NotNull
        @Pattern(regexp = "^[0-9.]+$")
        private String length;

        @NotNull
        @Pattern(regexp = "^[0-9.]+$")
        private String width;

        @NotNull
        @Pattern(regexp = "^[0-9.]+$")
        private String height;

        @NotNull
        @Pattern(regexp = "^[0-9.]+$")
        private String nw;

        @NotNull
        @Pattern(regexp = "^[0-9.]+$")
        private String gw;

        @NotNull
        @Pattern(regexp = "^[0-9.]+$")
        private String vol;

        @NotNull
        @Size(max = 5)
        @Pattern(regexp = "^(Yes|No)$")
        private String actual;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class UomDTO {

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

            private String code;
            private String name;
            private String status;
            private String reference;
            private Long ratio;
            private Long rounding;
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

}