package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@Where(clause = "deleted <> true")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "efs_vessel_master",schema = "tenant_default",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_VESSEL_MASTER_NAME"),
                @UniqueConstraint(columnNames = {"short_name", "updated_date"}, name = "UK_VESSEL_MASTER_SHORT_NAME")})
public class VesselMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @NotEmpty(message = "Name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Special characters are not allowed.")
    @Column(name = "name", nullable = false, length = 50)
    String name;

    @NotNull
    @NotEmpty(message = "Short Name should not be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Special characters are not allowed.")
    @Column(name = "short_name", nullable = false, length = 10)
    String shortName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vessel_country_id", foreignKey = @ForeignKey(name = "FK_VESSEL_COUNTRY_ID"))
    CountryMaster vesselCountry;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Special characters are not allowed.")
    @Column(name = "call_sign", length = 10)
    String callSign;

    @Column(name = "IMO_number", length = 10)
    String imoNumber;

    @Column(name = "note", length = 500)
    String note;

    @NotNull(message = "Accepted values are Active and Inactive")
    @Column(name = "status")
    String status;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}
