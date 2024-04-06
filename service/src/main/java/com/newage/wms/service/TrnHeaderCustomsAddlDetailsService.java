package com.newage.wms.service;

import com.newage.wms.entity.TrnHeaderCustomsAddlDetails;

public interface TrnHeaderCustomsAddlDetailsService {

    TrnHeaderCustomsAddlDetails getById(Long id);

    void deleteAllInIterable(Iterable<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsIterable);

}
