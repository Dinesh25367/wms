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
@Table(name="wms_transaction_lot",schema = "tenant_default")
public class TransactionLot extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ID"),nullable = false)
    private CustomerMaster customerMaster;

    @ManyToOne
    @JoinColumn(name = "sku_id", foreignKey = @ForeignKey(name = "FK_SKU_ID"),nullable = false)
    private SkuMaster skuMaster;

    @Column(name = "batch", length = 100)
    private String batch;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Column(name = "coo", length = 100)
    private String coo;

    @Column(name = "exp_date")
    private Date expDate;

    @Column(name = "mfg_date")
    private Date mfgDate;

    @Column(name = "lot_01", length = 100)
    private String lot01;

    @Column(name = "lot_02", length = 100)
    private String lot02;

    @Column(name = "lot_03", length = 100)
    private String lot03;

    @Column(name = "lot_04", length = 100)
    private String lot04;

    @Column(name = "lot_05", length = 100)
    private String lot05;

    @Column(name = "lot_06", length = 100)
    private String lot06;

    @Column(name = "lot_07", length = 100)
    private String lot07;

    @Column(name = "lot_08", length = 100)
    private String lot08;

    @Column(name = "lot_09", length = 100)
    private String lot09;

    @Column(name = "lot_10", length = 100)
    private String lot10;

    @OneToMany(targetEntity = TransactionLotMore.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_lot_more_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_TRANSACTION_LOT_MORE_ID"))
    private List<TransactionLotMore> transactionLotMoreList;
}
