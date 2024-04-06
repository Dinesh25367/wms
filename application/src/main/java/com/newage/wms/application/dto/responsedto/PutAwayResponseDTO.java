package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class PutAwayResponseDTO extends DefaultFieldsResponseDTO{

    private Long id;
    private PutAwayTaskHeaderDTO putAwayTaskHeader;
    private List<PutAwayTaskDetailDTO> putAwayTaskDetailList;
    private List<PutAwayTaskDetailDTO.PutAwayPopupDetails> completedPutAwayPopupDetailsList;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class PutAwayTaskHeaderDTO{

        private Long grnId;
        private String taskId;
        private String taskStatus;
        private String note;
        private String createdBy;
        private Date createdOn;
        private Date taskStarted;
        private Date taskCompleted;
    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class PutAwayTaskDetailDTO {

        private SkuFormDTO skuForm;
        private List<PutAwayPopupDetails> putawayPopupDetailsList;

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class PutAwayPopupDetails {

            @Valid
            private Long id;

            @Valid
            private SkuDTO sku;

            @Valid
            @NotNull
            private UomDTO uom;

            @Valid
            private UomDTO ruom;

            private Double rUomQty;
            private Double uomQty;
            private Integer taskSlNo;

            @Valid
            private LocationDTO toLoc;
            @Valid
            private LocationDTO sugLoc;
            private String eanupc;
            private Double qty;
            private String cartonId;
            private String lpnId;
            private Double volume;
            private Double weight;
            private Double putAwayQty;
            private String status;
            private String isEditable;
            private Long grnDetailId;
            private Double toPutAwayQty;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class SkuFormDTO{

            private Long grnDetailId;
            private SkuDTO sku;
            private String eanupc;
            private UomDTO uom;
            private Double uomQty;
            private UomDTO rUom;
            private Double ruomQty;
            private String lpnId;
            private String cartonId;
            private LocationDTO sugLoc;
            private LocationDTO toLoc;
            private GrnDetailDTO grnDetail;
            private List<LotFormDTO> lot;
            private List<TrnResponseDTO.TrnDetailDTO.LotDTO> moreLotList;
            private Double volume;
            private Double weight;
            private Double putAwayQty;
            private String status;
            private String isDisablePopup;
            private Integer transactionSlNo;
            private Double toPutAwayQty;
            private String isEditable;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class GrnDetailDTO {

            private Long grnDetailId;
            private UomDTO uom;
            private Double receivingQty;
            private Double volume;
            private Double weight;
            private String cartonId;
            private String lpnId;
            private LocationDTO location;
            private HsCodeDTO hsCode;
            private String batch;
            private String coo;
            private String serialNumber;
            private Date expDate;
            private Date mfgDate;
            private DimensionalDTO dimensionalDetails;
            private InventoryStatusDTO inventoryStatus;
            private TrnHeaderTransportationDTO transportation;


            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class UomDTO {

                private String id;
                private String code;
                private String status;
                private Double ratio;
                private String reference;
                private Long decimalPlaces;

            }

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class InventoryStatusDTO {

                private String id;
                private String name;
                private String code;
                private String isSaleable;
            }

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class TrnHeaderTransportationDTO {

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

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class HsCodeDTO {

                private String id;
                private String code;
                private String name;

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

            @Getter
            @Setter
            @Component
            @NoArgsConstructor
            public static class LocationDTO {

                private String id;
                private String locationUid;

            }
        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class SkuDTO {

            private String id;
            private String code;
            private String name;
            private SkuPackDetailResponseDTO packDetails;
        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class UomDTO {

            private String id;
            private String code;
            private String status;
            private Double ratio;
            private String reference;
            private Long decimalPlaces;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class LocationDTO {

            private String id;
            private String locationUid;

        }

        @Getter
        @Setter
        @Component
        @NoArgsConstructor
        public static class LotFormDTO {

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

    }

}