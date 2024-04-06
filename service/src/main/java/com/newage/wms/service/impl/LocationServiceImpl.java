package com.newage.wms.service.impl;

import com.newage.wms.entity.Location;
import com.newage.wms.repository.LocationRepository;
import com.newage.wms.service.LocationService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Log4j2
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    /*
     * Method to get all Location with pagination, sort and filter
     * @return Location Page
     */
    @Override
    public Page<Location> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all Location with pagination, sort and filter");
        return locationRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save all Location
     * @return List<Location>
     */
    @Override
    public List<Location> saveAll(List<Location> locationList) {
        log.info("ENTRY-EXIT - Save all Location");
        return locationRepository.saveAll(locationList);
    }

    /*
     * Method to save new Location
     * @return Location
     */
    @Override
    public Location saveLocation(Location location) {
        log.info("ENTRY-EXIT - Save Location");
        return locationRepository.save(location);
    }

    /*
     * Method to update Location
     * @return Location
     */
    @Override
    public Location updateLocation(Location location) {
        log.info("ENTRY-EXIT - Update Location");
        return locationRepository.save(location);
    }

    /*
     * Method to get Location by id
     * @return Location
     */
    @Override
    public Location getLocationById(Long id) {
        log.info("ENTRY-EXIT - Get Location by Id");
        return locationRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.LOCATION_ID_NOT_FOUND.CODE,
                        ServiceErrors.LOCATION_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all Location for autocomplete
     * @return List<Location>
     */
    @Override
    public List<Location> findAll() {
        log.info("ENTRY-EXIT - Get all Location");
        return locationRepository.findAll();
    }

}
