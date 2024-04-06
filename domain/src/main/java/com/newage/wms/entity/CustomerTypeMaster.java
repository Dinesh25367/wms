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
@Table(name = "efs_customer_type_master", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code", "updated_date"}, name = "UK_CUSTOMER_TYPE_CODE"),
                @UniqueConstraint(columnNames = {"name", "updated_date"}, name = "UK_CUSTOMER_TYPE_NAME")})
public class CustomerTypeMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Size(min = 1, max = 3, message = "Size must be between 1 and 3")
    @Column(name = "code", nullable = false, length = 3)
    String code;

    @NotNull
    @Size(min = 2, max = 30, message = "Length must be between 2 to 30")
    @Column(name = "name", nullable = false, length = 30)
    String name;

    @NotNull(message = "Accepted Values (Active, InActive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}
