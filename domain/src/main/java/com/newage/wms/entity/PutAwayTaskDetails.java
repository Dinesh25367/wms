package com.newage.wms.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="wms_putaway_task_details",schema = "tenant_default")
public class PutAwayTaskDetails extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id",foreignKey = @ForeignKey(name = "FK_SKU_ID"),nullable = false)
    private SkuMaster skuMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", foreignKey = @ForeignKey(name = "FK_UOM_ID"))
    private UomMaster uomMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruom_id", foreignKey = @ForeignKey(name = "FK_RUOM_ID"))
    private UomMaster ruomMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "putAway_task_header_id", foreignKey = @ForeignKey(name = "FK_PUTAWAY_TASK_HEADER_ID"))
    @JsonIgnore
    PutAwayTaskHeader putAwayTaskHeader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toLoc_id", foreignKey = @ForeignKey (name = "FK_LOCATION_ID"))
    private Location toLoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sugLoc_id", foreignKey = @ForeignKey (name = "FK_LOCATION_ID"))
    private Location sugLoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", foreignKey = @ForeignKey (name = "FK_LOT_ID"))
    private TransactionLot transactionLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_Detail_id" , foreignKey = @ForeignKey(name = "FK_GRN_DETAIL_ID"))
    private GrnDetail grnDetail;

    @Column(name = "transaction_sl_no", length = 7, nullable = false)
    private Integer transactionSlNo;

    @Column(name = "uom_qty", length = 30)
    private Double uomQty;

    @Column(name = "ruom_qty", length = 30)
    private Double ruomQty;

    @Column(name = "eanupc", length = 30)
    private String eanupc;

    @Column(name = "qty", length = 30)
    private Double qty;

    @Column(name = "carton_id", length = 30)
    private String cartonId;

    @Column(name = "lpn_id", length = 30)
    private String lpnId;

    @Column(name = "volume", length = 32)
    private Double volume;

    @Column(name = "weight", length = 23)
    private Double weight;

    @Column(name = "putAway_qty", length = 23)
    private Double putAwayQty;

    @Column(name = "status")
    private String status;

}
