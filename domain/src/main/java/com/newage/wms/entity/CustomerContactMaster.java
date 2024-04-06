package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Entity
@Where(clause = " deleted <> true ")
@Getter
@Setter
@Table(name = "customer_contact_master")
@AllArgsConstructor
@Builder
public class CustomerContactMaster extends Auditable<String> {

    public CustomerContactMaster(){
        //this method is to initialize Customer Contact Master
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

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

    @Column(name = "salutation", length = 10)
    private String contactSalutation;

    @NonNull
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NonNull
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "is_primary", length = 10)
    private boolean primary;

    @ManyToOne
    @JoinColumn(name = "designation_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_CONTACT_DESIGNATION_ID"))
    @JsonBackReference
    private DesignationMaster designation;

    @ManyToOne
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_CONTACT_DEPARTMENT_ID"))
    @JsonBackReference
    private DepartmentMaster department;

    @Column(name = "pre_mobile_no", length = 10)
    private String preMobileNo;

    @Column(name = "mobile_no", length = 100)
    private String mobileNo;

    @Column(name = "pre_office_phone_no", length = 10)
    private String preOfficeNo;

    @Column(name = "office_phone_no", length = 100)
    private String officePhone;

    @Column(name = "email", length = 500)
    private String email;

    @ManyToOne
    @JoinColumn(name = "city_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_CONTACT_CITY_ID"))
    @JsonBackReference
    private CityMaster city;

    @Column(name = "preferred_contact_method", length = 10)
    private String preferredContactMethod;

    @Column(name = "deleted")
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_CONTACT_CUSTOMER_ID"))
    @JsonBackReference
    private CustomerMaster customer;

}