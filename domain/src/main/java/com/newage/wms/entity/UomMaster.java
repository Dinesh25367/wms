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
@Table(name="wms_uom_master",schema = "tenant_default",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "UK_UOM_CODE")})
public class UomMaster extends Auditable<String>{

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

    @Column(name = "description", length = 50,nullable = false)
    private String description;

    @Column(name = "restriction_of_uom",length = 10)
    private String restrictionOfUom;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_ID"),nullable = false)
    private CategoryMaster categoryMaster;

    @Column(name = "reference",length = 10)
    private String reference;

    @Column(name = "ratio")
    private Double ratio;

    @Column(name = "rounding")
    private Long decimalPlaces;

    @Column(name = "status",length = 10,nullable = false)
    private String status;

}
