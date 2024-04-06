package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "efs_country_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "UK_COUNTRY_CODE"),
        @UniqueConstraint(columnNames = {"name"}, name = "UK_COUNTRY_NAME")})
@org.hibernate.annotations.Cache(region = "countryCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CountryMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "code", nullable = false, length = 2)
    private String code;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "iso3", nullable = false, length = 3)
    private String iso3;

    @Column(name = "capital")
    private String capital;

    @Column(name = "native")
    private String nativeName;

    @Column(name = "flag")
    private String flag;

    @Column(name = "flag_unicode")
    private String flagUnicode;

    @Column(name = "phone_code")
    private String phoneCode;

    @Column(name = "currency_code")
    private String currencyCode;

    @OneToOne(mappedBy = "country")
    private CurrencyMaster currency;

    @NotNull(message = "Accepted Values (Active, Disabled, Inactive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    private Set<StateMaster> states;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    private Set<CityMaster> cities;

    @Version
    @Column(name = "version")
    Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", foreignKey = @ForeignKey(name = "FK_COUNTRY_REGION_ID"))
    RegionMaster region;

}
