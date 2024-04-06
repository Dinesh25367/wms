package com.newage.wms.service;

import com.newage.wms.entity.Location;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface LocationService {

    Page<Location> findAll(Predicate predicate, Pageable pageable);

    List<Location> saveAll(List<Location> locationList);

    Location saveLocation(Location location);

    Location updateLocation(Location location);

    Location getLocationById(Long id);

    List<Location> findAll();

}
