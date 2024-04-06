package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "efs_time_zone_master")
@org.hibernate.annotations.Cache(region = "timeZone", usage = CacheConcurrencyStrategy.READ_WRITE)
public class TimeZoneMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "zone_name")
    private String zoneName;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "std")
    private String std;

    @Column(name = "dst")
    private String dst;

    @NotNull(message = "Accepted Values (Active, InActive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

}