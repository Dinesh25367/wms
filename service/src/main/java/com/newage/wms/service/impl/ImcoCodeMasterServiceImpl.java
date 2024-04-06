package com.newage.wms.service.impl;

import com.newage.wms.entity.CategoryMaster;
import com.newage.wms.entity.ImcoCodeMaster;
import com.newage.wms.repository.ImcoCodeMasterRepository;
import com.newage.wms.service.ImcoCodeMasterService;
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
public class ImcoCodeMasterServiceImpl implements ImcoCodeMasterService {

    @Autowired
    private ImcoCodeMasterRepository imcoCodeMasterRepository;

    /*
     *Method to find ImcoCode by id
     * @Return ImcoCodeMaster
     */
    @Override
    public ImcoCodeMaster getById(Long id) {
        log.info("ENTRY-EXIT - find ImcoCode by id");
        return imcoCodeMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.IMCO_CODE_ID_NOT_FOUND.CODE,
                        ServiceErrors.IMCO_CODE_ID_NOT_FOUND.KEY));
    }

    /*
     *Method to find all ImcoCode
     * @Return Iterable<ImcoCodeMaster>
     */
    @Override
    public Iterable<ImcoCodeMaster> getAllAutoComplete(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - find all ImcoCode");
        return imcoCodeMasterRepository.findAll(predicate,pageable);
    }
    /*
     * Method to save new ImcoCodeMaster
     * @return  ImcoCodeMaster
     */
    @Override
    public ImcoCodeMaster saveImcoCode(ImcoCodeMaster imcoCodeMaster) {
        log.info("ENTRY - EXIT save all ImcoCodeMaster ");
        return imcoCodeMasterRepository.save(imcoCodeMaster);
    }
    /*
     * Method to find all ImcoCodeMaster with filter, sort and pagination
     * @Return ImcoCodeMaster
     */
    @Override
    public Page<ImcoCodeMaster> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - find ImcoCodeMaster by id");
        return imcoCodeMasterRepository.findAll(predicate,pageable);
    }
    /*
     * Method to update existing ImcoCodeMaster
     * @return ImcoCodeMaster
     */
    @Override
    public ImcoCodeMaster updateImcoCodeMaster(ImcoCodeMaster imcoCodeMaster) {
        log.info("ENTRY - EXIT update ImcoCodeMaster");
        return imcoCodeMasterRepository.save(imcoCodeMaster);
    }

    @Override
    public void validateUniqueImcoAttributeSave(String code){
        log.info("ENTRY - validate unique category attributes in save");
        List<ImcoCodeMaster> imcoCodeMasterList = imcoCodeMasterRepository.findAll();
        Boolean codeExists = imcoCodeMasterList.stream()
                .anyMatch(imco -> imco.getCode().equals(code));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique aisle attributes in Update
     */
    @Override
    public void validateUniqueImcoAttributeUpdate(String code,Long id){
        log.info("ENTRY - validate unique aisle attributes in update");
        List<ImcoCodeMaster> imcoCodeMasterList = imcoCodeMasterRepository.findAll();
        Boolean codeExists = imcoCodeMasterList.stream()
                .anyMatch(imco -> imco.getCode().equals(code) && !imco.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    @Override
    public ImcoCodeMaster getByCode(String code) {
        return imcoCodeMasterRepository.getByCode(code);
    }

}
