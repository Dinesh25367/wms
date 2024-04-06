package com.newage.wms.service;

import com.newage.wms.entity.TrnDetail;

public interface TrnDetailService {

    TrnDetail save(TrnDetail trnDetail);

    TrnDetail getById(Long id);

    void deleteAllInIterable(Iterable<TrnDetail> trnDetailIterable);

    Integer findMaxTransactionSerialNo(Long transactionId);

    Boolean getSkuEditFlag(Long id);

}
