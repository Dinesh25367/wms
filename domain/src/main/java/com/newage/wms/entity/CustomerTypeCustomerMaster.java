package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Entity
@Where(clause = "deleted <> true")
@Getter
@Setter
@Table(name = "customer_type_customer_master")
public class CustomerTypeCustomerMaster extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_RESPONSE_CUSTOMERTYPEID"))
    CustomerMaster customer;

    @OneToOne
    @JoinColumn(name = "customer_type_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_TYPE_MASTER"))
    CustomerTypeMaster customerType;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}
