package com.newage.wms.repository;

import com.newage.wms.entity.GrnDetail;
import com.newage.wms.entity.QGrnDetail;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface GrnDetailRepository extends JpaRepository<GrnDetail,Long>,
        QuerydslPredicateExecutor<GrnDetail>, QuerydslBinderCustomizer<QGrnDetail> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QGrnDetail qGrnDetail){
        querydslBindings.bind(String.class).first((StringPath path, String value) -> {
            return path.startsWithIgnoreCase(value);
        });
    }

}
