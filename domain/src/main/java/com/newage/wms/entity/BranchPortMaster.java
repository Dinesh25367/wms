package com.newage.wms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Where(clause = "deleted <> true ")
@Table(name = "efs_branch_port_master")
@org.hibernate.annotations.Cache(region = "branchPortCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class BranchPortMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_RESPONSE_PORT_ID"))
    BranchMaster branchMaster;

    @OneToOne
    @JoinColumn(name = "port_id", foreignKey = @ForeignKey(name = "FK_BRANCH_PORT_ID"))
    PortMaster portMaster;

    @Column(name = "deleted")
    boolean deleted = false;

    @Version
    @Column(name = "version")
    Long version;

}
