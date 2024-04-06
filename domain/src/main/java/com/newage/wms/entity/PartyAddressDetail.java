package com.newage.wms.entity;

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
@Table(name="party_address_detail")
public class PartyAddressDetail extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "group_company_id")
    private Long groupCompanyId;

    @Column(name = "company_entity_id")
    private Long companyEntityId;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "source_type", length = 100)
    private String sourceType;

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "sl_no", length = 20)
    private String slNo;

    @Column(name = "party_type", length = 200)
    private String partyType;

    @Column(name = "address_line_1", length = 200)
    private String addressLine1;

    @Column(name = "address_line_2", length = 200)
    private String addressLine2;

    @Column(name = "address_line_3", length = 200)
    private String addressLine3;

    @Column(name = "building_no", length = 100)
    private String buildingNo;

    @Column(name = "street_name", length = 200)
    private String streetName;

    @Column(name = "state_name", length = 200)
    private String stateName;

    @Column(name = "city_name", length = 200)
    private String cityName;

    @Column(name = "po_box", length = 100)
    private String poBox;

    @Column(name = "contact")
    private Long contact;

    @ManyToOne
    @JoinColumn(name = "state_id",foreignKey = @ForeignKey(name = "FK_STATE_ID"))
    private StateMaster state;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private CountryMaster country;

    @Column(name = "email", length = 200)
    private String email;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityMaster city;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "contact_details")
    private String contactDetails;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "phone")
    private String phone;

    @Column(name = "pre_phone_no")
    private String prePhoneNo;

    @Column(name = "pre_mobile_no")
    private String preMobileNo;

    @Column(name = "status")
    private String status;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "pickup_place_or_zipcode")
    private String pickupPlaceOrZipcode;

}