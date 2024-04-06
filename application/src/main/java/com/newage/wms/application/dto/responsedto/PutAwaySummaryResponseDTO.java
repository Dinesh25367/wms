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
public class PutAwaySummaryResponseDTO extends DefaultFieldsResponseDTO {
    private Long id;
    private CustomerDTO customer;
    private String orderNo;
    private String team;
    private String noOfSku;
    private String volume;
    private String noOfLpn;
    private String noOfCarton;
    private String status;
    private String createYesNo;


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

    public void checkForNullAndSetDefaults(){
        if (this.volume == null){
            this.volume = "";
        }
        if (this.noOfSku == null){
            this.noOfSku = "";
        }
        if (this.noOfLpn == null){
            this.noOfLpn = "";
        }
        if (this.noOfCarton == null){
            this.noOfCarton = "";
        }
        if (this.team == null){
            this.team = "";
        }
        if (this.orderNo == null){
            this.orderNo = "";
        }
    }

}
