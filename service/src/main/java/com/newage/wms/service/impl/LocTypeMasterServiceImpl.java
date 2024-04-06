package com.newage.wms.service.impl;

import com.newage.wms.entity.LocTypeMaster;
import com.newage.wms.repository.LocTypeMasterRepository;
import com.newage.wms.service.LocTypeMasterService;
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
public class LocTypeMasterServiceImpl implements LocTypeMasterService {

    @Autowired
    private LocTypeMasterRepository locTypeMasterRepository;

    /*
     * Method to get all LocType
     * @return Page<LocTypeMaster>
     */
    @Override
    public Page<LocTypeMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all StorageType");
        return locTypeMasterRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save Location Type master
     * @Return LocTypeMaster
     */
    @Override
    public LocTypeMaster saveLocType(LocTypeMaster locTypeMaster) {
        log.info("ENTRY-EXIT - Save Location Type");
        return locTypeMasterRepository.save(locTypeMaster);
    }

    /*
     * Method to update Location Type master
     * @Return LocTypeMaster
     */
    @Override
    public LocTypeMaster updateLocType(LocTypeMaster locTypeMaster) {
        log.info("ENTRY-EXIT - Update Location Type");
        return locTypeMasterRepository.save(locTypeMaster);
    }

    /*
     * Method to get LocType by id
     * @return LocTypeMaster
     */
    @Override
    public LocTypeMaster getLocTypeById(Long id){
        log.info("ENTRY-EXIT - Get Location Type by id");
        return locTypeMasterRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.LOC_TYPE_ID_NOT_FOUND.CODE,
                        ServiceErrors.LOC_TYPE_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all LocType
     * @return Iterable<LocTypeMaster>
     */
    @Override
    public Iterable<LocTypeMaster> fetchAllLocationType(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all Location Type");
        return locTypeMasterRepository.findAll(predicate, pageable);
    }

    /*
     * Method to get LocType by code
     * @return LocTypeMaster
     */
    @Override
    public LocTypeMaster getLocTypeByCode(String locTypeCode) {
        log.info("ENTRY-EXIT - Get Location Type by code");
        return locTypeMasterRepository.findByCode(locTypeCode);
    }

    /*
     * Method to validate unique LocType attributes in Save
     */
    @Override
    public void validateUniqueLocTypeAttributeSave(String code) {
        log.info("ENTRY - validate unique LocType attributes in save");
        List<LocTypeMaster> locTypeMasterList = locTypeMasterRepository.findAll();
        Boolean codeExists = locTypeMasterList.stream()
                .anyMatch(locType -> locType.getCode().equals(code));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.LOC_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.LOC_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique LocType attributes in Update
     */
    @Override
    public void validateUniqueLocTypeAttributeUpdate(String code,Long id){
        log.info("ENTRY - validate unique LocType attributes in update");
        List<LocTypeMaster> locTypeMasterList = locTypeMasterRepository.findAll();
        Boolean codeExists = locTypeMasterList.stream()
                .anyMatch(locType -> locType.getCode().equals(code) && !locType.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.LOC_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.LOC_TYPE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}
