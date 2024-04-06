package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;

@Entity
@Where(clause = "deleted <> true ")
@Getter
@Setter
@Table(name = "customer_business_potential_master")
public class CustomerProductMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Version
    @Column(name = "version")
    Long version;

    @ManyToOne
    @JoinColumn(name = "group_company_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"))
    GroupCompanyMaster groupCompanyMaster;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"))
    CompanyMaster companyMaster;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"))
    BranchMaster branchMaster;

    @Column(name = "sl_no", length = 50)
    private int serialNo;

    @ManyToOne
    @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_PRODUCT_SERVICE_ID"))
    ServiceMaster service;

    @ManyToOne
    @JoinColumn(name = "origin_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_PRODUCT_ORIGIN_ID"))
    PortMaster origin;

    @ManyToOne
    @JoinColumn(name = "destination_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_PRODUCT_DESTINATION_ID"))
    PortMaster destination;

    @Column(name = "all_origin_port")
    Boolean allOriginPort = false;

    @Column(name = "all_destination_port")
    Boolean allDestinationPort = false;

    @Column(name = "all_service")
    Boolean allService = false;

    @Column(name = "all_tos")
    Boolean allTos = false;

    @Range(min = 0, max = 10000)
    @Column(name = "no_of_shipment", length = 5)
    Integer noOfShipment;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_PRODUCT_CUSTOMER_ID"))
    @JsonBackReference
    CustomerMaster customer;

    @Column(name = "deleted")
    boolean deleted = false;

}
