package com.newage.wms.repository;

        import com.newage.wms.entity.QTrnDetail;
        import com.newage.wms.entity.TrnDetail;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.querydsl.QuerydslPredicateExecutor;
        import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
        import org.springframework.data.querydsl.binding.QuerydslBindings;
        import org.springframework.stereotype.Repository;

@Repository
public interface TrnDetailRepository extends JpaRepository<TrnDetail,Long>,
        QuerydslPredicateExecutor<TrnDetail>, QuerydslBinderCustomizer<QTrnDetail> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnDetail qTrnDetail){

    }

}