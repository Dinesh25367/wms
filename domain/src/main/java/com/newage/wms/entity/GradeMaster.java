package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Where(clause = "deleted <> true ")
@Getter
@Setter
@Table(name = "efs_grade_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"priority","updated_date"}, name = "UK_GRADE_PRIORITY")})
public class GradeMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 2, max = 20)
    @Column(name = "name", length = 20)
    String name;

    @NotNull(message = "Accepted Values (One, Two, Three, Four, Five)")
    @Column(name = "priority", nullable = false, length = 10)
    private String priority;

    @Column(name = "colour_code", length = 100)
    String colourCode;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}
