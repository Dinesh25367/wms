package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wms_customer_configuration_master",schema = "tenant_default")
public class CustomerConfigurationMaster extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "group_company_id", foreignKey = @ForeignKey(name = "FK_GROUP_COMP_ID"),nullable = false)
    private GroupCompanyMaster groupCompanyMaster;

    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_COMP_ID"),nullable = false)
    private CompanyMaster companyMaster;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_BRANCH_ID"),nullable = false)
    private BranchMaster branchMaster;

    @Column(name = "module", length = 30, nullable = false)
    private String module;

    @ManyToOne
    @JoinColumn(name = "customer_id",foreignKey = @ForeignKey(name = "FK_CUSTOMER_ID"),nullable = false)
    private CustomerMaster customerMaster;

    @Column(name = "configuration_flag", length = 50, nullable = false)
    private String configurationFlag;

    @Column(name = "value", length = 5000,nullable = false)
    private String value;

    @Column(name = "data_type",nullable = false)
    private String dataType;

    @Column(name = "is_mandatory", length = 15, nullable = false)
    private String isMandatory;

    @Column(name = "note", length = 5000)
    private String note;

}
