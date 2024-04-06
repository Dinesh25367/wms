package com.newage.wms.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wms_putaway_task_header",schema = "tenant_default")

public class PutAwayTaskHeader extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
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

    @OneToMany(mappedBy = "putAwayTaskHeader",cascade = CascadeType.ALL)
    private List<PutAwayTaskDetails> putAwayTaskDetailsList;

    @ManyToOne
    @JoinColumn(name = "grn_id", foreignKey = @ForeignKey(name = "FK_GRN_HEADER_ID"), nullable = false)
    private GrnHeader grnHeader;


    @Column(name = "task_id", length = 30, nullable = false)
    private String taskId;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "note")
    private String note;


}
