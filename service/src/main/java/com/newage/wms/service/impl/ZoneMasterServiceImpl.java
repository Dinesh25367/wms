package com.newage.wms.service.impl;

import com.newage.wms.entity.ZoneMaster;
import com.newage.wms.repository.ZoneMasterRepository;
import com.newage.wms.service.ZoneMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Log4j2
@Service
@Transactional
public class ZoneMasterServiceImpl implements ZoneMasterService {

    private final ZoneMasterRepository zoneMasterRepository;

    @Autowired
    public ZoneMasterServiceImpl(ZoneMasterRepository zoneMasterRepository) {
        this.zoneMasterRepository = zoneMasterRepository;
    }

    /*
     * Method to get all zones
     * @Return zoneMaster
      */
    @Override
    public Page<ZoneMaster> getAllZones(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all Zones");
        return zoneMasterRepository.findAll(predicate, pageRequest);
    }

    /*
    * Method to validate new code
    */
    @Override
    public void validateNewCode(String code) {
        log.info("ENTRY-EXIT - validate new Code");
        if(zoneMasterRepository.existsByCode(code)){
            throw new ServiceException(ServiceErrors.ZONE_CODE_ALREADY_EXIST.CODE);
        }
    }

    /*
    *Method  to validate new name
    */
    @Override
    public void validateNewName(String name) {
        log.info("ENTRY-EXIT - validate new name");
        if(zoneMasterRepository.existsByName(name)){
            throw new ServiceException(ServiceErrors.ZONE_NAME_ALREADY_EXIST.CODE);
        }
    }

    /*
    *Method to get zone by Id
    * @Return zoneMaster
    */
    @Override
    public ZoneMaster getZoneById(Long id) {
        log.info("ENTRY-EXIT - get Zone by Id");
        return zoneMasterRepository.findById(id).
                orElseThrow(() ->  new ServiceException(ServiceErrors.ZONE_ID_NOT_FOUND.CODE));
    }

    /*
    *Method to fetch zone by country Id
    * @Return Country Id
    */
    @Override
    public List<ZoneMaster> getZoneByCountryId(Long id) {
        log.info("ENTRY-EXIT - get Zone by Country Id");
        return zoneMasterRepository.findByCountryId(id);
    }

    /*
    *Method to create new zone
    * @Return zoneMaster
    */
    @Override
    public ZoneMaster createZone(ZoneMaster zoneMaster) {
        log.info("ENTRY-EXIT - create Zone");
        return zoneMasterRepository.save(zoneMaster);
    }

    /*
    *Method to update zone
    * @Return ZoneMaster
    */
    @Override
    public ZoneMaster updateZone(ZoneMaster zoneMaster) {
        log.info("ENTRY-EXIT - update Zone");
        return zoneMasterRepository.save(zoneMaster);
    }

    /*
    *Method to delete zone
    */
    @Override
    public void deleteZone(ZoneMaster zoneMaster) {
        log.info("ENTRY-EXIT - delete Zone");
        zoneMasterRepository.delete(zoneMaster);
    }

    /*
    *Method to find Max Id
    * @Return Long ZoneMaster
    */
    @Override
    public Long finMaxId() {
        log.info("ENTRY-EXIT - find maximum Id");
        return zoneMasterRepository.findMaxId();
    }

}