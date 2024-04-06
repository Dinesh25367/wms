package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "efs_group_company_master", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code"}, name = "UK_GROUPCOMPANNY_CODE"),
                @UniqueConstraint(columnNames = {"name"}, name = "UK_GROUPCOMPANNY_NAME"),
                @UniqueConstraint(columnNames = {"reporting_name"},name = "UK_GROUPCOMPANY_REPORTINGNAME")})
public class GroupCompanyMaster extends Auditable<String>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Size(min=1, max = 5, message ="Code size must be between 1 and 5")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Special characters are not allowed")
    @Column(name = "code", nullable = false, length = 5)
    String code;

    @NotNull
    @Size(min=1, max = 100, message ="Name size must be between 1 and 100")
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @NotNull
    @Size(min=1, max = 25, message ="ReportingName size must be between 1 and 25")
    @Column(name = "reporting_Name",nullable = false,length = 25)
    String reportingName;

    @Column(name = "logo")
    byte[] logo;

    @Column(name = "status", nullable = false, length = 10)
    String status;

    @OneToMany(mappedBy = "groupCompany" , cascade = CascadeType.ALL)
    private List<CompanyMaster> companyDetails = new ArrayList<>();

}
