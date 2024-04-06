package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Entity
@Where(clause = "deleted <> true")
@Getter
@Setter
@Table(name = "customer_address_master")
public class CustomerAddressMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_company_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"))
    GroupCompanyMaster groupCompanyMaster;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"))
    CompanyMaster companyMaster;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"))
    BranchMaster branchMaster;

    @OneToOne
    @JoinColumn(name = "adress_type_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ADRESS_TYPE_ID"))
    AddressTypeMaster addressType;

    @Column(name = "po_box_no", length = 15)
    String poBoxNo;

    @Column(name = "street_name", length = 100)
    String streetName;

    @Column(name = "building_no_name", length = 100)
    String buildingNoName;

    @Column(name = "landmark", length = 100)
    String landMark;

    @Column(name = "latitude")
    Double latitude;

    @Column(name = "longitude")
    Double longitude;

    @Column(name = "location_name")
    String locationName;

    @OneToOne
    @JoinColumn(name = "city_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ADDRESS_CITY_ID"))
    CityMaster city;

    @OneToOne
    @JoinColumn(name = "state_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ADDRESS_STATE_ID"))
    StateMaster state;

    @OneToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ADDRESS_COUNTRY_ID"))
    CountryMaster country;

    @Column(name = "contact_person", length = 100)
    String contactPerson;

    @Column(name = "phone", length = 100)
    String phone;

    @Column(name = "mobile_no", length = 100)
    String mobileNo;

    @Column(name = "email", length = 500)
    String email;

    @Column(name = "communication_address", length = 10)
    boolean communicationAddress;

    @Column(name = "corporate_address", length = 10)
    boolean corporateAddress;

    @Column(name = "web_domain_address", length = 10)
    boolean webDomainAddress;

    @Column(name = "zip_code", length = 10)
    String zipCode;

    @Column(name = "map_link", length = 200)
    String mapLink;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ADDRESS_CUSTOMER_ID"))
    @JsonBackReference
    CustomerMaster customer;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}

