package com.newage.wms.service.impl;

import com.newage.wms.entity.VesselMaster;
import com.newage.wms.repository.VesselMasterRepository;
import com.newage.wms.service.VesselMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class VesselMasterServiceImpl implements VesselMasterService {

    private final VesselMasterRepository vesselMasterRepository;

    @Autowired
    public VesselMasterServiceImpl(VesselMasterRepository vesselMasterRepository) {
        this.vesselMasterRepository = vesselMasterRepository;
    }

    /*
     * Method to find All VesselMaster with pagination, sort and filter
     * @Return Page<VesselMaster>
     */
    @Override
    public Page<VesselMaster> getAllVessels(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - Find All VesselMaster with pagination, sort and filter");
        return vesselMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to get VesselMaster by Id
     * @Return VesselMaster
     */
    @Override
    public VesselMaster getVesselById(Long id) {
        log.info("ENTRY-EXIT - get VesselMaster by Id");
        return vesselMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.VESSEL_ID_NOT_FOUND.CODE, ServiceErrors.VESSEL_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to Save VesselMaster
     * @Return VesselMaster
     */
    @Override
    public VesselMaster createVessel(VesselMaster vesselMaster) {
        log.info("ENTRY-EXIT - Save VesselMaster");
        return vesselMasterRepository.save(vesselMaster);
    }

    /*
     * Method to Update VesselMaster
     * @Return VesselMaster
     */
    @Override
    public VesselMaster updateVessel(VesselMaster vesselMaster) {
        log.info("ENTRY-EXIT - Update VesselMaster");
        return vesselMasterRepository.save(vesselMaster);
    }

    /*
     * Method to validate VesselMaster by name
     */
    @Override
    public void validateNewName(String name) {
        log.info("ENTRY-EXIT - validate VesselMaster by name");
        if (vesselMasterRepository.existsByName(name)) {
            throw new ServiceException(ServiceErrors.VESSEL_NAME_DUPLICATE.CODE, ServiceErrors.VESSEL_NAME_DUPLICATE.KEY);
        }
    }

    /*
     * Method to validate VesselMaster by short name
     */
    @Override
    public void validateNewShortName(String shortName) {
        log.info("ENTRY-EXIT - validate VesselMaster by short name");
        if (vesselMasterRepository.existsByShortName(shortName)) {
            throw new ServiceException(ServiceErrors.VESSEL_SHORT_NAME_DUPLICATE.CODE, ServiceErrors.VESSEL_SHORT_NAME_DUPLICATE.KEY);
        }
    }

    /*
     * Method to validate new Call Sign
     */
    @Override
    public void validateNewCallSign(String callSign) {
        log.info("ENTRY-EXIT - validate new Call Sign");
        if (vesselMasterRepository.existsByCallSign(callSign)) {
            throw new ServiceException(ServiceErrors.VESSEL_CALL_SIGN_DUPLICATE.CODE, ServiceErrors.VESSEL_CALL_SIGN_DUPLICATE.KEY);
        }
    }

    /*
     * Method to validate new IMO
     */
    @Override
    public void validateNewIMO(String imoNumber) {
        log.info("ENTRY-EXIT - validate new IMO");
        if (vesselMasterRepository.existsByImoNumber(imoNumber)) {
            throw new ServiceException(ServiceErrors.VESSEL_IMO_DUPLICATE.CODE, ServiceErrors.VESSEL_IMO_DUPLICATE.KEY);
        }
    }

    /*
     * Method to delete VesselMaster
     */
    @Override
    public void deleteVessel(VesselMaster vesselMaster) {
        log.info("ENTRY-EXIT - delete VesselMaster");
        vesselMasterRepository.delete(vesselMaster);
    }

    /*
     * Method to get all VesselMaster for autoComplete
     * @Return Iterable<VesselMaster>
     */
    @Override
    public Iterable<VesselMaster> getAllAutoComplete(Predicate combinedPredicate, Pageable pageable) {
        log.info("ENTRY-EXIT - get all VesselMaster for autoComplete");
        return vesselMasterRepository.findAll(combinedPredicate,pageable);
    }

}
