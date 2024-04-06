package com.newage.wms.repository;

import com.newage.wms.entity.QTrnDetailSo;
import com.newage.wms.entity.TrnDetailSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository

public interface TrnDetailSoRepository extends JpaRepository<TrnDetailSo,Long>,
        QuerydslPredicateExecutor<TrnDetailSo>, QuerydslBinderCustomizer<QTrnDetailSo>  {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnDetailSo qTrnDetailSo){

    }
}
