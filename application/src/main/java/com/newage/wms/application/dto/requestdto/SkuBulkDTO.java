package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Component
@NoArgsConstructor
public class SkuBulkDTO {

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
    private String code;

    @NotNull
    private String name;

    @NotNull
    private String customerCode;

    private String skuGroup;

    private String skuSubGroup;

    @NotNull
    private String storageAreaCode;

    @NotNull
    private String storageTypeCode;

    @NotNull
    private String rotationBy;

    @NotNull
    private String rotationMethod;

    @NotNull
    private String baseUnitOfMeasureCode;

    @NotNull
    @Pattern(regexp = "^(Yes|No)$")
    private String breakPackForPick;

    private String hsCodeCode;

    private String imcoCodeCode;

    private String currencyCode;

    private String purchasePrice;

    @NotNull
    private String lovStatus;

    private String note;

    @NotNull
    @Size(max = 30)
    private String uomType;

    @NotNull
    private String uomCode;

    @NotNull
    private String unit;

    @NotNull
    @Pattern(regexp = "^[0-9.]+$")
    private String length;

    @NotNull
    @Pattern(regexp = "^[0-9.]+$")
    private String width;

    @NotNull
    @Pattern(regexp = "^[0-9.]+$")
    private String height;

    @NotNull
    @Pattern(regexp = "^[0-9.]+$")
    private String nw;

    @NotNull
    @Pattern(regexp = "^[0-9.]+$")
    private String gw;

    @NotNull
    @Pattern(regexp = "^[0-9.]+$")
    private String vol;

    @NotNull
    @Size(max = 5)
    @Pattern(regexp = "^(Yes|No)$")
    private String actual;

    private String brandCode;

    private String plantCode;

    private String batchItem;

    private String serialItem;

    private String batch;

    private String serialNo;

    private String mfgDate;

    private String expDate;

    private String coo;

    private String boxId;

    private String lpnId;

}
