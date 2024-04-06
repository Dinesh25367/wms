package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "efs_branch_address_master")
public class BranchAddressMaster extends Auditable<String>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "address_line_1")
    String addressLine1;

    @Column(name = "address_line_2")
    String addressLine2;

    @Column(name = "address_line_3")
    String addressLine3;

    @Column(name = "zip_code")
    String zipCode;

    @Column(name = "phone_no", length = 100)
    String phoneNo;

    @Column(name = "mobile_no", length = 100)
    String mobileNo;

    @Column(name = "email", length = 500)
    String email;

    @Column(name = "fax", length = 100)
    String fax;

    @Column(name = "primary_address")
    boolean primaryAddress = false;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_BRANCH_CURRENCYID"))
    BranchMaster branchMaster;

    @Version
    @Column(name = "version")
    Long version;

}
