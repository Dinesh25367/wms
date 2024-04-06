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
@Table(name="wms_trn_header_freight_shipping",schema = "tenant_default")
public class TrnHeaderFreightShipping extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @ManyToOne
    @JoinColumn(name = "carrier_id",foreignKey = @ForeignKey(name = "FK_CARRIER_ID"))
    private CarrierMaster carrierMaster;

    @ManyToOne
    @JoinColumn(name="vessel_id",foreignKey = @ForeignKey(name = "FK_VESSEL_ID"))
    private VesselMaster vesselMaster;

    @ManyToOne
    @JoinColumn(name="origin_id",foreignKey = @ForeignKey(name="FK_ORIGIN_ID"))
    private OriginMaster originMaster;

    @ManyToOne
    @JoinColumn(name = "tos_id",foreignKey = @ForeignKey(name="FK_TOS_ID"))
    private TosMaster tosMaster;

    @Column(name="master_doc_ref",length = 30)
    private String masterDocRef;

    @Column(name="master_doc_date")
    private Date masterDocDate;

    @Column(name="house_doc_ref",length = 30)
    private String houseDocRef;

    @Column(name="house_doc_date")
    private Date houseDocDate;

    @Column(name="eta")
    private Date eta;

    @Column(name="ata")
    private Date ata;

    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnHeaderAsn trnHeaderAsn;

    /*
     * Method to set null values in empty string
     */
    public void setNullInEmptyString(){
        if (masterDocRef != null && (masterDocRef.isEmpty() || masterDocRef.isBlank())) {
            this.masterDocRef = null;
        }
        if (houseDocRef != null && (houseDocRef.isEmpty() || houseDocRef.isBlank())) {
            this.houseDocRef = null;
        }
    }

}
