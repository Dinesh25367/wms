package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Where(clause = "deleted <> true ")
@Table(name = "efs_customer_and_service")
public class CustomerEmployeeService extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_master_employee_id", foreignKey = @ForeignKey(name = "CUSTOMER_MASTER_EMPLOYEE_ID"))
    private CustomerMasterEmployee customerMasterEmployee;

    @OneToOne
    @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "CUSTOMER_EMPLOYEE_SERVICE_ID"))
    private ServiceMaster service;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}
