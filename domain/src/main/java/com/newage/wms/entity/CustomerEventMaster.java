package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Where(clause = "deleted <> true ")
@Getter
@Setter
@Table(name = "customer_event_master")
public class CustomerEventMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "group_company_id", foreignKey = @ForeignKey(name = "FK_EVENT_GROUP_COMP_ID"))
    GroupCompanyMaster groupCompanyMaster;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_EVENT_COMP_ID"))
    CompanyMaster companyMaster;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_EVENT_BRANCH_ID"))
    BranchMaster branchMaster;

    @ManyToOne
    @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_SERVICE_ID"))
    ServiceMaster service;

    @ManyToOne
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_EVENT_ID"))
    EventMaster event;

    @Column(name = "email")
    String eventEmails;

    @NotNull(message = "Accepted Values (Import, Export, NA)")
    @Column(name = "trade_code", length = 10)
    private String tradeCode;

    @Column(name = "event_service", length = 30)
    private String eventService;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_EMAIL_EVENT_CUSTOMER_ID"))
    @JsonBackReference
    CustomerMaster customer;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}
