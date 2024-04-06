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
public class GrnHeaderResponseDTO extends DefaultFieldsResponseDTO{


    private Long id;
    private String isGrnCancellable;
    private String isGrnViewable;
    private String isEditable;
    private TrnHeaderAsnDTO trnHeaderAsn;
    private CustomerAddressResponseDTO customerAddress;
    private List<TrnResponseDTO.PartyCustomerDTO> party;
    private GrnHeaderDTO grnHeader;
    private List<TrnDetailDTO> asnDetailsList;
    private TrnResponseDTO.TrnHeaderFreightShippingDTO trnHeaderFreightShipping;

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

        private Long id;
        private TrnHeaderAsnDTO.TransactionDTO transaction;
        private TrnHeaderAsnDTO.WareHouseDTO warehouse;
        private TrnHeaderAsnDTO.CustomerDTO customer;
        private TrnHeaderAsnDTO.CustomerDTO supplier;
        private String from;
        private String customerOrderNo;
        private Date orderDate;
        private String customerInvoiceNo;
        private Date invoiceDate;
        private Date createdDate;
        private String transactionType;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class WareHouseDTO{

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

            private String id;
            private String transactionUid;

        }

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnDetailDTO {

        private TrnResponseDTO.TrnDetailDTO.DetailsFormDTO detailsForm;

        private TrnDetailDTO.MoreLotFormDTO moreLotForm;

        private List<TrnDetailDTO.GrnDetailDTO> grnDetailDTOList;

        private List<TrnResponseDTO.TrnDetailDTO.LotDTO> moreLotList;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class DetailsFormDTO {

            private String id;
            private TrnDetailDTO.DetailsFormDTO.SkuDTO sku;
            private String name;
            private TrnDetailDTO.DetailsFormDTO.UomDTO uom;
            private Double expQty;
            private TrnDetailDTO.DetailsFormDTO.UomDTO ruom;
            private Double rqty;
            private String boxId;
            private TrnDetailDTO.DetailsFormDTO.HsCodeDTO hsCode;
            private TrnDetailDTO.DetailsFormDTO.CurrencyDTO currency;
            private String rate;
            private Double actualQty;
            private Double actualRQty;
            private String lpnId;
            private Double volume;
            private Double grossWeight;
            private Double netWeight;
            private String documentLineTotalAmount;
            private String actualLineTotalAmount;
            private String batch;
            private String coo;
            private String serialNumber;
            private Date expDate;
            private Date mfgDate;

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class SkuDTO{

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

                private String id;
                private String code;
                private String name;

            }

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class UomDTO{

                private String id;
                private String code;
                private String status;
                private Double ratio;
                private String reference;
                private Long decimalPlaces;

            }

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class MoreLotFormDTO {

            private Long grnLotId;
            private String lot01;
            private String lot02;
            private String lot03;
            private String lot04;
            private String lot05;
            private String lot06;
            private String lot07;
            private String lot08;
            private String lot09;
            private String lot10;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class GrnDetailDTO {

            private Long grnDetailId;
            private TrnDetailDTO.DetailsFormDTO.UomDTO uom;
            private Double receivingQty;
            private Double volume;
            private Double weight;
            private String cartonId;
            private String lpnId;
            private TrnDetailDTO.GrnDetailDTO.LocationDTO location;
            private TrnDetailDTO.DetailsFormDTO.HsCodeDTO hsCode;
            private String batch;
            private String coo;
            private String serialNumber;
            private Date expDate;
            private Date mfgDate;
            private String isEditable;
            private TrnDetailDTO.GrnDetailDTO.DimensionalDTO dimensionalDetails;
            private List<TrnResponseDTO.TrnDetailDTO.LotDTO> moreLotList;
            private TrnDetailDTO.GrnDetailDTO.InventoryStatusDTO inventoryStatus;
            private TrnDetailDTO.GrnDetailDTO.TrnHeaderTransportationDTO transportation;

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

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class LocationDTO{

                private String id;
                private String locationUid;

            }

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class InventoryStatusDTO{

                private String id;
                private String name;
                private String code;
                private String isSaleable;
            }

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class TrnHeaderTransportationDTO{

                private Long id;
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
        }
    }
}
