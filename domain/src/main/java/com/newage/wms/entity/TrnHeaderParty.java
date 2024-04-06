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
@Table(name="wms_trn_header_party",schema = "tenant_default")
public class TrnHeaderParty extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @ManyToOne
    @JoinColumn(name = "shipper_id",foreignKey = @ForeignKey(name = "FK_SHIPPER_ID"))
    private CustomerMaster shipperMaster;

    @Column(name = "shipper_name", length = 30)
    private String shipperName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipper_address_id",foreignKey = @ForeignKey(name = "FK_SHIPPER_ADDRESS_ID"))
    private PartyAddressDetail shipperAddressDetail;

    @ManyToOne
    @JoinColumn(name = "consignee_id",foreignKey = @ForeignKey(name = "FK_CONSIGNEE_ID"))
    private CustomerMaster consigneeMaster;

    @Column(name = "consignee_name", length = 30)
    private String consigneeName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consignee_address_id",foreignKey = @ForeignKey(name = "FK_CONSIGNEE_ADDRESS_ID"))
    private PartyAddressDetail consigneeAddressDetail;

    @ManyToOne
    @JoinColumn(name = "forwarder_id",foreignKey = @ForeignKey(name = "FK_FORWARDER_ID"))
    private CustomerMaster forwarderMaster;

    @Column(name = "forwarder_name", length = 30)
    private String forwarderName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "forwarder_address_id",foreignKey = @ForeignKey(name = "FK_FORWARDER_ADDRESS_ID"))
    private PartyAddressDetail forwarderAddressDetail;

    @ManyToOne
    @JoinColumn(name = "transporter_id",foreignKey = @ForeignKey(name = "FK_TRANSPORTER_ID"))
    private CustomerMaster transporterMaster;

    @Column(name = "transporter_name", length = 30)
    private String transporterName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transporter_address_id",foreignKey = @ForeignKey(name = "FK_TRANSPORTER_ADDRESS_ID"))
    private PartyAddressDetail transporterAddressDetail;

    @OneToOne
    @JoinColumn(name = "transaction_id",nullable = false,foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"))
    @JsonIgnore
    private TrnHeaderAsn trnHeaderAsn;

}
