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
@Table(name="wms_trn_header_freight",schema = "tenant_default")
public class TrnHeaderFreight extends Auditable<String>{

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
    @JoinColumn(name = "freight_ref_shipment_id",foreignKey = @ForeignKey(name = "FK_FREIGHT_REF_SHIPMENT_ID"))
    private ShipmentHeader freightRefShipmentMaster;

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
    @JoinColumn(name = "freight_currency_id",foreignKey = @ForeignKey(name = "FK_FREIGHT_CURRENCY_ID"),nullable = false)
    private CurrencyMaster freightCurrencyMaster;

    @Column(name = "freight_currency_rate", length = 27,nullable = false)
    private String freightCurrencyRate;

    @Column(name = "freight_currency_amount")
    private String freightCurrencyAmount;

    @Column(name = "freight_apportion", length = 30)
    private String freightApportion;

    @Column(name = "self_life")
    private String selfLife;

    @ManyToOne
    @JoinColumn(name = "insurance_currency_id",foreignKey = @ForeignKey(name = "FK_INSURANCE_CURRENCY_ID"),nullable = false)
    private CurrencyMaster insuranceCurrencyMaster;

    @Column(name = "insurance_currency_rate", length = 27,nullable = false)
    private String insuranceCurrencyRate;

    @Column(name = "insurance_currency_amount")
    private String insuranceCurrencyAmount;

    @Column(name = "insurance_apportion", length = 30)
    private String insuranceApportion;

    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnHeaderAsn trnHeaderAsn;

    /*
     * Method to set null values in empty string
     */
    public void setNullInEmptyString(){
        setNullInEmptyStringForFirstFourFields();
        setNullInEmptyStringForLastFourFields();
    }

    private void setNullInEmptyStringForFirstFourFields(){
        if (trade != null && (trade.isEmpty() || trade.isBlank())) {
            this.trade = null;
        }
        if (chargeableWeight != null && (chargeableWeight.isEmpty() || chargeableWeight.isBlank())) {
            this.chargeableWeight = null;
        }
        if (chargeableVolume != null && (chargeableVolume.isEmpty() || chargeableVolume.isBlank())) {
            this.chargeableVolume = null;
        }
        if (chargeablePalletCount != null && (chargeablePalletCount.isEmpty() || chargeablePalletCount.isBlank())) {
            this.chargeablePalletCount = null;
        }
    }

    private void setNullInEmptyStringForLastFourFields(){
        if (freightCurrencyAmount != null && (freightCurrencyAmount.isEmpty() || freightCurrencyAmount.isBlank())) {
            this.freightCurrencyAmount = null;
        }
        if (freightCurrencyAmount != null && (freightApportion.isEmpty() || freightApportion.isBlank())) {
            this.freightApportion = null;
        }
        if (insuranceCurrencyAmount != null && (insuranceCurrencyAmount.isEmpty() || insuranceCurrencyAmount.isBlank())) {
            this.insuranceCurrencyAmount = null;
        }
        if (insuranceApportion != null && (insuranceApportion.isEmpty() || insuranceApportion.isBlank())) {
            this.insuranceApportion = null;
        }
    }



}
