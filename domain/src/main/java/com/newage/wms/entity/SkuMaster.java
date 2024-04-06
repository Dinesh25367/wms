package com.newage.wms.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="wms_sku_master",schema = "tenant_default")
public class SkuMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @ManyToOne
    @JoinColumn(name = "group_company_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"),nullable = false)
    private GroupCompanyMaster groupCompanyMaster;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_COMP_ID"),nullable = false)
    private CompanyMaster companyMaster;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_BRANCH_ID"),nullable = false)
    private BranchMaster branchMaster;

    @Column(name = "code",nullable = false)
    private String code;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "ean_upc")
    private String eanUpc;

    @ManyToOne
    @JoinColumn(name = "customer_id",foreignKey = @ForeignKey(name = "FK_CUSTOMER_ID"),nullable = false)
    private CustomerMaster customerMaster;

    @Column(name = "sku_group")
    private String skuGroup;

    @Column(name = "sku_sub_group")
    private String skuSubGroup;

    @ManyToOne
    @JoinColumn(name = "storage_area_id", foreignKey = @ForeignKey(name = "FK_STORAGE_AREA_ID"),nullable = false)
    private StorageAreaMaster storageAreaMaster;

    @ManyToOne
    @JoinColumn(name = "storage_type_id", foreignKey = @ForeignKey(name = "FK_STORAGE_TYPE_ID"),nullable = false)
    private StorageTypeMaster storageTypeMaster;

    @Column(name = "rotation_by",nullable = false)
    private String rotationBy;

    @Column(name = "rotation_method",nullable = false)
    private String rotationMethod;

    @ManyToOne
    @JoinColumn(name = "base_unit_of_measure", foreignKey = @ForeignKey(name = "FK_UOM_ID"),nullable = false)
    private UomMaster baseUnitOfMeasurement;

    @Column(name = "break_pack_for_pick",nullable = false)
    private Character breakPackForPick;

    @ManyToOne
    @JoinColumn(name = "hs_code_id", foreignKey = @ForeignKey(name = "FK_HS_CODE_ID"))
    private HsCodeMaster hsCodeMaster;

    @ManyToOne
    @JoinColumn(name = "imco_code_id", foreignKey = @ForeignKey(name = "FK_IMCO_CODE_ID"))
    private ImcoCodeMaster imcoCodeMaster;

    @ManyToOne
    @JoinColumn(name = "purchase_currency_id", foreignKey = @ForeignKey(name = "FK_CURRENCY_ID"))
    private CurrencyMaster currencyMaster;

    @Column(name = "purchasePrice")
    private String purchasePrice;

    @Column(name = "lovStatus",nullable = false)
    private String lovStatus;

    @Column(name = "note")
    private String note;

    @OneToMany(targetEntity = SkuPackDetail.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_SKU_ID"))
    private List<SkuPackDetail> skuPackDetailList;

    @OneToMany(targetEntity = SkuLotDetails.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_SKU_ID"))
    private List<SkuLotDetails> skuLotDetailsList;

    @Column(name = "brandCode")
    private String brandCode;

    @Column(name = "plantCode")
    private String plantCode;

    @Column(name = "batchItem")
    private String batchItem;

    @Column(name = "serialItem")
    private String serialItem;

    @Column(name = "batch")
    private String batch;

    @Column(name = "serialNo")
    private String serialNo;

    @Column(name = "mfgDate")
    private String mfgDate;

    @Column(name = "expDate")
    private String expDate;

    @Column(name = "coo")
    private String coo;

    @Column(name = "boxId")
    private String boxId;

    @Column(name = "lpnId")
    private String lpnId;

    @Column(name = "sku_image")
    private byte[] skuImageByte;

}

