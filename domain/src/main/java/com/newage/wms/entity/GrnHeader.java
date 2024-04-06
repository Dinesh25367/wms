package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wms_grn_header",schema = "tenant_default")
public class GrnHeader extends Auditable<String>{

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", foreignKey = @ForeignKey(name = "FK_TRANSACTION_ID"),nullable = false)
    private TrnHeaderAsn trnHeaderAsnMaster;

    @Column(name = "transaction_uid", length = 30, nullable = false)
    private String transactionUid;

    @Column(name = "grn_ref")
    private String grnRef;

    @Column(name = "grn_date")
    private Date grnDate;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "grnHeader", cascade = CascadeType.ALL)
    private List<GrnDetail> grnDetailList;

}
