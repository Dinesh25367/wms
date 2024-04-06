package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "efs_city_master", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id", "state_id", "country_id"}, name = "UK_COUNTRY_STATE_CITY")})
@org.hibernate.annotations.Cache(region = "cityCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CityMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @NotEmpty
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "country_id", foreignKey  = @ForeignKey(name = "FK_CITY_COUNTRY_ID"))
    CountryMaster country;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "state_id", foreignKey = @ForeignKey(name = "FK_CITY_STATE_ID"))
    StateMaster state;

    @NotNull(message = "Accepted Values (Active, Block, Hide)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Version
    @Column(name = "version")
    Long version;

}