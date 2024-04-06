package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class SkuResponseDTO {

    private Long id;
    private Long version;
    private String createdUser;
    private Date createdDate;
    private String updatedUser;
    private Date updatedDate;
    private String isEditable;
    private Double onHandQty;
    private SkuDetailsDTO skuDetails;
    private PackDetailsDTO packDetails;
    private MoreDetailsDTO skuMoreDetails;
    private LotDetailsDTO skuLotDetails;
    private List<LotDTO> skulotDetailsList;
    private List<LotDTO> skulotDetailsListForAsn;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class SkuDetailsDTO{

        private GroupCompanyDTO groupCompany;
        private CompanyDTO company;
        private BranchDTO branch;
        private String code;
        private String name;
        private String eanUpc;
        private CustomerDTO customer;
        private String skuGroup;
        private String skuSubGroup;
        private StorageAreaDTO storageArea;
        private StorageTypeDTO storageType;
        private String rotationBy;
        private String rotationMethod;
        private UomDTO baseUnitOfMeasure;
        private String breakPackForPick;
        private HsCodeDTO hsCode;
        private ImcoCodeDTO imcoCode;
        private CurrencyDTO currency;
        private String purchasePrice;
        private String lovStatus;
        private String note;
    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class PackDetailsDTO{

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

        private LotDTO lot01;
        private LotDTO lot02;
        private LotDTO lot03;
        private LotDTO lot04;
        private LotDTO lot05;
        private LotDTO lot06;
        private LotDTO lot07;
        private LotDTO lot08;
        private LotDTO lot09;
        private LotDTO lot10;
        private List<LotDTO> lotArray;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class LotDTO{

        private Long id;
        private String name;
        private String label;
        private String isMandatory;
        private String value;
        private String code;
        private Date date;
        private String isStaticLot;

    }

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
    public static class CustomerDTO{

        private Long id;
        private String code;
        private String name;
        private String shortName;
        private String searchKey;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class StorageAreaDTO{

        private Long id;
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

        private Long id;
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

        private Long id;
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

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class HsCodeDTO{

        private Long id;
        private String name;
        private String code;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class ImcoCodeDTO{

        private Long id;
        private String name;
        private String code;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class CurrencyDTO{

        private Long id;
        private String code;
        private String name;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class SkuPackDetailDTO {

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
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
        public static class UomDTO {

            private Long id;
            private String code;
            private String status;
            private String reference;
            private String restrictionOfUom;
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

}
