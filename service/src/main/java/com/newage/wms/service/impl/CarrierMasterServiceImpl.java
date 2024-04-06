package com.newage.wms.service.impl;

import com.newage.wms.entity.CarrierMaster;
import com.newage.wms.entity.QCarrierMaster;
import com.newage.wms.repository.CarrierMasterRepository;
import com.newage.wms.service.CarrierMasterService;
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

@Service
@Log4j2
public class CarrierMasterServiceImpl implements CarrierMasterService {

    private final CarrierMasterRepository carrierMasterRepository;

    @Autowired
    public CarrierMasterServiceImpl(CarrierMasterRepository carrierMasterRepository) {
        this.carrierMasterRepository = carrierMasterRepository;
    }

    /*
     * Method to get all Carrier
     * Return Carrier
     */
    @Override
    public Page<CarrierMaster> getAllCarrier(Predicate predicate, Pageable pageRequest) {
        log.info("ENTRY-EXIT - get all Carrier");
        return carrierMasterRepository.findAll(predicate, pageRequest);
    }

    /*
     * Method to get Carrier by Id
     * Return Carrier
     */
    @Override
    public CarrierMaster getCarrierById(Long id) {
        log.info("ENTRY-EXIT - get Carrier By Id");
        return carrierMasterRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.CARRIER_ID_NOT_FOUND.CODE,
                        ServiceErrors.CARRIER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to Filter Carrier
     * @Return Page<CarrierMaster>
     */
    @Override
    public Page<CarrierMaster> getFilteredAllCarrier(Predicate predicate, Pageable pageRequest, String carrierName, String carrierCode) {
        log.info("ENTRY-EXIT -  Filter Carrier");
        QCarrierMaster qCarrierMaster = QCarrierMaster.carrierMaster;
        Collection<Predicate> predicates = new ArrayList<>();
        if (carrierName != null && !carrierName.isEmpty() && carrierCode != null && !carrierCode.isEmpty()) {
            predicates.add(qCarrierMaster.name.startsWithIgnoreCase(carrierName).or(qCarrierMaster.code.startsWithIgnoreCase(carrierCode)));
        }
        predicates.add(predicate);
        log.info("predicates  " + predicates);
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        log.info("predicateAll  " + predicateAll);
        return carrierMasterRepository.findAll(predicateAll, pageRequest);
    }

    /*
     * Method to Filter Carrier for autocomplete
     * @Return Iterable<CarrierMaster>
     */
    @Override
    public Iterable<CarrierMaster> getAllAutoComplete(String query, String transportMode,String status) {
        log.info("ENTRY-EXIT - Filter Carrier for autocomplete");

        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QCarrierMaster.carrierMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QCarrierMaster.carrierMaster.code.containsIgnoreCase(query)
                    .or(QCarrierMaster.carrierMaster.name.containsIgnoreCase(query)));
        }
        if (transportMode != null && !transportMode.isEmpty() && !transportMode.isBlank()){
            predicates.add(QCarrierMaster.carrierMaster.transportMode.eq(transportMode));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QCarrierMaster.carrierMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        return carrierMasterRepository.findAll(predicateAll,pageable);
    }

}
