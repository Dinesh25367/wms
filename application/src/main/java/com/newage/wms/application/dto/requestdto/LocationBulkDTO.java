package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Component
@NoArgsConstructor
public class LocationBulkDTO {

    private String user;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String groupCompanyId;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String companyId;

    @NotNull
    @Pattern(regexp = "^[0-9]+$")
    private String branchId;

    @NotNull
    private String wareHouseCode;

    @NotNull
    @Size(max = 30)
    private String locationUid;

    @Pattern(regexp = "^(Yes|No)$")
    @NotNull
    private String isBinLocation;

    private String masterLocation;

    private String zoneCode;

    private String aisleCode;

    private String rackCode;

    @Size(max = 30)
    private String columnCode;

    @Size(max = 30)
    private String levelCode;

    private Long levelOrder;

    @Size(max = 30)
    private String position;

    private String storageAreaCode;

    private String storageTypeCode;

    private String locTypeCode;

    private String locationHandlingUomCode;

    @Size(max = 30)
    private String dimensionUnit;

    private String length;

    private String width;

    private String height;

    private String weight;

    private String volume;

    @Size(max = 10)
    private String abc;

    @Pattern(regexp = "^(Yes|No)$")
    private String mixedSkuAllowed;

    @Pattern(regexp = "^(Yes|No)$")
    private String replenishmentAllowed;

    @Size(max = 10)
    private String checkDigit;

    @Size(max = 10)
    private String status;

    private Long locationPathSeq;

    @Size(max = 30)
    private String deepSeq;

    @Size(max = 4000)
    private String note;

}

