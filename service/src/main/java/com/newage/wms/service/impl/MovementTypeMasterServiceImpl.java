package com.newage.wms.service.impl;

import com.newage.wms.entity.MovementTypeMaster;
import com.newage.wms.repository.MovementTypeMasterRepository;
import com.newage.wms.service.MovementTypeMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class MovementTypeMasterServiceImpl implements MovementTypeMasterService {

    @Autowired
    private MovementTypeMasterRepository movementTypeMasterRepository;

    /*
     * Method to get MovementTypeMaster by id
     * @return MovementTypeMaster
     */
    @Override
    public MovementTypeMaster getById(Long id) {
        log.info("ENTRY-EXIT - Get MovementTypeMaster by id");
        return movementTypeMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.MOVEMENT_TYPE_ID_NOT_FOUND.CODE,
                        ServiceErrors.MOVEMENT_TYPE_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all MovementTypeMaster for AutoComplete
     * @return Iterable<MovementTypeMaster>
     */
    @Override
    public Iterable<MovementTypeMaster> getAllAutoComplete(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all MovementTypeMaster for AutoComplete");
        return movementTypeMasterRepository.findAll(predicate, pageable);
    }

    @Override
    public MovementTypeMaster getByCode() {
        return movementTypeMasterRepository.findByCode("DIRECT");
    }

    /*
     * Method to save new MovementTypeMaster
     * @return  MovementTypeMaster
     */
    @Override
    public MovementTypeMaster save(MovementTypeMaster movementTypeMaster) {
        log.info("ENTRY - EXIT save all MovementTypeMaster ");
        return movementTypeMasterRepository.save(movementTypeMaster);
    }


    /*
     * Method to find all MovementTypeMaster with filter, sort and pagination
     * @Return MovementTypeMaster
     */
    @Override
    public Page<MovementTypeMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - find MovementTypeMaster by id");
        return movementTypeMasterRepository.findAll(predicate, pageable);
    }


    /*
     * Method to update existing MovementTypeMaster
     * @return MovementTypeMaster
     */
    @Override
    public MovementTypeMaster update(MovementTypeMaster movementTypeMaster) {
        log.info("ENTRY - EXIT update MovementTypeMaster");
        return movementTypeMasterRepository.save(movementTypeMaster);
    }

    /*
     * Method to validate unique Movement Type attributes in Save
     */
    @Override
    public void validateUniqueRackAttributeSave(String code) {
        log.info("ENTRY - validate unique MoveMent Type attributes in save");
        List<MovementTypeMaster> movementTypeMasterList = movementTypeMasterRepository.findAll();
        Boolean codeExists = movementTypeMasterList.stream()
                .anyMatch(movementTypeMaster -> movementTypeMaster.getCode().equals(code));
        if (codeExists) {
            throw new ServiceException(
                    ServiceErrors.MOVEMENT_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.MOVEMENT_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique Movement Type attributes in Update
     */

    @Override
    public void validateUniqueRackAttributeUpdate(String code, Long id) {
        log.info("ENTRY - validate unique  Movement Type attributes in update");
        List<MovementTypeMaster> movementTypeMasterList = movementTypeMasterRepository.findAll();
        Boolean codeExists = movementTypeMasterList.stream()
                .anyMatch(movementTypeMaster -> movementTypeMaster.getCode().equals(code) && !movementTypeMaster.getId().equals(id));
        if (codeExists) {
            throw new ServiceException(
                    ServiceErrors.MOVEMENT_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.MOVEMENT_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}
