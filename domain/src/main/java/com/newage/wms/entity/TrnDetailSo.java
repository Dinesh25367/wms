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
@Table(name="wms_trn_detail_so",schema = "tenant_default")

public class TrnDetailSo extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "transaction_sl_no", length = 5, nullable = false)
    private Integer transactionSlNo;

    @Column(name = "pick_from_customs_doc_ref1")
    private String pickFromCustomDocRef1;

    @Column(name = "pick_from_customs_doc_ref2")
    private String pickFromCustomDocRef2;

    @Column(name = "pick_from_asn")
    private String pickFromAsn;

    @Column(name = "pick_from_grn")
    private String pickFromGrn;

    @Column(name = "pick_from_po")
    private String pickFromPo;

    @Column(name = "pick_from_lpn")
    private String pickFromLpn;

    @Column(name = "pick_from_location")
    private String pickFromLocation;

    @Column(name = "sale_line_total_value")
    private String saleLineTotalValue;

    @Column(name = "is_expired_allowed")
    private String isExpiredAllowed;

    @Column(name = "is_damaged_allowed")
    private String isDamagedAllowed;


    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnDetail trnDetail;


}
