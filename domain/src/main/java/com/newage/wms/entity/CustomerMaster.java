package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "deleted <> true")
@Getter
@Setter
@Table(name = "customer_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_uid", "updated_date"}, name = "UK_CUSTOMER_CODE"),
        @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_CUSTOMER_NAME")})
@org.hibernate.annotations.Cache(region = "customerCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    Long version;

    @ManyToOne
    @JoinColumn(name = "group_company_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"))
    GroupCompanyMaster groupCompanyMaster;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_COMP_ID"))
    CompanyMaster companyMaster;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_BRANCH_ID"))
    BranchMaster branchMaster;

    @NotNull
    @Size(min = 2, max = 15)
    @Column(name = "customer_uid", nullable = false, length = 10)
    private String code;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "grade_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_GRADE_ID"))
    GradeMaster grade;

    @ManyToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_COUNTRY_ID"))
    CountryMaster country;

    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "search_keys", nullable = false)
    private String searchKey;

    @Column(name = "customer_access_status", length = 10)
    String status;

    @Column(name = "business_relation_status", length = 10)
    private String businessRelationStatus;

    @Column(name = "business_relation_status_note", length = 10)
    String businessRelationStatusNote;

    @Column(name = "customer_status", length = 10)
    private String customerStatus;

    @Column(name = "is_overseas_agent")
    private Boolean overseasAgent;

    @Column(name = "customer_term")
    private String customerTerm;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerTypeCustomerMaster> customerType = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CustomerAddressMaster> customerAddressMasterList = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CustomerContactMaster> customerContactMasterList = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CustomerProductMaster> customerProductMasterList = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CustomerMasterEmployee> customerEmployees = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CustomerEventMaster> customerEventMasterList = new ArrayList<>();

    @Column(name = "deleted")
    boolean deleted = false;

}
