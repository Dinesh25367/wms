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
@Table(name="efs_auth_user_profile",schema = "tenant_default")
public class AuthUserProfile extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    @Column(name = "user_name", nullable = false, length = 25)
    private String userName;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Column(name = "organisation_acess", nullable = false, length = 10)
    private String organisationAccess;

    @Column(name = "primary_company_id")
    private Long primaryCompanyId;

    @Column(name = "primary_company_name", length = 100)
    private String primaryCompanyName;

    @Column(name = "primary_login_branch_id")
    private Long primaryLoginBranchId;

    @Column(name = "primary_login_branch_name", length = 50)
    private String primaryLoginBranchName;

    @Column(name = "primary_email_id", nullable = false, length = 200)
    private String primaryEmailId;

    @Column(name = "employ_mapping_type", length = 12)
    private String employMappingType;

    @Column(name = "primary_branch_employee_id")
    private Long primaryBranchEmployeeId;

    @Column(name = "primary_branch_employee_name", length = 50)
    private String primaryBranchEmployeeName;

    @Column(name = "currency_code", length = 45)
    private String currencyCode;

    @Column(name = "primary_role_id")
    private Long primaryRoleId;

}