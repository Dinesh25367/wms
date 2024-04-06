package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wms_transaction_history",schema = "tenant_default")
public class TransactionHistory extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "group_company_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMPANY_ID"))
    private GroupCompanyMaster groupCompanyMaster;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_COMPANY_ID"))
    private CompanyMaster companyMaster;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_BRANCH_ID"))
    private BranchMaster branchMaster;

    @Column(name = "reference_id",length = 30)
    private String referenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", foreignKey = @ForeignKey(name = "FK_WAREHOUSE_ID"))
    private WareHouseMaster wareHouseMaster;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "task_sl_no")
    private Integer taskSlNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    TrnHeaderAsn trnHeaderAsnMaster;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_sl_no")
    private Integer transactionSlNo;

    @Column(name = "transaction_no",length = 300)
    private String transactionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_transaction_id", foreignKey = @ForeignKey(name = "FK_SOURCE_TRANSACTION_ID"))
    @JsonIgnore
    TrnHeaderAsn sourceTrnHeaderAsnMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_detail_id", foreignKey = @ForeignKey(name = "FK_GRN_DETAIL_ID"))
    @JsonIgnore
    GrnDetail grnDetail;

    @Column(name = "source_transaction_type")
    private String sourceTransactionType;

    @Column(name = "source_transaction_sl_no")
    private Integer sourceTransactionSlNo;

    @Column(name = "source_transaction_no",length = 300)
    private String sourceTransactionNo;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ID"))
    private CustomerMaster customerMaster;

    @ManyToOne
    @JoinColumn(name = "sku_id", foreignKey = @ForeignKey(name = "FK_SKU_ID"))
    private SkuMaster skuMaster;

    @ManyToOne
    @JoinColumn(name = "uom_id", foreignKey = @ForeignKey(name = "FK_UOM_ID"))
    private UomMaster uomMaster;

    @Column(name = "lpn_id",length = 30)
    private String lpnId;

    @Column(name = "carton_id",length = 30)
    private String cartonId;

    @Column(name = "in_out")
    private String inOut;

    @ManyToOne
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "FK_LOCATION_ID"))
    private Location locationMaster;

    @ManyToOne
    @JoinColumn(name = "lot_id", foreignKey = @ForeignKey(name = "FK_LOT_ID"))
    private TransactionLot transactionLotMaster;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "gross_weight")
    private Double grossWeight;

    @ManyToOne
    @JoinColumn(name = "inv_status_id", foreignKey = @ForeignKey(name = "FK_INVENTORY_STATUS_ID"))
    private InventoryStatusMaster invStatus;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "note",length = 4000)
    private String note;

    @Column(name = "actual_date")
    private Date actualDate;


}
