package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Where(clause = "deleted <> true ")
@Setter
@Getter
@Table(name = "efs_hs_code_master",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"country_id", "code", "updated_date"}, name = "UK_HS_CODE"),
                @UniqueConstraint(columnNames = {"country_id", "name", "updated_date"}, name = "UK_HS_NAME")}
)
public class HsCodeMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[0-9]*$", message = "Only numeric value is allowed")
    @Size(min = 2, max = 10, message = "Code should be less than 10 character length")
    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @NotNull
    @NotEmpty
    @Size(max = 500, message = "Name length must be 0 to 500 characters")
    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "FK_HS_CODE_COUNTRY_ID"))
    private CountryMaster country;

    @Size(max = 100, message = "Size must be between 0 to 100 characters")
    @Column(name = "industry", nullable = false, length = 100)
    private String industry;

    @NotNull(message = "Accepted Values (Active, Disabled, InActive)")
    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "note", nullable = false, length = 500)
    private String note;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    private Long version;

}
