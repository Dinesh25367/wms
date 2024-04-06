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
@Table(name="wms_rack_master",schema = "tenant_default",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code","warehouse_id"}, name = "UK_RACK_CODE_AND_WAREHOUSE_ID")})
public class RackMaster extends Auditable<String>{

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

    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", foreignKey = @ForeignKey(name = "FK_WAREHOUSE_ID"))
    private WareHouseMaster wareHouseMaster;

    @ManyToOne
    @JoinColumn(name = "aisle_id", foreignKey = @ForeignKey(name = "FK_AISLE_ID"))
    private AisleMaster aisleMaster;

    @Column(name = "side", length = 30)
    private String side;

    @ManyToOne
    @JoinColumn(name = "storage_area_id", foreignKey = @ForeignKey(name = "FK_STORAGE_AREA_ID"))
    private StorageAreaMaster storageAreaMaster;

    @ManyToOne
    @JoinColumn(name = "storage_type_id", foreignKey = @ForeignKey(name = "FK_STORAGE_TYPE_ID"))
    private StorageTypeMaster storageTypeMaster;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "note", length = 4000)
    private String note;

}
