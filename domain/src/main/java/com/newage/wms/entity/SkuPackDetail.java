package com.newage.wms.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="wms_sku_pack_details",schema = "tenant_default")
public class SkuPackDetail extends Auditable<String>{

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

    @Column(name = "uom_type",nullable = false,length = 30)
    private String uomType;

    @ManyToOne
    @JoinColumn(name = "uom_id", foreignKey = @ForeignKey(name = "FK_UOM_ID"),nullable = false)
    private UomMaster uomMaster;

    @Column(name = "ratio",nullable = false)
    private String ratio;

    @Column(name = "unit",length = 30,nullable = false)
    private String unit;

    @Column(name = "length",nullable = false)
    private String length;

    @Column(name = "width",nullable = false)
    private String width;

    @Column(name = "height",nullable = false)
    private String height;

    @Column(name = "nw",nullable = false)
    private String nw;

    @Column(name = "gw",nullable = false)
    private String gw;

    @Column(name = "vol",nullable = false)
    private String vol;

    @Column(name = "actual",nullable = false,length = 5)
    private String actual;

}

