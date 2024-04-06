package com.newage.wms.service;

import com.newage.wms.entity.TosMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TosMasterService {

      TosMaster getTosById(Long id);

      TosMaster createTos(TosMaster tosMaster);

      TosMaster updateTos(TosMaster tosMaster);

      void deleteTos(TosMaster tosMaster);

      Page<TosMaster> getAllTos(Predicate predicate, Pageable pageRequest, String name, String code);

      void validateNewTosCode(String code);

      void validateNewTosName(String name);

      Iterable<TosMaster> getAllAutoComplete(Predicate predicate, Pageable pageable);

}
