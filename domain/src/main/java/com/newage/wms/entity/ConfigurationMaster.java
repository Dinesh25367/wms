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
@Table(name = "wms_configuration_master",schema = "tenant_default")
public class ConfigurationMaster extends Auditable<String>{

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

    @Column(name = "cancel_rights", length = 30)
    private String cancelRights;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID"),nullable = false)
    private AuthUserProfile authUserProfile;

    @Column(name = "module", length = 30, nullable = false)
    private String module;

    @Column(name = "screen", length = 30)
    private String screen;

    @Column(name = "tab", length = 30)
    private String tab;

    @Column(name = "configuration_flag", length = 50, nullable = false)
    private String configurationFlag;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "value", length = 5000,nullable = false)
    private String value;

    @Column(name = "data_type", nullable = false)
    private String dataType;

    @Column(name = "note", length = 5000)
    private String note;

    @Column(name = "status", length = 30)
    private String status;

}
