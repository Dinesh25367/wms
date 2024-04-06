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
@Table(name="wms_trn_header_so",schema = "tenant_default")
public class TrnHeaderSo extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "is_our_job",length = 15,nullable = false)
    private String isOurJob;

    @ManyToOne
    @JoinColumn(name = "booking_ref_shipment_id",foreignKey = @ForeignKey(name = "FK_BOOKING_REF_SHIPMENT_ID"))
    private ShipmentHeader bookingRefShipmentMaster;

    @Column(name = "service",length = 30,nullable = false)
    private String service;

    @Column(name = "trade",length = 30)
    private String trade;

    @Column(name = "chargeable_weight", length = 17)
    private String chargeableWeight;

    @Column(name = "chargeable_Volume", length = 12)
    private String chargeableVolume;

    @Column(name = "chargeable_pallet_count", length = 7)
    private String chargeablePalletCount;

    @ManyToOne
    @JoinColumn(name = "order_currency_id",foreignKey = @ForeignKey(name = "FK_ORDER_CURRENCY_ID"),nullable = false)
    private CurrencyMaster orderCurrencyMaster;

    @Column(name = "order_currency_rate", length = 27,nullable = false)
    private String orderCurrencyRate;

    @Column(name = "order_currency_amount")
    private String orderCurrencyAmount;

    @Column(name = "self_life")
    private String selfLife;

    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnHeaderAsn trnHeaderAsn;

}
