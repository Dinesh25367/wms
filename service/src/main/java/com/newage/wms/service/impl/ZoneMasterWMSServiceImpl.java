package com.newage.wms.service.impl;

import com.newage.wms.entity.ZoneMasterWMS;
import com.newage.wms.repository.ZoneMasterWMSRepository;
import com.newage.wms.service.ZoneMasterWMSService;
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
public class ZoneMasterWMSServiceImpl implements ZoneMasterWMSService {

    @Autowired
    private ZoneMasterWMSRepository zoneMasterWMSRepository;

    /*
     * Method to get all ZoneMasterWMS with pagination, sort and filter
     * @return Page<ZoneMasterWMS>
     */
    @Override
    public Page<ZoneMasterWMS> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all ZoneMasterWMS with pagination, sort and filter");
        return zoneMasterWMSRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save ZoneMasterWMS
     * @Return ZoneMasterWMS
     */
    @Override
    public ZoneMasterWMS saveZoneWMS(ZoneMasterWMS zoneMasterWMS) {
        log.info("ENTRY-EXIT - Save ZoneMasterWMS ");
        return zoneMasterWMSRepository.save(zoneMasterWMS);
    }

    /*
     *Method to update ZoneMasterWMS
     * @Return ZoneMasterWMS
     */
    @Override
    public ZoneMasterWMS updateZoneMasterWMS(ZoneMasterWMS zoneMasterWMS) {
        log.info("ENTRY-EXIT - Update ZoneMasterWMS by Id");
        return zoneMasterWMSRepository.save(zoneMasterWMS);
    }

    /*
     *Method to get ZoneMasterWMS by id
     * @Return ZoneMasterWMS
     */
    @Override
    public ZoneMasterWMS getZoneMasterWMSById(Long id) {
        log.info("ENTRY-EXIT - Update ZoneMasterWMS by id");
        return zoneMasterWMSRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.ZONE_MASTER_ID_NOT_FOUND.CODE,
                        ServiceErrors.ZONE_MASTER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all ZoneMasterWMS for autocomplete
     * @Return Iterable<ZoneMasterWMS>
     */
    @Override
    public Iterable<ZoneMasterWMS> fetchAllZoneWMS(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all ZoneMasterWMS for autocomplete");
        return zoneMasterWMSRepository.findAll(predicate,pageable);
    }

    /*
     *Method to get ZoneWMS by code
     * @Return ZoneMasterWMS
     */
    @Override
    public ZoneMasterWMS getZoneMasterWMSByCode(String zoneCode) {
        log.info("ENTRY-EXIT - Update Zone by code");
        return zoneMasterWMSRepository.findByCode(zoneCode);
    }

    /*
     * Method to validate unique ZoneMasterWMS attributes in Save
     */
    @Override
    public void validateUniqueZoneAttributeSave(String code,Long warehouseId){
        log.info("ENTRY - validate unique ZoneMasterWMS attributes in save");
        List<ZoneMasterWMS> zoneMasterList = zoneMasterWMSRepository.findAll();
        Boolean codeExists = zoneMasterList.stream()
                .anyMatch(zone -> zone.getCode().equals(code) && zone.getWareHouseMaster().getId().equals(warehouseId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.ZONE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.ZONE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique ZoneMasterWMS attributes in Update
     */
    @Override
    public void validateUniqueZoneAttributeUpdate(String code,Long id, Long warehouseId){
        log.info("ENTRY - validate unique ZoneMasterWMS attributes in update");
        List<ZoneMasterWMS> zoneMasterList = zoneMasterWMSRepository.findAll();
        Boolean codeExists = zoneMasterList.stream()
                .anyMatch(zone -> (zone.getCode().equals(code) && zone.getWareHouseMaster().getId().equals(warehouseId)) && !zone.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.ZONE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.ZONE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}
