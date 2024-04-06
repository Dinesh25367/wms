package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Where(clause = "deleted <> true ")
@Table(name = "efs_branch_master", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code", "updated_date"}, name = "UK_BRANCH_BRANCHCODE"),
                @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_BRANCH_BRANCHNAME"),
                @UniqueConstraint(columnNames = {"reporting_name", "updated_date"}, name = "UK_BRANCH_REPORTINGNAME")})
public class BranchMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 10)
    @Size(min = 1, max = 5, message = "Branch code size must be between 1 and 5")
    private String code;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(name = "reporting_Name", nullable = false, length = 25)
    private String reportingName;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_BRANCH_COMPANYID"))
    private CompanyMaster companyMaster;

    @ManyToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_BRANCH_COUNTRYID"))
    private CountryMaster countryMaster;

    @ManyToOne
    @JoinColumn(name = "state_id", foreignKey = @ForeignKey(name = "FK_BRANCH_STATE"))
    private StateMaster stateMaster;

    @ManyToOne
    @JoinColumn(name = "city_id", foreignKey = @ForeignKey(name = "FK_BRANCH_CITYID"))
    private CityMaster cityMaster;

    @ManyToOne
    @JoinColumn(name = "zone_id", foreignKey = @ForeignKey(name = "FK_BRANCH_ZONEID"))
    private ZoneMaster zoneMaster;

    @ManyToOne
    @JoinColumn(name = "agent_id", foreignKey = @ForeignKey(name = "FK_BRANCH_AGENTID"))
    CustomerMaster agentMaster;

    @ManyToOne
    @JoinColumn(name = "time_zone_id", foreignKey = @ForeignKey(name = "FK_BRANCH_TIME_ZONEID"))
    TimeZoneMaster timeZoneMaster;

    @Column(name = "date_format", nullable = false, length = 30)
    private String dateFormat;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @OneToMany(mappedBy = "branchMaster", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BranchAddressMaster> branchAddressMasterList;

    @ManyToOne
    @JoinColumn(name = "currency_id", foreignKey = @ForeignKey(name = "FK_BRANCH_CURRENCYID"))
    private CurrencyMaster currencyMaster;

    @OneToMany(mappedBy = "branchMaster", cascade = CascadeType.ALL)
    private List<BranchPortMaster> portMaster = new ArrayList<>();

    @Column(name = "gst_no")
    private String gstNumber;

    @Column(name = "vat_no")
    private String vatNumber;

    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "transport_name", length = 30)
    private String transportName;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Version
    @Column(name = "version")
    private long version;

}

