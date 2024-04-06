package com.newage.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted <> true ")
@Setter
@Getter
@Entity
@Table(name = "efs_customer_employee_mapping")
public class CustomerMasterEmployee extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "CUSTOMER_EMPLOYEE_MAPPING_CUSTOMER_SERVICE_ID"))
    private DepartmentMaster department;

    @NotNull(message = "Service Id is Required")
    @OneToMany(mappedBy = "customerMasterEmployee", cascade = CascadeType.ALL)
    private List<CustomerEmployeeService> service = new ArrayList<>();

    @Column(name = "all_service")
    Boolean allService = false;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "CUSTOMER_EMPLOYEE_MAPPING_ID"))
    @JsonBackReference
    private CustomerMaster customer;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @JoinColumn(name = "version")
    private Long version;

}