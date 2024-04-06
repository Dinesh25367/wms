package com.newage.wms.service;

import com.newage.wms.entity.TrnHeaderAddlDetails;

public interface TrnHeaderAddlDetailsService {

    TrnHeaderAddlDetails getById(Long id);

    void deleteAllInIterable(Iterable<TrnHeaderAddlDetails> trnHeaderAddlDetailsIterable);

}
