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
public class TrnRequestDTO {

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
    private TrnHeaderAsnDTO trnHeaderAsn;

    @Valid
    @NotNull
    private TrnHeaderFreightDTO trnHeaderFreight;

    @Valid
    @NotNull
    private List<PartyCustomerDTO> party;

    @Valid
    private List<TrnHeaderTransportationDTO> trnHeaderTransportationList;

    @Valid
    private TrnHeaderFreightShippingDTO trnHeaderFreightShipping;

    @Valid
    private TrnHeaderCustomsDocumentDTO trnHeaderCustomsDocument;

    @Valid
    private List<TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsList;

    @Valid
    private List<TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsList;

    @Valid
    private List<TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsNewFlexiFieldsList;

    @Valid
    private List<TrnDetailDTO> asnDetailsList;


    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderAsnDTO{

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

        private Date createdDate;

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

        @Size(max = 15)
        @Pattern(regexp = "^(Yes|No)$")
        private String isExpiryAllowed;

        @Size(max = 15)
        @Pattern(regexp = "^(Yes|No)$")
        private String isBlindReceipt;

        @Size(max = 30)
        private String status;



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
    public static class TrnHeaderFreightDTO{

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
        private FreightRefShipmentDTO freightRefShipment;

        @NotNull
        @Pattern(regexp = "^(AIR|LCL|FCL)$")
        private String service;

        @NotNull
        @Pattern(regexp = "^(Import|Export)$")
        private String trade;

        @Valid
        @NotNull
        private CurrencyDTO freightCurrency;

        @Size(max = 27)
        @Pattern(regexp = "^[0-9.]+$")
        @NotNull
        private String freightCurrencyRate;

        @Size(max = 30)
        @Pattern(regexp = "^[0-9]*$")
        private String freightCurrencyAmount;

        @Size(max = 30)
        private String freightApportion;

        @Valid
        @NotNull
        private CurrencyDTO insuranceCurrency;

        @Size(max = 27)
        @Pattern(regexp = "^[0-9.]+$")
        @NotNull
        private String insuranceCurrencyRate;

        @Size(max = 30)
        @Pattern(regexp = "^[0-9]*$")
        private String insuranceCurrencyAmount;

        @Size(max = 30)
        private String insuranceApportion;

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
    public static class PartyCustomerDTO{

        private String index;

        private String isEditedName;

        private String shipperName;

        @Valid
        private CustomerDTO shipper;

        @Size(max = 200)
        private String buildingNoName;

        @Size(max = 200)
        private String streetName;

        @Valid
        private CityDTO city;

        @Valid
        private StateDTO state;

        @Valid
        private CountryDTO country;

        @Size(max = 100)
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
        public static class CityDTO{

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

            private String name;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class StateDTO{

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
        public static class CountryDTO{

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
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

        @Pattern(regexp = "^[0-9]*$")
        private String id;

        @Size(max = 15)
        @Pattern(regexp = "^(Yes|No)$")
        private String isOurTransport;

        @Size(max = 15)
        @Pattern(regexp = "^(Yes|No)$")
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

        @Size(max = 30)
        private String houseDocRef;

        private Date houseDocDate;

        @Size(max = 30)
        private String masterDocRef;

        private Date masterDocDate;

        private Date eta;

        private Date ata;

        @Valid
        private CarrierDTO carrier;

        @Valid
        private VesselDTO vessel;

        @Valid
        private OriginDTO origin;

        @Valid
        private TosDTO tos;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class CarrierDTO{

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

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

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

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

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

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

            @NotNull
            @Pattern(regexp = "^[0-9]+$")
            private String id;

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

        @Size(max = 50)
        private String documentType;

        @Size(max = 100)
        private String docRef1;

        @Size(max = 100)
        private String docRef2;

        private Date docDate;

        @Valid
        private CustomerDTO ior;

        @Valid
        private CustomerDTO docPassedCompany;

        @Size(max = 100)
        private String docPassedPerson;

        @Size(max = 100)
        private String documentValue;

        @Size(max = 100)
        private String accepted_tolerance;

        @Size(max = 100)
        private String ftaReferenceNo;

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

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderCustomsAddlDetailsDTO {

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private String configurationId;

        @Pattern(regexp = "^[0-9]*$")
        private String id;

        @Size(max = 100)
        private String textbox;

        private Date date;

        private Double number;

    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class TrnHeaderAddlDetailsDTO {

        @Pattern(regexp = "^[0-9]*$")
        private String configurationId;

        @Pattern(regexp = "^[0-9]*$")
        private String id;

        @Size(max = 100)
        private String character;

        @Size(max = 100)
        private String freetext;

        private Date date;

        private Double number;

        private String isMandatory;

        private String label;

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
        private MoreFtaFormDTO moreFtaForm;

        @Valid
        private List<TrnSoRequestDTO.TrnDetailDTO.LotDTO> moreLotList;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class DetailsFormDTO {

            @Pattern(regexp = "^[0-9]*$")
            private String id;

            @NotNull
            @Valid
            private SkuDTO sku;

            @NotNull
            @Size(max = 50)
            private String name;

            @NotNull
            @Valid
            private UomDTO uom;

            @NotNull
            private Double expQty;

            @Valid
            @NotNull
            private UomDTO ruom;

            @NotNull
            private Double rqty;

            @Size(max = 30)
            private String boxId;

            @Valid
            private HsCodeDTO hsCode;

            @Valid
            @NotNull
            private CurrencyDTO currency;

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

            @Size(max = 10)
            private String isBackOrder;

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
            private QcStatusDTO inspectionStatus;

            @Valid
            private AuthUserProfileDTO inspectedBy;

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
        public static class MoreFtaFormDTO {

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String ftaValue;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String casePcb;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String outerCartonPcb;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String packetPcb;

            @Pattern(regexp = "^(?:-?\\d+(\\.\\d+)?|)$")
            private String pcsQty;

        }

    }

}

