package com.newage.wms.service;

import com.newage.wms.entity.GrnHeader;
import com.newage.wms.entity.TransactionHistory;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface TransactionHistoryService {

    List<TransactionHistory> findAll(Predicate predicate,Pageable pageable);

    List<TransactionHistory> save(List<TransactionHistory> transactionHistory);

    String generateTaskId();

    List<TransactionHistory> filteredByGrnDetailId(Long grnDetailId);

    List<TransactionHistory> findByGrnDetailId( Long grnDetailId);

    Optional<TransactionHistory> getPutAwayTaskForGRNDetailIfExists(Long grnDetailId);

    Boolean putAwayCancelRights(Long userId);

    String getPutAwayStatus (GrnHeader grnHeader);

    Double getPutAwayQty(Long grnDetailId);

    Double getPutAwayVolume(Long grnDetailId);

    Double getPutAwayWeight(Long grnDetailId);

    String checkIfAtLeastOneGrnDetailIsGenerated(GrnHeader grnHeader);

    List<TransactionHistory> findFilteredData(Long grnDetailId, Integer taskSlNo);

    boolean isPopUpRights(Long grnDetailId);
}
