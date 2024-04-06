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
@Table(name="wms_grn_lot_detail",schema = "tenant_default")
public class GrnLotDetail extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "transaction_sl_no", length = 7, nullable = false)
    private Integer transactionSlNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_detail_id", foreignKey = @ForeignKey(name = "FK_TRANSACTION_DETAIL_ID"),nullable = false)
    private TrnDetail trnDetailMaster;

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

    @ManyToOne
    @JoinColumn(name = "hs_code_id",foreignKey = @ForeignKey(name = "FK_HS_CODE_ID"))
    private HsCodeMaster hsCodeMaster;

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

    @Column(name = "deleted", length = 10)
    private String deleted;

    @OneToOne
    @JoinColumn(name = "grn_detail_id",nullable = false,foreignKey = @ForeignKey(name = "FK_GRN_DETAIL_ID"))
    @JsonIgnore
    private GrnDetail grnDetail;

}
