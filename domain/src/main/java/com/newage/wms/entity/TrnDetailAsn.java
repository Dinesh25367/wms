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
@Table(name="wms_trn_detail_asn",schema = "tenant_default")
public class TrnDetailAsn extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "actual_pack_qty", length = 32)
    private Double actualQty;

    @Column(name = "actual_piece_qty", length = 22)
    private Double actualRQty;

    @Column(name = "transaction_sl_no", length = 7, nullable = false)
    private Integer transactionSlNo;

    @Column(name = "volume", length = 32,nullable = false)
    private Double volume;

    @Column(name = "gross_weight", length = 23,nullable = false)
    private Double grossWeight;

    @Column(name = "net_weight", length = 23,nullable = false)
    private Double netWeight;

    @Column(name = "lpn_id", length = 30)
    private String lpnId;

    @Column(name = "document_line_total_amount", length = 24)
    private Double documentLineTotalAmount;

    @Column(name = "actual_line_total_amount", length = 24)
    private Double actualLineTotalAmount;

    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnDetail trnDetail;

}
