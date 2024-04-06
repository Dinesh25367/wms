package com.newage.wms.service;

import com.newage.wms.entity.VesselMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VesselMasterService {

      Page<VesselMaster> getAllVessels(Predicate predicate, Pageable pageRequest);

      VesselMaster getVesselById(Long id);

      VesselMaster createVessel(VesselMaster vesselMaster);

      VesselMaster updateVessel(VesselMaster vesselMaster);

      void validateNewName(String name);

      void validateNewShortName(String shortName);

      void validateNewCallSign(String callSign);

      void validateNewIMO(String imoNumber);

      void deleteVessel(VesselMaster vesselMaster);

      Iterable<VesselMaster> getAllAutoComplete(Predicate combinedPredicate, Pageable pageable);

}
