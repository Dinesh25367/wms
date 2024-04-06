package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="wms_warehouse_master",schema = "tenant_default",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "UK_CODE"),
        @UniqueConstraint(columnNames = {"warehouse_id"}, name = "UK_WAREHOUSE_ID")})
public class WareHouseMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "warehouse_id",length = 30,nullable = false)
    private String wareHouseId;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "group_company_id", nullable = false)
    private Long groupCompanyId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_BRANCH_ID"))
    private BranchMaster branchMaster;

    @Column(name = "code", length = 10,nullable = false)
    private String code;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "status", length = 10, nullable = false)
    private String status;

    @Column(name = "note", length = 1000)
    private String note;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_address_id",foreignKey = @ForeignKey(name = "FK_WAREHOUSE_PARTY_ADDRESS_ID"))
    private PartyAddressDetail partyAddressDetail;

    @Column(name = "open_date")
    private Date openDate;

    @Column(name = "is_bonded", length = 1, nullable = false)
    private Character isBonded;

    @Column(name = "is_third_party_warehouse", length = 1, nullable = false)
    private Character isThirdPartyWareHouse;

    @ManyToOne
    @JoinColumn(name = "cross_dock_location_id", foreignKey = @ForeignKey(name = "FK_CROSS_DOCK_LOCATION_ID"))
    private Location crossDockLocationMaster;


    @Column(name = "warehouse_location_prefix", length = 10)
    private String wareHouseLocationPrefix;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wareHouse")
    private WareHouseContactDetail wareHouseContactDetail;

}
