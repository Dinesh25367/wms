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
@Table(name="wms_grn_detail",schema = "tenant_default")
public class GrnDetail extends Auditable<String>{

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

    @ManyToOne
    @JoinColumn(name = "uom_id", foreignKey = @ForeignKey(name = "FK_UOM_ID"))
    private UomMaster uomMaster;

    @Column(name = "receiving_qty", length = 32)
    private Double receivingQty;

    @Column(name = "volume", length = 32)
    private Double volume;

    @Column(name = "weight", length = 23)
    private Double weight;

    @Column(name = "carton_id", length = 30)
    private String cartonId;

    @Column(name = "lpn_id", length = 30)
    private String lpnId;

    @ManyToOne
    @JoinColumn(name = "r_uom_id",foreignKey = @ForeignKey(name = "FK_R_UOM_ID"))
    private UomMaster rUomMaster;

    @Column(name = "receiving_r_qty", length = 22)
    private Double receivingRQty;

    @Column(name = "exp_qty_while_receiving", length = 32)
    private Double expQtyWhileReceiving;

    @ManyToOne
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "FK_LOCATION_ID"))
    private Location locationMaster;

    @Column(name = "deleted", length = 10)
    private String deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_id", foreignKey = @ForeignKey(name = "FK_GRN_ID"))
    @JsonIgnore
    GrnHeader grnHeader;

    @ManyToOne
    @JoinColumn(name="inventory_status_id", foreignKey = @ForeignKey(name = "FK_INVENTORY_STATUS_ID"))
    private InventoryStatusMaster inventoryStatusMaster;

    @ManyToOne
    @JoinColumn(name="transportation_id", foreignKey = @ForeignKey(name = "FK_TRANSPORTATION_ID"))
    private TrnHeaderTransportation trnHeaderTransportation;

    @Column(name = "unit",length = 30)
    private String unit;

    @Column(name = "length")
    private String length;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "isChecked")
    private Boolean isChecked;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "grnDetail")
    private GrnLotDetail grnLotDetail;

}
