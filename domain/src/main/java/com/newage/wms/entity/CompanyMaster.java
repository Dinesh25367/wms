package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Where(clause = "deleted <> true ")
@Table(name = "efs_company_master", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code","updated_date"}, name = "UK_COMPANY_CODE"),
                @UniqueConstraint(columnNames = {"name","updated_date"}, name = "UK_COMPANY_NAME"),
                @UniqueConstraint(columnNames = {"reporting_name","updated_date"}, name = "UK_COMPANY_REPORTINGNAME")})
public class CompanyMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Size(min = 2, max = 5)
    @Pattern(regexp = "[a-zA-Z]*", message = "Code has invalid characters")
    @Column(name = "code", nullable = false, length = 5)
    String code;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @NotNull
    @Column(name = "reporting_Name", nullable = false, length = 25)
    String reportingName;

    @Column(name = "logo")
    byte[] logo;

    @NotNull(message = "Accepted Values (Active, Disabled, Inactive)")
    @Column(name = "status", nullable = false, length = 10)
    String status;

    @Column(name = "financial_year")
    String financialYear;

    @Column(name = "start_month")
    String startMonth;

    @Column(name = "end_month")
    String endMonth;

    @Version
    @Column(name = "version")
    Long version;

    @Column(name = "deleted")
    boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "group_company_id", foreignKey = @ForeignKey(name = "FK_COMPANY_GROUPCOMPANYID"))
    GroupCompanyMaster groupCompany;

}
