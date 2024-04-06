package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "customer_additional_info")
public class CustomerAdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_entity_id", length = 20)
    private Long companyId;

    @Column(name = "group_company_id", length = 20)
    private Long groupCompanyId;

    @Column(name = "branch_id", length = 20)
    private Long branchId;

    @Column(name = "tsa_validation_number", length = 40)
    @Pattern(regexp = "^[a-zA-Z0-9@.-]*$", message = "Special characters are not allowed in tsa_verification_number.")
    private String tsaValidationNumber;

    @Column(name = "tsa_validation_date", length = 40)
    private Date tsaValidationDate;

    @Column(name = "tsa_verification_number", length = 40)
    @Pattern(regexp = "^[a-zA-Z0-9@.-]*$", message = "Special characters are not allowed in tsa_verification_number.")
    private String tsaVerificationNumber;

    @Column(name = "pan_number", length = 11)
    @Pattern(regexp = "^[a-zA-Z0-9@.-]*$", message = "Special characters are not allowed in tsa_verification_number.")
    private String panNumber;

    @Column(name = "vat_number", length = 40)
    @Pattern(regexp = "^[a-zA-Z0-9@.-]*$", message = "Special characters are not allowed in tsa_verification_number.")
    private String vatNumber;

    @Column(name = "tax_id_no", length = 30)
    @Pattern(regexp = "^[a-zA-Z0-9@.-]*$", message = "Special characters are not allowed in tsa_verification_number.")
    private String taxIdNo;

    @Column(name = "known_shipper", nullable = false, length = 10)
    private String knownShipper;

    @Column(name = "blanket", nullable = false, length = 10)
    private String blanket;

    @Column(name = "agent_iata_code", length = 40)
    @Pattern(regexp = "^[a-zA-Z0-9@.-]*$", message = "Special characters are not allowed in tsa_verification_number.")
    private String agentIATACode;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ADDITIONAL-INFO"))
    private  CustomerMaster customer;

    @Version
    @Column(name = "version")
    private Long version;

}
