package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Where(clause = "deleted <> true")
@Getter
@Setter
@Table(name = "efs_tos_master",schema = "tenant_default", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "updated_date"}, name = "UK_TOS_CODE"),
        @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_TOS_NAME")})
public class TosMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Size(min = 2, max = 3)
    @Pattern(regexp = "[a-z-A-Z ]*", message = "Code has invalid characters")
    @Column(name = "code", nullable = false, length = 3)
    String code;

    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "[a-z-A-Z ]*", message = "Name has invalid characters")
    @Column(name = "name", nullable = false, length = 50)
    String name;

    @NotNull(message = "Accepted Values (Prepaid, Collect)")
    @Column(name = "freight_ppcc", nullable = false, length = 10)
    private String freightPPCC;

    @Column(name = "note", length = 500)
    String note;

    @NotNull(message = "Accepted Values (Active, Disabled, InActive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

    @Column(name = "ranking")
    Long ranking;

}
