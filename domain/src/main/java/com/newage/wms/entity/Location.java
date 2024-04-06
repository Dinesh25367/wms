package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wms_location_master",schema = "tenant_default", uniqueConstraints = {
        @UniqueConstraint(name = "UK_LOCATION_UID_AND_WAREHOUSE_ID",columnNames = {"location_uid", "warehouse_id"})
})
public class Location extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Version
    @Column(name = "version",nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "warehouse_id", foreignKey = @ForeignKey(name = "FK_WAREHOUSE_ID"))
    private WareHouseMaster wareHouseMaster;

    @Column(name = "location_uid",length = 30,nullable = false)
    private String locationUid;

    @Column(name = "bin_location",nullable = false)
    private Character isBinLocation;

    @ManyToOne
    @JoinColumn(name = "master_location_id", foreignKey = @ForeignKey(name = "FK_MASTER_LOCATION_ID"))
    private Location masterLocationId;

    @ManyToOne
    @JoinColumn(name = "zone_id", foreignKey = @ForeignKey(name = "FK_ZONE_ID"))
    private ZoneMasterWMS zoneMaster;

    @ManyToOne
    @JoinColumn(name = "aisle_id", foreignKey = @ForeignKey(name = "FK_AISLE_ID"))
    private AisleMaster aisleMaster;

    @ManyToOne
    @JoinColumn(name = "rack_id", foreignKey = @ForeignKey(name = "FK_RACK_ID"))
    private RackMaster rackMaster;

    @Column(name = "column_code",length = 5)
    private String columnCode;

    @Column(name = "level_code",length = 5)
    private String levelCode;

    @Column(name = "level_order")
    private Long levelOrder;

    @Column(name = "position",length = 3)
    private String position;

    @ManyToOne
    @JoinColumn(name = "storage_area_id", foreignKey = @ForeignKey(name = "FK_STORAGE_AREA_ID"),nullable = false)
    private StorageAreaMaster storageAreaMaster;

    @ManyToOne
    @JoinColumn(name = "storage_type_id", foreignKey = @ForeignKey(name = "FK_STORAGE_TYPE_ID"),nullable = false)
    private StorageTypeMaster storageTypeMaster;

    @ManyToOne
    @JoinColumn(name = "loc_type_id", foreignKey = @ForeignKey(name = "FK_LOC_TYPE_ID"),nullable = false)
    private LocTypeMaster locTypeMaster;

    @ManyToOne
    @JoinColumn(name = "location_handling_uom_id", foreignKey = @ForeignKey(name = "FK_UOM_ID"))
    private UomMaster locationHandlingUomMaster;

    @Column(name = "dimension_unit",length = 30,nullable = false)
    private String dimensionUnit;

    @Column(name = "length",length = 10,nullable = false)
    private String length;

    @Column(name = "width",length = 10,nullable = false)
    private String width;

    @Column(name = "height",length = 10,nullable = false)
    private String height;

    @Column(name = "weight",length = 15,nullable = false)
    private String weight;

    @Column(name = "volume",length = 15,nullable = false)
    private String volume;

    @Column(name = "abc",length = 20,nullable = false)
    private String abc;

    @Column(name = "mixed_sku_allowed",nullable = false)
    private Character mixedSkuAllowed;

    @Column(name = "replenishment_allowed",nullable = false)
    private Character replenishmentAllowed;

    @Column(name = "check_digit",length = 10)
    private String checkDigit;

    @Column(name = "status",length = 10,nullable = false)
    private String status;

    @Column(name = "location_path_seq")
    private Long locationPathSeq;

    @Column(name = "deep_seq",length = 30)
    private String deepSeq;

    @Column(name = "note",length = 4000)
    private String note;

}

