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
@Table(name="wms_trn_detail",schema = "tenant_default")
public class TrnDetail extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "transaction_uid", length = 30, nullable = false)
    private String transactionUid;

    @Column(name = "transaction_sl_no", length = 7, nullable = false)
    private Integer transactionSlNo;

    @ManyToOne
    @JoinColumn(name = "sku_id",foreignKey = @ForeignKey(name = "FK_SKU_ID"),nullable = false)
    private SkuMaster skuMaster;

    @Column(name = "name", length = 50,nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "uom_id",foreignKey = @ForeignKey(name = "FK_UOM_ID"),nullable = false)
    private UomMaster uomMaster;

    @Column(name = "exp_pack_qty", length = 32,nullable = false)
    private Double expQty;

    @ManyToOne
    @JoinColumn(name = "r_uom_id",foreignKey = @ForeignKey(name = "FK_R_UOM_ID"),nullable = false)
    private UomMaster rUomMaster;

    @Column(name = "exp_piece_qty", length = 22,nullable = false)
    private Double rqty;

    @Column(name = "box_id", length = 30)
    private String boxId;

    @ManyToOne
    @JoinColumn(name = "hs_code_id",foreignKey = @ForeignKey(name = "FK_HS_CODE_ID"))
    private HsCodeMaster hsCodeMaster;

    @ManyToOne
    @JoinColumn(name = "currency_id",foreignKey = @ForeignKey(name = "FK_CURRENCY_ID"),nullable = false)
    private CurrencyMaster currencyMaster;

    @Column(name = "currency_rate", length = 26,nullable = false)
    private Double rate;

    @Column(name = "is_back_order")
    private String isBackOrder;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnDetail")
    private TrnDetailLot trnDetailLot;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnDetail")
    private TrnDetailAsnCustoms trnDetailAsnCustoms;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnDetail")
    private TrnDetailAsn trnDetailAsn;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnDetail")
    private TrnDetailQc trnDetailQc;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trnDetail")
    private TrnDetailSo trnDetailSo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    TrnHeaderAsn trnHeaderAsn;

}
