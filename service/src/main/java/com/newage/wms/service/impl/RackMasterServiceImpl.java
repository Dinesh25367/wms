package com.newage.wms.service.impl;

import com.newage.wms.entity.RackMaster;
import com.newage.wms.repository.RackMasterRepository;
import com.newage.wms.service.RackMasterService;
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
public class RackMasterServiceImpl implements RackMasterService {

    @Autowired
    private RackMasterRepository rackMasterRepository;

    /*
     * Method to get all Rack with pagination, sort and filter
     * @return Page<RackMaster>
     */
    @Override
    public Page<RackMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all Rack with pagination, sort and filter");
        return rackMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save Rack
     * @return RackMaster
     */
    @Override
    public RackMaster saveRack(RackMaster rackMaster) {
        log.info("ENTRY-EXIT - Create Rack");
        return rackMasterRepository.save(rackMaster);
    }

    /*
     * Method to update Rack
     * @return RackMaster
     */
    @Override
    public RackMaster updateRack(RackMaster rackMaster) {
        log.info("ENTRY-EXIT - Update Rack");
        return rackMasterRepository.save(rackMaster);
    }

    /*
     * Method to get Rack by id
     * @return RackMaster
     */
    @Override
    public RackMaster getRackMasterById(Long id) {
        log.info("ENTRY-EXIT - Get Rack by id");
        return rackMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.RACK_ID_NOT_FOUND.CODE,
                        ServiceErrors.RACK_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all Rack for autocomplete
     * @return Iterable<RackMaster>
     */
    @Override
    public Iterable<RackMaster> fetchAllRack(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all Rack autocomplete");
        return rackMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to get Rack by code
     * @return RackMaster
     */
    @Override
    public RackMaster getRackMasterByCode(String rackCode) {
        log.info("ENTRY-EXIT - Get Rack by code");
        return rackMasterRepository.findByCode(rackCode);
    }

    /*
     * Method to validate unique rack attributes in Save
     */
    @Override
    public void validateUniqueRackAttributeSave(String code,Long warehouseId){
        log.info("ENTRY - validate unique rack attributes in save");
        List<RackMaster> rackMasterList = rackMasterRepository.findAll();
        Boolean codeExists = rackMasterList.stream()
                .anyMatch(rack -> rack.getCode().equals(code) && rack.getWareHouseMaster().getId().equals(warehouseId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.RACK_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.RACK_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique rack attributes in Update
     */
    @Override
    public void validateUniqueRackAttributeUpdate(String code,Long id,Long warehouseId){
        log.info("ENTRY - validate unique rack attributes in update");
        List<RackMaster> rackMasterList = rackMasterRepository.findAll();
        Boolean codeExists = rackMasterList.stream()
                .anyMatch(rack -> (rack.getCode().equals(code) && rack.getWareHouseMaster().getId().equals(warehouseId)) && !rack.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.RACK_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.RACK_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}
