package com.newage.wms.repository;

import com.newage.wms.entity.GrnLotDetail;
import com.newage.wms.entity.QGrnLotDetail;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface GrnLotDetailRepository extends JpaRepository<GrnLotDetail,Long>,
        QuerydslPredicateExecutor<GrnLotDetail>, QuerydslBinderCustomizer<QGrnLotDetail> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QGrnLotDetail qGrnLotDetail){
        querydslBindings.bind(String.class).first((StringPath path, String value) -> {
            return path.startsWithIgnoreCase(value);
        });
    }
}
