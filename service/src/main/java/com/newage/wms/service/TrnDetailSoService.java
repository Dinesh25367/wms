package com.newage.wms.service;


import com.newage.wms.entity.TrnDetailSo;


public interface TrnDetailSoService {

    TrnDetailSo getById(Long id);

    void deleteAllInIterable(Iterable<TrnDetailSo> trnDetailSoIterable);

    String generateTransactionUid();

    Boolean isBackOrder(Long Id);

    void deleteById(Long id);
}
