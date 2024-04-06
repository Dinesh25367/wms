package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class TrnSoResponseDTO {

    private TrnHeaderSoDTO trnHeaderSo;
    private TrnHeaderFreightSoDTO trnHeaderSoFreight;
    private List<TrnResponseDTO.PartyCustomerDTO> party;
    private List<TrnResponseDTO.TrnHeaderTransportationDTO> trnHeaderTransportationList;
    private TrnResponseDTO.TrnHeaderFreightShippingDTO trnHeaderFreightShipping;
    private TrnResponseDTO.TrnHeaderCustomsDocumentDTO trnHeaderCustomsDocument;
    private List<TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsList;
    private List<TrnResponseDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsList;
    private List<TrnResponseDTO.TrnDetailDTO> soDetailsList;
    private String isBackOrder;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderSoDTO{

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private GroupCompanyDTO groupCompany;
        private CompanyDTO company;
        private BranchDTO branch;
        private String transactionUid;
        private String transactionType;
        private String customerOrderNo;
        private Date orderDate;
        private String customerInvoiceNo;
        private Date invoiceDate;
        private String isEdiTransaction;
        private Date expectedReceivingDate;
        private String note;
        private String isCrossDock;
        private String isConfirmationEdi;
        private String priority;
        private String transactionStatus;
        private String status;
        private WareHouseDTO wareHouse;
        private CustomerDTO customer;
        private MovementTypeDTO movementType;
        private OrderTypeDTO orderType;
        private TransactionDTO transaction;

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
        public static class WareHouseDTO{

            private Long id;
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
        public static class MovementTypeDTO{

            private Long id;
            private String code;
            private String status;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class OrderTypeDTO{

            private Long id;
            private String code;
            private String status;

        }

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderFreightSoDTO {

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private String isOurJob;
        private BookingRefShipmentDTO bookingRefShipment;
        private String service;
        private String trade;
        private String chargeableWeight;
        private String chargeableVolume;
        private String chargeablePalletCount;
        private CurrencyDTO orderCurrency;
        private Double orderCurrencyRate;
        private Double orderCurrencyAmount;
        private String selfLife;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class BookingRefShipmentDTO{

            private Long id;
            private String shipmentUid;
            private String shipmentStatus;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class ServiceDTO{

            private Long id;
            private String code;
            private String name;
            private String status;
            private String transportMode;
            private String importExport;
            private String primaryService;
            private String clearance;
            private String fullGroupage;

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

    }



    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnDetailDTO {

        private TrnResponseDTO.TrnDetailDTO.DetailsFormDTO detailsForm;
        private TrnResponseDTO.TrnDetailDTO.MoreLotFormDTO moreLotForm;
        private TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO moreQCForm;
        private TrnResponseDTO.TrnDetailDTO.MoreFtaFormDTO moreSoForm;
        private List<SkuResponseDTO.LotDTO> moreLotList;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class DetailsFormDTO {

            private Long id;
            private Integer transactionSlNo;
            private TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO sku;
            private String name;
            private TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO uom;
            private String expQty;
            private TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO ruom;
            private String rqty;
            private String boxId;
            private TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.HsCodeDTO hsCode;
            private TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.CurrencyDTO currency;
            private String rate;
            private String actualQty;
            private String actualRQty;
            private String lpnId;
            private String volume;
            private String grossWeight;
            private String netWeight;
            private String documentLineTotalAmount;
            private String actualLineTotalAmount;
            private String batch;
            private String coo;
            private Date expDate;
            private Date mfgDate;
            private String serialNumber;

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class SkuDTO{

                private Long id;
                private String code;
                private String name;
                private SkuPackDetailResponseDTO packDetails;

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
            public static class HsCodeDTO{

                private Long id;
                private String code;
                private String name;

            }

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class UomDTO{

                private Long id;
                private Long skuPackId;
                private String uomType;
                private String code;
                private String status;
                private String reference;
                private String restrictionOfUom;
                private Double ratio;
                private Long decimalPlaces;
                private TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO category;

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

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class MoreLotFormDTO {

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
        public static class MoreQCFormDTO {

            private String qcRequired;
            private String failedQty;
            private String inspectPackQty;
            private String inspectPieceQty;
            private String inspectedPackQty;
            private String inspectedPieceQty;
            private TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO inspectionStatus;
            private TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO inspectedBy;
            private String qcNote;
            private String qcResult;

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class QcStatusDTO{

                private Long id;
                private String code;
                private String name;

            }

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class AuthUserProfileDTO{

                private Long id;
                private String userName;
                private String status;
                private Long primaryRoleId;

            }

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class MoreFtaFormDTO {

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