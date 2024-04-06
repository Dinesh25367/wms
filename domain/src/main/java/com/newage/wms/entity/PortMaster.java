package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "efs_port_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code","updated_date"}, name = "UK_PORT_CODE"),
        @UniqueConstraint(columnNames = {"name","transport_mode","updated_date"}, name = "UK_PORT_NAME")})
@org.hibernate.annotations.Cache(region = "portCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Version
    @Column(name = "version")
    Long version;

    @NotNull
    @Size(min = 2, max = 3)
    @Column(name = "code", nullable = false, length = 3)
    String code;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @NotNull(message = "Accepted Values (Active, Disabled, Inactive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @NotNull
    @Column(name = "transport_mode", nullable = false, length = 10)
    String transportMode;

    @NotNull
    @Column(name = "country_code", nullable = false, length = 3)
    String countryCode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_PORT_COUNTRY_ID"))
    CountryMaster country;

}
