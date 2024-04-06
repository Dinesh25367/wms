package com.newage.wms.repository;

import com.newage.wms.entity.BranchMaster;
import com.newage.wms.entity.QBranchMaster;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface BranchMasterRepository extends JpaRepository<BranchMaster, Long>,
        QuerydslPredicateExecutor<BranchMaster>, QuerydslBinderCustomizer<QBranchMaster> {

    @Query(" select id from BranchMaster bm where bm.id = (select max(id) from BranchMaster)\n")
    Long findMaxId ();

    boolean existsByName ( String name );
    boolean existsByCode ( String code );
    boolean existsByReportingName ( String reportingName );

    @Query("select bm from BranchMaster bm where bm.agentMaster.id=:agentId")
    BranchMaster findByLineItem ( Long agentId );

    default void customize (QuerydslBindings bindings, QBranchMaster root ) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.companyMaster.id);
        bindings.including(root.companyMaster.name);
        bindings.including(root.companyMaster.code);
        bindings.including(root.companyMaster.groupCompany.id);
        bindings.including(root.companyMaster.groupCompany.name);
        bindings.including(root.companyMaster.groupCompany.code);
        bindings.including(root.agentMaster.id);
        bindings.including(root.agentMaster.name);
        bindings.including(root.name);
        bindings.including(root.reportingName);
        bindings.bind(String.class).first((StringPath path, String value ) ->
                path.containsIgnoreCase(value));
    }

}

