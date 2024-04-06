package com.newage.wms.service;

import com.newage.wms.entity.TrnHeaderTransportation;

public interface TrnHeaderTransportationService {

    TrnHeaderTransportation getById(Long id);

    void deleteAllInIterable(Iterable<TrnHeaderTransportation> trnHeaderTransportationIterable);

}
