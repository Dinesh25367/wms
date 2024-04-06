package com.newage.wms.application.dto.requestdto;

import com.newage.wms.application.dto.responsedto.SkuPackDetailResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@Component
public class PutAwayRequestDTO {
    @NotNull
    private String user;

    @NotNull
    @Pattern(regexp = "^\\d+$")
    private Long groupCompanyId;

    @NotNull
    @Pattern(regexp = "^\\d+$")
    private Long companyId;

    @NotNull
    @Pattern(regexp = "^\\d+$")
    private Long branchId;


    @Valid
    @NotNull
    private PutAwayTaskHeaderDTO putAwayTaskHeader;

    @Valid
    @NotNull
    private List<PutAwayTaskDetailDTO> putAwayTaskDetailList;

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class PutAwayTaskHeaderDTO{

        private Long grnId;
        private String taskId;
        private String status;
        private String note;
    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class PutAwayTaskDetailDTO{
        private PutAwayFormDTO putAwayForm;

        private List<PutAwayPopupDetails> putawayPopupDetailsList;
    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class PutAwayFormDTO {
        @Valid
        private Long grnDetailId;

        @Valid
        private SkuDTO sku;

        @Valid
        @NotNull
        private UomDTO uom;

        @Valid
        private UomDTO ruom;

        private Double rUomQty;
        private Double uomQty;

        @Valid
        private LocationDTO toLoc;
        @Valid
        private LocationDTO sugLoc;
        private String eanupc;
        private Double qty;
        private String cartonId;
        private String lpnId;
        @NotNull
        private Double volume;
        @NotNull
        private Double weight;
        private Double putAwayQty;
        private String status;
        private String isEditable;
        private Integer transactionSlNo;
    }
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

        @Valid
        private LocationDTO toLoc;
        @Valid
        private LocationDTO sugLoc;
        private String eanupc;
        private Double qty;
        private String cartonId;
        private String lpnId;
        @NotNull
        private Double volume;
        @NotNull
        private Double weight;
        private Double putAwayQty;
        private String status;
    }


    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class SkuDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private Long id;
        private String code;
        private String name;
        private SkuPackDetailResponseDTO packDetails;
    }

    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class UomDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private Long id;
        private String code;
        private String name;
        private String status;
        private Double ratio;
        private String reference;
        private Long decimalPlaces;
        private String note;

    }
    @Getter
    @Setter
    @Component
    @NoArgsConstructor
    public static class LocationDTO{

        @NotNull
        @Pattern(regexp = "^[0-9]+$")
        private Long id;
        private String code;
        private String locationUid;

    }

}
