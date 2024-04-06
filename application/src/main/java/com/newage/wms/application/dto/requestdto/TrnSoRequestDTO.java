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
public class TrnSoRequestDTO {

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
    private TrnHeaderSoDTO trnHeaderSo;

    @Valid
    @NotNull
    private TrnHeaderFreightSoDTO trnHeaderSoFreight;

    @Valid
    @NotNull
    private List<TrnRequestDTO.PartyCustomerDTO> party;

    @Valid
    private List<TrnRequestDTO.TrnHeaderTransportationDTO> trnHeaderTransportationList;

    @Valid
    private TrnRequestDTO.TrnHeaderFreightShippingDTO trnHeaderFreightShipping;

    @Valid
    private TrnRequestDTO.TrnHeaderCustomsDocumentDTO trnHeaderCustomsDocument;

    @Valid
    private List<TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsList;

    @Valid
    private List<TrnRequestDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsList;

    @Valid
    private List<TrnDetailDTO> soDetailsList;


    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderSoDTO{

        @Valid
        @NotNull
        private WareHouseDTO wareHouse;

        @Valid
        @NotNull
        private CustomerDTO customer;

        @Size(max = 100)
        private String customerOrderNo;

        private Date orderDate;

        @Size(max = 100)
        private String customerInvoiceNo;

        private Date invoiceDate;

        private TransactionDTO transaction;

        @Valid
        @NotNull
        private MovementTypeDTO movementType;

        @Size(max = 15)
        @NotNull
        @Pattern(regexp = "^(Yes|No)$")
        private String isEdiTransaction;

        @NotNull
        private Date expectedReceivingDate;

        @Size(max = 1000)
        private String note;

        @Size(max = 15)
        @Pattern(regexp = "^(Yes|No|Direct)$")
        private String isCrossDock;

        @Size(max = 15)
        @Pattern(regexp = "^(Yes|No)$")
        private String isConfirmationEdi;

        @Size(max = 30)
        private String priority;

        @Size(max = 30)
        private String status;

        @Size(max = 30)
        private String orderType;

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
        public static class MovementTypeDTO{

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

            private String code;
            private String name;

        }

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderFreightSoDTO{

        @Pattern(regexp = "^(Yes|No)$")
        @Size(max = 15)
        @NotNull
        private String isOurJob;

        @Size(max = 17)
        @Pattern(regexp = "^[0-9.]*$")
        private String chargeableWeight;

        @Size(max = 12)
        @Pattern(regexp = "^[0-9.]*$")
        private String chargeableVolume;

        @Size(max = 7)
        @Pattern(regexp = "^[0-9.]*$")
        private String chargeablePalletCount;

        @Valid
        private FreightRefShipmentDTO bookingRefShipment;

        @NotNull
        @Pattern(regexp = "^(AIR|LCL|FCL)$")
        private String service;

        @NotNull
        @Pattern(regexp = "^(Import|Export)$")
        private String trade;

        private String selfLife;

        @Valid
        @NotNull
        private CurrencyDTO orderCurrency;

        @Size(max = 27)
        @Pattern(regexp = "^[0-9.]+$")
        @NotNull
        private String orderCurrencyRate;

        @Size(max = 30)
        @Pattern(regexp = "^[0-9]*$")
        private String orderCurrencyAmount;


        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class FreightRefShipmentDTO{

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

            private String shipmentUid;
            private String shipmentStatus;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class ServiceDTO{

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
            private String status;

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
        private MoreQCFormDTO moreQCForm;

        @Valid
        private MoreSoFormDTO moreSoForm;

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

        }
        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class DetailsFormDTO {

            @Pattern(regexp = "^[0-9]*$")
            private String id;

            @NotNull
            @Valid
            private DetailsFormDTO.SkuDTO sku;

            @NotNull
            @Size(max = 50)
            private String name;

            @NotNull
            @Valid
            private DetailsFormDTO.UomDTO uom;

            @NotNull
            private Double expQty;

            @Valid
            @NotNull
            private DetailsFormDTO.UomDTO ruom;

            @NotNull
            private Double rqty;

            @Size(max = 30)
            private String boxId;

            @Valid
            private DetailsFormDTO.HsCodeDTO hsCode;

            @Valid
            @NotNull
            private DetailsFormDTO.CurrencyDTO currency;

            @Pattern(regexp = "^-?\\d+(\\.\\d+)?$")
            @NotNull
            private String rate;

            private Double actualQty;

            private Double actualRQty;

            @Size(max = 30)
            private String lpnId;

            @NotNull
            private Double volume;

            @NotNull
            private Double grossWeight;

            @NotNull
            private Double netWeight;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String documentLineTotalAmount;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String actualLineTotalAmount;

            @Size(max = 10)
            private String isBackOrder;

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
                private String note;

            }

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class MoreLotFormDTO {

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
        public static class MoreQCFormDTO {

            @Size(max = 3)
            @Pattern(regexp = "^(Yes|No)$")
            private String qcRequired;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String failedQty;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String inspectPackQty;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String inspectPieceQty;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String inspectedPackQty;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String inspectedPieceQty;

            @Valid
            private MoreQCFormDTO.QcStatusDTO inspectionStatus;

            @Valid
            private MoreQCFormDTO.AuthUserProfileDTO inspectedBy;

            @Size(max = 4000)
            private String qcNote;

            @Size(max = 10)
            private String qcResult;

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class QcStatusDTO{

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
            public static class AuthUserProfileDTO{

                @NotNull
                @Pattern(regexp = "^[0-9]+$")
                private String id;

                private String userName;
                private String status;
                private Long primaryRoleId;

            }

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class MoreSoFormDTO {


            private String pickFromCustomDocRef1;


            private String pickFromCustomDocRef2;


            private String pickFromAsn;


            private String pickFromGrn;


            private String pickFromPo;


            private String pickFromLpn;


            private String pickFromLocation;


            private String saleLineTotalValue;

            @Size(max = 3)
            @Pattern(regexp = "^(Yes|No)$")
            private String isExpiredAllowed;

            @Size(max = 3)
            @Pattern(regexp = "^(Yes|No)$")
            private String isDamagedAllowed;

        }

    }

}
