package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = " deleted <> true")
@Getter
@Setter
@Table(name = "efs_carrier_master", schema = "tenant_default", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "updated_date"}, name = "UK_CARRIER_CODE"),
        @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_CARRIER_NAME")})
public class CarrierMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "Accepted Values (Air, Ocean)")
    @Column(name = "transport_mode", nullable = false, length = 10)
    private String transportMode;

    @NotNull(message = "Only 200 character will accept")
    @Column(name = "name", length = 200)
    String name;

    @NotNull(message = "Special characters are not allowed")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Special characters are not allowed")
    @Column(name = "code", length = 8)
    String code;

    @OneToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_CARRIER_COUNTRY"))
    CountryMaster country;

    @NotNull(message = "only numbers are allowed")
    @Column(name = "carrier_prefix", length = 3)
    String carrierPrefix;


    @Column(name = "scac_code", length = 10)
    String scacCode;

    @NotNull(message = "Accepted Values (Yes, No)")
    @Column(name = "local_carrier", nullable = false, length = 10)
    private String localCarrier;

    @NotNull(message = "Accepted Values (Active, Disabled, InActive)")
    @Column(name = "active_status", nullable = false, length = 20)
    String status;

    @Column(name = "image")
    byte[] image;

    @OneToMany(mappedBy = "carrierMaster", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CarrierMasterContact> contacts = new ArrayList<>();

    @Column(name = "deleted")
    boolean deleted = false;

}
