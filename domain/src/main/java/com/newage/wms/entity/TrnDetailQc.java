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
@Table(name="wms_trn_detail_qc",schema = "tenant_default")
public class TrnDetailQc extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "transaction_sl_no", length = 5, nullable = false)
    private Integer transactionSlNo;

    @Column(name = "qc_required", length = 3)
    private String qcRequired;

    @Column(name = "inspect_pack_qty", length = 22)
    private Double inspectPackQty;

    @Column(name = "inspect_piece_qty", length = 22)
    private Double inspectPieceQty;

    @Column(name = "inspected_pack_qty", length = 22)
    private Double inspectedPackQty;

    @Column(name = "inspected_piece_qty", length = 22)
    private Double inspectedPieceQty;

    @Column(name = "failed_qty", length = 22)
    private Double failedQty;

    @ManyToOne
    @JoinColumn(name = "qc_status_id",foreignKey = @ForeignKey(name = "FK_QC_STATUS_ID"))
    private QcStatusMaster inspectionStatusMaster;

    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "FK_USER_ID"))
    private AuthUserProfile inspectedByMaster;

    @Column(name = "qc_note", length = 4000)
    private String qcNote;

    @Column(name = "qc_result", length = 10)
    private String qcResult;

    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnDetail trnDetail;

}
