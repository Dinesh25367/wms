package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Where(clause = "deleted <> true ")
@Getter
@Setter
@Table(name = "efs_department_master", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_DEPARTMENT_DEPTNAME")})
public class DepartmentMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "code", nullable = false, length = 10)
    String code;

    @Column(name = "name", nullable = false, length = 50)
    String name;

    @NotNull(message = "Accepted Values (Active, Disabled, Inactive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Column(name = "note", length = 500)
    String note;

    @NotNull(message = "Accepted Values (Internal, External)")
    @Column(name = "type", nullable = false, length = 15)
    private String type;

    @Version
    @Column(name = "version")
    Long version;

    @Column(name = "deleted")
    boolean deleted = false;

}
