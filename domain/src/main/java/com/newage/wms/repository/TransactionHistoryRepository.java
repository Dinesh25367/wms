package com.newage.wms.repository;

import com.newage.wms.entity.QTransactionHistory;
import com.newage.wms.entity.TransactionHistory;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Long>,
        QuerydslPredicateExecutor<TransactionHistory>, QuerydslBinderCustomizer<QTransactionHistory>  {

    @Override
    default void customize(QuerydslBindings bindings, QTransactionHistory qTransactionHistory){
        bindings.bind(String.class).first((StringPath path, String value) -> {
            return path.startsWithIgnoreCase(value);
        });
        bindings.bind(qTransactionHistory.transactionStatus).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
