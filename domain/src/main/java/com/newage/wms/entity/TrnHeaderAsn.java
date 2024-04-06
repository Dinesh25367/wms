package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wms_trn_header_asn",schema = "tenant_default")
public class TrnHeaderAsn extends Auditable<String>{

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

    @Column(name = "transaction_uid", length = 300, nullable = false)
    private String transactionUid;

    @Column(name = "asn_id")
    private Long transactionId;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", foreignKey = @ForeignKey(name = "FK_WAREHOUSE_ID"),nullable = false)
    private WareHouseMaster wareHouseMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",foreignKey = @ForeignKey(name = "FK_CUSTOMER_ID"),nullable = false)
    private CustomerMaster customerMaster;

    @Column(name = "customer_order_no", length = 100)
    private String customerOrderNo;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "customer_invoice_no", length = 100)
    private String customerInvoiceNo;

    @Column(name = "invoice_date")
    private Date invoiceDate;

    @ManyToOne
    @JoinColumn(name = "movement_type_id",foreignKey = @ForeignKey(name = "FK_MOVEMENT_TYPE_ID"),nullable = false)
    private MovementTypeMaster movementTypeMaster;

    @Column(name = "is_edi_transaction",length = 15,nullable = false)
    private String isEdiTransaction;

    @Column(name = "expected_receiving_date", nullable = false)
    private Date expectedReceivingDate;

    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "is_cross_dock",length = 15)
    private String isCrossDock;

    @Column(name = "is_confirmation_edi",length = 15)
    private String isConfirmationEdi;

    @Column(name = "priority", length = 30)
    private String priority;

    @Column(name = "is_expiry_allowed",length = 15)
    private String isExpiryAllowed;

    @Column(name = "is_blind_receipt",length = 15)
    private String isBlindReceipt;

    @Column(name = "transaction_status", length = 30, nullable = false)
    private String transactionStatus;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "order_type", length = 30)
    private String orderType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnHeaderAsn")
    private TrnHeaderFreight trnHeaderFreight;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnHeaderAsn")
    private TrnHeaderSo trnHeaderSo;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnHeaderAsn")
    private TrnHeaderParty trnHeaderParty;

    @OneToMany(targetEntity = TrnHeaderTransportation.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    private List<TrnHeaderTransportation> trnHeaderTransportationList;

    @OneToMany(targetEntity = TrnHeaderCustomsAddlDetails.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    private List<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsList;

    @OneToMany(targetEntity = TrnHeaderAddlDetails.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    private List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnHeaderAsn")
    private TrnHeaderFreightShipping trnHeaderFreightShipping;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnHeaderAsn")
    private TrnHeaderCustomsDocument trnHeaderCustomsDocument;

    @OneToMany(mappedBy = "trnHeaderAsn",cascade = CascadeType.ALL)
    private List<TrnDetail> trnDetailList;

    /*
     * Method to set null values in empty string
     */
    public void setNullInEmptyString(){
        if (customerOrderNo != null && (customerOrderNo.isEmpty() || customerOrderNo.isBlank())) {
            this.customerOrderNo = null;
        }
        if (customerInvoiceNo != null && (customerInvoiceNo.isEmpty() || customerInvoiceNo.isBlank())) {
            this.customerInvoiceNo = null;
        }
        if (status != null && (status.isEmpty() || status.isBlank())) {
            this.status = null;
        }
        if (note != null && (note.isEmpty() || note.isBlank())) {
            this.note = null;
        }
    }

}