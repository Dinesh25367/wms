package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Entity
@Where(clause = "deleted <> true ")
@Getter
@Setter
@Table(name = "efs_zone_master", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code", "updated_date"}, name = "UK_ZONE_CODE"),
                @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_ZONE_NAME")})
@org.hibernate.annotations.Cache(region = "zoneCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZoneMaster extends Auditable<String>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "code", nullable = false, length = 10)
    String code;

    @Column(name = "name", nullable = false, length = 50)
    String name;

    @ManyToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_ZONE_COUNTRY_ID"))
    CountryMaster country;

    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Column(name = "note", length = 500)
    String note;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}
