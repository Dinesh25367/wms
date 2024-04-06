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
@Table(name="wms_trn_detail_asn_customs",schema = "tenant_default")
public class TrnDetailAsnCustoms extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "transaction_sl_no", length = 5, nullable = false)
    private Integer transactionSlNo;

    @Column(name = "fta_value", length = 18)
    private Double ftaValue;

    @Column(name = "case_pcb", length = 21)
    private Double casePcb;

    @Column(name = "outer_carton_pcb",length = 21)
    private Double outerCartonPcb;

    @Column(name = "packet_pcb", length = 21)
    private Double packetPcb;

    @Column(name = "pcs_qty", length = 32)
    private Double pcsQty;

    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnDetail trnDetail;

}
