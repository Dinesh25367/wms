package com.newage.wms.service.impl;

import com.newage.wms.entity.DoorMaster;
import com.newage.wms.entity.QDoorMaster;
import com.newage.wms.repository.DoorMasterRepository;
import com.newage.wms.service.DoorMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@Service
public class DoorMasterServiceImpl implements DoorMasterService {

    @Autowired
    private DoorMasterRepository doorMasterRepository;

    @Override
    public Page<DoorMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all Door");
        return doorMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save Door master
     * @Return DoorMaster
     */
    @Override
    public DoorMaster saveDoor(DoorMaster doorMaster) {
        log.info("ENTRY-EXIT - Save Location Type");
        return doorMasterRepository.save(doorMaster);
    }

    /*
     * Method to update Door master
     * @Return DoorMaster
     */
    @Override
    public DoorMaster updateDoor(DoorMaster doorMaster) {
        log.info("ENTRY-EXIT - Update Door");
        return doorMasterRepository.save(doorMaster);
    }

    /*
     * Method to get DoorMaster by Id
     * @Return DoorMaster
     */
    @Override
    public DoorMaster findById(Long id) {
        log.info("ENTRY-EXIT - get DoorMaster by Id");
        return doorMasterRepository.findById(id).
                orElseThrow(()-> new ServiceException(
                        ServiceErrors.DOOR_ID_DOES_NOT_EXIST.CODE,
                        ServiceErrors.DOOR_ID_DOES_NOT_EXIST.KEY
                ));
    }

    /*
     * Method to find All DoorMaster for autoComplete
     * @Return Iterable<DoorMaster>
     */
    @Override
    public Iterable<DoorMaster> findAllAutoComplete(String query,String status,Long branchId) {
        log.info("ENTRY-EXIT - Find All DoorMaster for autoComplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QDoorMaster.doorMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QDoorMaster.doorMaster.name.containsIgnoreCase(query)
                    .or(QDoorMaster.doorMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QDoorMaster.doorMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QDoorMaster.doorMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        return doorMasterRepository.findAll(predicateAll,pageable);
    }

    @Override
    public void validateUniqueDoorAttributeSave(String code) {
        log.info("ENTRY - validate unique Door attributes in save");
        List<DoorMaster> doorMasterList = doorMasterRepository.findAll();
        Boolean codeExists = doorMasterList.stream()
                .anyMatch(door -> door.getCode().equals(code));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.DOOR_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.DOOR_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }
    /*
     * Method to validate unique Door attributes in Update
     */
    @Override
    public void validateUniqueDoorAttributeUpdate(String code, Long id) {
        log.info("ENTRY - validate unique LocType attributes in update");
        List<DoorMaster> doorMasterList = doorMasterRepository.findAll();
        Boolean codeExists = doorMasterList.stream()
                .anyMatch(door -> door.getCode().equals(code) && !door.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.DOOR_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.DOOR_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}

