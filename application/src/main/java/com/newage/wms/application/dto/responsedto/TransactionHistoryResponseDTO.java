package com.newage.wms.application.dto.responsedto;

import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Component
public class TransactionHistoryResponseDTO extends DefaultFieldsResponseDTO {
    private Long id;
    private String referenceId;
    private WareHouseDTO wareHouse;
    private String taskId;
    private Integer taskSlNo;
    private TrnHeaderAsnDTO trnHeaderAsnMaster;
    private String transactionType;
    private Integer transactionSlNo;
    private String transactionNo;
    private TrnHeaderAsnDTO sourceTrnHeaderAsnMaster;
    private String sourceTransactionType;
    private Integer sourceTransactionSlNo;
    private String sourceTransactionNo;
    private String transactionStatus;
    private CustomerDTO customer;
    private SkuDTO sku;
    private UomDTO uom;
    private String lpnId;
    private String cartonId;
    private String inOut;
    private LocationDTO location;
    private Double qty;
    private Double volume;
    private Double netWeight;
    private Double grossWeight;
    private InventoryStatusDTO invStatus;
    private String taskStatus;
    private String note;
    private Date actualDate;

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
    public static class TrnHeaderAsnDTO{

        @Valid
        @NotNull
        private TrnRequestDTO.TrnHeaderAsnDTO.WareHouseDTO wareHouse;

        @Valid
        @NotNull
        private TrnRequestDTO.TrnHeaderAsnDTO.CustomerDTO customer;

        @Size(max = 100)
        private String customerOrderNo;

        private Date orderDate;

        @Size(max = 100)
        private String customerInvoiceNo;

        private Date invoiceDate;

        @Valid
        @NotNull
        private TrnRequestDTO.TrnHeaderAsnDTO.MovementTypeDTO movementType;

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


}
