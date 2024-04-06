package com.newage.wms.service;

import com.newage.wms.entity.GrnDetail;
import com.newage.wms.entity.GrnHeader;
import com.newage.wms.entity.TrnDetail;
import java.util.List;

public interface GrnDetailService {

    List<GrnDetail> findAllGrnDetailForTransactionIdAndTransactionDetailId(Long grnId,Long transactionDetailId);

    List<GrnDetail> findAllGrnDetailForGrnId(Long grnId);

    boolean checkIfDetailIdForDeletable(Long id);

    double getAllCompletedQtyForOneDetail(TrnDetail trnDetail,GrnHeader grnHeader,boolean isActualQty);

    void deleteAllIterables(Iterable<GrnDetail> grnDetailIterable);

    List<GrnDetail> findAll(Long grnHeaderId);

    GrnDetail getById(Long id);

    void deleteAllInIterable(List<GrnDetail> grnDetailListToBeDeleted);
}
