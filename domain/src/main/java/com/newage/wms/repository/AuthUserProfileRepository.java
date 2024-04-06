package com.newage.wms.repository;

import com.newage.wms.entity.AuthUserProfile;
import com.newage.wms.entity.QAuthUserProfile;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserProfileRepository extends JpaRepository<AuthUserProfile, Long>,
        QuerydslPredicateExecutor<AuthUserProfile>, QuerydslBinderCustomizer<QAuthUserProfile> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QAuthUserProfile qAuthUserProfile){
        querydslBindings.bind(qAuthUserProfile.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
