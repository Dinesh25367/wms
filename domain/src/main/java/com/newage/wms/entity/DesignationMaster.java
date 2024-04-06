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
@Getter
@Setter
@Table(name = "efs_designation_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_DESIGNATION_CODE"),
        @UniqueConstraint(columnNames = {"code", "updated_date"}, name = "UK_DESIGNATION_NAME"),})
public class DesignationMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @NotEmpty(message = "code must not be Empty")
    @Size(min = 1, max = 10, message = "Code size must be between 1 and 10")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Special characters are not allowed")
    @Column(name = "code", nullable = false, length = 10)
    String code;

    @NotNull
    @NotEmpty(message = "name must not be Empty")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100")
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @NotNull(message = "Accepted Values (Active, Disabled, InActive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Size(max = 500, message = "Note must be between 0 and 500")
    @Column(name = "note", length = 500)
    String note;

    @NotNull(message = "Accepted Values (Internal, External)")
    @Column(name = "type", nullable = false, length = 15)
    private String type;

    @ManyToOne
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "FK_DESIGNATION_DEPARTMENT_ID"))
    DepartmentMaster department;

    @Column(name = "deleted")
    boolean deleted = false;

}
