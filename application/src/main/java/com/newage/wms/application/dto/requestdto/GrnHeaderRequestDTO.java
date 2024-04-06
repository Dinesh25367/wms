package com.newage.wms.application.dto.requestdto;

import com.newage.wms.application.dto.responsedto.SkuPackDetailResponseDTO;
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
public class GrnHeaderRequestDTO {

    @NotNull
    private String user;

    @NotNull
    @Pattern(regexp = "^\\d+$")
    private String groupCompanyId;

    @NotNull
    @Pattern(regexp = "^\\d+$")
    private String companyId;

    @NotNull
    @Pattern(regexp = "^\\d+$")
    private String branchId;

    @Valid
    @NotNull
    private TrnHeaderAsnDTO trnHeaderAsn;

    private GrnHeaderDTO grnHeader;

    @Valid
    @NotNull
    private List<TrnDetailDTO> asnDetailsList;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class GrnHeaderDTO {

        private String grnRef;
        private String status;
        private Date grnDate;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderAsnDTO {

        @Valid
        private TransactionDTO transaction;

        @Valid
        @NotNull
        private WareHouseDTO warehouse;

        @Valid
        @NotNull
        private CustomerDTO customer;

        @Valid
        private CustomerDTO supplier;

        @Size(max = 100)
        private String supplierName;

        @Size(max = 30)
        @NotNull
        private String from;

        @Size(max = 100)
        private String customerOrderNo;

        private Date orderDate;

        @Size(max = 100)
        private String customerInvoiceNo;

        private Date invoiceDate;

        private Date createdDate;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class WareHouseDTO{

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

            private String wareHouseId;
            private String code;
            private String name;
            private String status;
            private String wareHouseLocationPrefix;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class CustomerDTO{

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

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
        public static class TransactionDTO{

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

            private String transactionUid;
            private String customerOrderNo;
            private String customerInvoiceNo;

        }

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnDetailDTO {

        @Valid
        @NotNull
        private DetailsFormDTO detailsForm;

        @Valid
        private MoreLotFormDTO moreLotForm;

        @Valid
        private List<GrnDetailDTO> grnDetailDTOList;

        @Valid
        private List<LotDTO> moreLotList;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class LotDTO{

            private String name;
            private String label;
            private String isMandatory;
            private String value;
            private Date date;
            private Long id;
            private String code;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class DetailsFormDTO {

            @Pattern(regexp = "^[0-9]*$")
            private String id;

            @Valid
            private SkuDTO sku;

            @Size(max = 50)
            private String name;

            @Valid
            @NotNull
            private UomDTO uom;

            private Double expQty;

            @Valid
            private UomDTO ruom;

            private Double rqty;

            @Size(max = 30)
            private String boxId;

            @Valid
            private HsCodeDTO hsCode;

            @Valid
            private CurrencyDTO currency;

            @Pattern(regexp = "^-?\\d+(\\.\\d+)?$")
            private String rate;

            private Double actualQty;

            private Double actualRQty;

            @Size(max = 30)
            private String lpnId;

            private Double volume;

            private Double grossWeight;

            private Double netWeight;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String documentLineTotalAmount;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String actualLineTotalAmount;

            @Size(max = 100)
            private String batch;

            @Size(max = 100)
            private String coo;

            @Size(max = 100)
            private String serialNumber;

            private Date expDate;

            private Date mfgDate;

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class SkuDTO{

                @NotNull
                @Pattern(regexp = "^[0-9]+$")
                private String id;

                private String code;
                private String name;
                private SkuPackDetailResponseDTO packDetails;
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
                private String status;

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
            public static class UomDTO{

                @NotNull
                @Pattern(regexp = "^[0-9]+$")
                private String id;

                private String code;
                private String name;
                private String status;
                private Double ratio;
                private String reference;
                private Long decimalPlaces;
                private String note;

            }

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class MoreLotFormDTO {

            @Pattern(regexp = "^[0-9]*$")
            private String grnLotId;

            @Size(max = 100)
            private String lot01;

            @Size(max = 100)
            private String lot02;

            @Size(max = 100)
            private String lot03;

            @Size(max = 100)
            private String lot04;

            @Size(max = 100)
            private String lot05;

            @Size(max = 100)
            private String lot06;

            @Size(max = 100)
            private String lot07;

            @Size(max = 100)
            private String lot08;

            @Size(max = 100)
            private String lot09;

            @Size(max = 100)
            private String lot10;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class GrnDetailDTO {

            @Pattern(regexp = "^[0-9]*$")
            private String grnDetailId;

            @Valid
            @NotNull
            private DetailsFormDTO.UomDTO uom;

            private Double receivingQty;

            private Double volume;

            private Double weight;

            @Size(max = 30)
            private String cartonId;

            @Size(max = 30)
            private String lpnId;

            @Valid
            private LocationDTO location;

            @Valid
            private DimensionalDTO dimensionalDetails;

            @Valid
            private InventoryStatusDTO inventoryStatus;

            @Valid
            private TrnHeaderTransportationDTO transportation;

            @Valid
            private GrnHeaderRequestDTO.TrnDetailDTO.DetailsFormDTO.HsCodeDTO hsCode;

            @Size(max = 100)
            private String batch;

            @Size(max = 100)
            private String coo;

            @Size(max = 100)
            private String serialNumber;

            private Date expDate;

            private Date mfgDate;

            @Valid
            private List<LotDTO> moreLotList;

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class LocationDTO{

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
            public static class InventoryStatusDTO{

                @NotNull
                @Pattern(regexp = "^[0-9]+$")
                private String id;
                private String code;
                private String name;
                private String isSaleable;

            }

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class TrnHeaderTransportationDTO{

                @Pattern(regexp = "^[0-9]*$")
                private String id;

                @Size(max = 15)
                @Pattern(regexp = "^(Yes|No)$")
                private String isOurTransport;

                private String vehicleType;

                private String vehicleNumber;

                private String containerType;

                private String containerNumber;

                private String seal;

                private String driver;

                private String driverMobile;

                private String driverId;

                private String gatePassNumber;

            }


            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class DimensionalDTO {
                private String length;
                private String width;
                private String height;
                private String unit;
                private Boolean isChecked;
            }



        }

    }

}