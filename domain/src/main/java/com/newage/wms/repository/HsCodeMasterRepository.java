package com.newage.wms.repository;

import com.newage.wms.entity.HsCodeMaster;
import com.newage.wms.entity.QHsCodeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface HsCodeMasterRepository extends JpaRepository<HsCodeMaster,Long>,
        QuerydslPredicateExecutor<HsCodeMaster>, QuerydslBinderCustomizer<QHsCodeMaster> {

    HsCodeMaster getByCode(String code);
    @Override
    default void customize(QuerydslBindings bindings, QHsCodeMaster qHsCodeMaster){

    }

}