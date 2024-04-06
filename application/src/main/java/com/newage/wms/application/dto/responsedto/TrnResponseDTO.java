package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class TrnResponseDTO {

    private String isCancellable;
    private String isHeaderEditable;
    private String allowGrn;
    private String isRowAddable;
    private TrnResponseDTO.TrnHeaderAsnDTO trnHeaderAsn;
    private TrnHeaderFreightDTO trnHeaderFreight;
    private List<PartyCustomerDTO> party;
    private List<TrnResponseDTO.TrnHeaderTransportationDTO> trnHeaderTransportationList;
    private TrnHeaderFreightShippingDTO trnHeaderFreightShipping;
    private TrnResponseDTO.TrnHeaderCustomsDocumentDTO trnHeaderCustomsDocument;
    private List<TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsList;
    private List<TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsList;
    private List<TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsNewFlexiFieldsList;
    private List<TrnDetailDTO> asnDetailsList;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderAsnDTO{

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
        private String isExpiryAllowed;
        private String isBlindReceipt;
        private String transactionStatus;
        private String status;
        private WareHouseDTO wareHouse;
        private CustomerDTO customer;
        private MovementTypeDTO movementType;

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
            private Character isBonded;

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

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderFreightDTO {

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private String isOurJob;
        private FreightRefShipmentDTO freightRefShipment;
        private String service;
        private String trade;
        private String chargeableWeight;
        private String chargeableVolume;
        private String chargeablePalletCount;
        private CurrencyDTO freightCurrency;
        private Double freightCurrencyRate;
        private Double freightCurrencyAmount;
        private String freightApportion;
        private CurrencyDTO insuranceCurrency;
        private Double insuranceCurrencyRate;
        private Double insuranceCurrencyAmount;
        private String insuranceApportion;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class FreightRefShipmentDTO{

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
    public static class PartyCustomerDTO{

        private String index;
        private Boolean isEditedName;
        private String shipperName;
        private CustomerDTO shipper;
        private String buildingNoName;
        private String streetName;
        private CityDTO city;
        private StateDTO state;
        private CountryDTO country;
        private String poBoxNo;
        private String mobile;
        private String phone;
        private String mobilePrefix;
        private String phonePrefix;
        private String emailId;

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
        public static class CityDTO{

            private String id;
            private String name;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class StateDTO{

            private String id;
            private String code;
            private String name;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class CountryDTO{

            private String id;
            private String name;
            private String phoneCode;

        }

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderTransportationDTO{

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private String isOurTransport;
        private String ourTransPort;
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
    public static class TrnHeaderFreightShippingDTO {

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private String houseDocRef;
        private Date houseDocDate;
        private String masterDocRef;
        private Date masterDocDate;
        private Date eta;
        private Date ata;
        private TrnHeaderFreightShippingDTO.CarrierDTO carrier;
        private TrnHeaderFreightShippingDTO.VesselDTO vessel;
        private TrnHeaderFreightShippingDTO.OriginDTO origin;
        private TrnHeaderFreightShippingDTO.TosDTO tos;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class CarrierDTO{

            private Long id;
            private String code;
            private String name;
            private String transportMode;
            private String scacCode;
            private String localCarrier;
            private String status;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class VesselDTO{

            private Long id;
            private String code;
            private String name;
            private String status;
            private String callSign;
            private String imoNumber;
            private String shortName;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class OriginDTO{

            private Long id;
            private String code;
            private String name;
            private String status;
            private String transportMode;
            private String countryCode;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class TosDTO{

            private Long id;
            private String code;
            private String name;
            private String status;
            private String ranking;
            private String freightPPCC;

        }

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderCustomsDocumentDTO {

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private String documentType;
        private String docRef1;
        private String docRef2;
        private Date docDate;
        private CustomerDTO ior;
        private CustomerDTO docPassedCompany;
        private String docPassedPerson;
        private String documentValue;
        private String accepted_tolerance;
        private String ftaReferenceNo;

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

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderCustomsAddlDetailsDTO{

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private String textbox;
        private Date date;
        private Double number;
        private Long configurationId;
        private String module;
        private String screen;
        private String tab;
        private String configurationFlag;
        private String description;
        private String value;
        private String dataType;
        private String note;
        private String status;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderAddlDetailsDTO{

        private Long id;
        private Long version;
        private String createdUser;
        private Date createdDate;
        private String updatedUser;
        private Date updatedDate;
        private String character;
        private String freetext;
        private Date date;
        private Double number;
        private Long configurationId;
        private String module;
        private String configurationFlag;
        private String value;
        private String label;
        private String dataType;
        private String note;
        private String isMandatory;


    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnDetailDTO {

        private String isDetailEditable;
        private String isDetailDeletable;
        private DetailsFormDTO detailsForm;
        private MoreLotFormDTO moreLotForm;
        private MoreQCFormDTO moreQCForm;
        private MoreFtaFormDTO moreFtaForm;
        private MoreSoFormDTO moreSoForm;
        private List<LotDTO> moreLotList;
        private List<LotDTO> moreLotListForAsn;

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
            private Date date;
            private String code;
            private String isStaticLot;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class DetailsFormDTO {

            private Long id;
            private Integer transactionSlNo;
            private SkuDTO sku;
            private String name;
            private UomDTO uom;
            private Double expQty;
            private Double qtyAlreadyReceived;
            private Double expQtyWhileReceiving;
            private Double remainingExpQty;
            private UomDTO ruom;
            private String rqty;
            private String boxId;
            private HsCodeDTO hsCode;
            private CurrencyDTO currency;
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
            private String isBackOrder;
            private Date expDate;
            private Date mfgDate;
            private String serialNumber;
            private Double receiveqty=0.0;
            private Double damagedQty=0.0;
            private Double excess=0.0;
            private Double shortage=0.0;
            private String isQuantityZero;


            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class SkuDTO{

                private Long id;
                private String code;
                private String name;
                private SkuPackDetailResponseDTO packDetails;
                private SkuResponseDTO.MoreDetailsDTO skuMoreDetails;

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
            private QcStatusDTO inspectionStatus;
            private AuthUserProfileDTO inspectedBy;
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

            private String ftaValue;
            private String casePcb;
            private String outerCartonPcb;
            private String packetPcb;
            private String pcsQty;

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

