package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Where(clause = "deleted <> true ")
@Getter
@Setter
@Table(name = "efs_event_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "updated_date"}, name = "UK_EVENT_CODE"),
        @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_EVENT_NAME")})
public class EventMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Special characters are not allowed")
    @Column(name = "code", nullable = false, length = 10)
    String code;

    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @ManyToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_EVENT_MASTER_COUNTRY_ID"))
    CountryMaster country;

    @NotNull(message = "Accepted Values (Active, Inactive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Column(name = "note", length = 500)
    String note;

    @Column(name = "deleted")
    boolean deleted = false;

}
