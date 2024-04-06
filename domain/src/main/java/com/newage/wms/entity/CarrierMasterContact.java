package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "efs_carrier_master_contact_details",schema = "tenant_default")
public class CarrierMasterContact extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_CARRIER_LOCATION"))
    BranchMaster branch;
    //TO-DO Need to Refer Agent Master once ready
    @OneToOne
    @JoinColumn(name = "agent_code", foreignKey = @ForeignKey(name = "FK_CARRIER_AGENTNAME"))
    CustomerMaster agentCode;
    //TO-DO Need to Refer Agent Master once ready
    @OneToOne
    @JoinColumn(name = "agent_name", foreignKey = @ForeignKey(name = "FK_CARRIER_AGENTCODE"))
    CustomerMaster agentName;

    @Column(name = "account_number", length = 30)
    String accountNumber;

    @Column(name = "email", length = 200)
    private String agentEmail;

    @ManyToOne
    @JoinColumn(name = "carrier_id", foreignKey = @ForeignKey(name = "FK_RESPONSES_CARRIERID"))
    CarrierMaster carrierMaster;

}
