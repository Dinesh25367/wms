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
@Table(name="wms_aisle_master",schema = "tenant_default",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code","warehouse_id"}, name = "UK_AISLE_CODE_AND_WAREHOUSE_ID")})
public class AisleMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Version
    @Column(name = "version")
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

    @Column(name = "code", length = 30,nullable = false)
    private String code;

    @Column(name = "name", length = 50,nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", foreignKey = @ForeignKey(name = "FK_WAREHOUSE_ID"),nullable = false)
    private WareHouseMaster wareHouseMaster;

    @ManyToOne
    @JoinColumn(name = "zone_id", foreignKey = @ForeignKey(name = "FK_ZONE_ID"))
    private ZoneMasterWMS zoneMaster;

    @ManyToOne
    @JoinColumn(name = "storage_area_id", foreignKey = @ForeignKey(name = "FK_STORAGE_AREA_ID"),nullable = false)
    private StorageAreaMaster storageAreaMaster;

    @ManyToOne
    @JoinColumn(name = "storage_type_id", foreignKey = @ForeignKey(name = "FK_STORAGE_TYPE_ID"),nullable = false)
    private StorageTypeMaster storageTypeMaster;

    @Column(name = "status", length = 10,nullable = false)
    private String status;

    @Column(name = "note", length = 4000)
    private String note;

}
