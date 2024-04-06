package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Where(clause = "deleted <> true ")
@Getter
@Setter
@Table(name = "efs_address_type_master")
@org.hibernate.annotations.Cache(region = "addressTypeCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AddressTypeMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(max = 25)
    @Column(name = "address_Type", nullable = false, length = 25)
    String addressType;

    @NotNull(message = "Accepted Values (Active, InActive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Column(name = "deleted")
    boolean deleted = false;

}
