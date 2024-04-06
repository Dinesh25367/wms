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
@Table(name="wms_inventory_status_master",schema = "tenant_default")
public class InventoryStatusMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "name", length = 30,nullable = false)
    private String name;

    @Column(name = "code", length = 30,nullable = false)
    private String code;

    @Column(name = "is_saleable", length = 30,nullable = false)
    private String isSaleable;

    @Column(name = "description",length = 100)
    private String description;

    @Column(name = "note", length = 4000)
    private String note;

}