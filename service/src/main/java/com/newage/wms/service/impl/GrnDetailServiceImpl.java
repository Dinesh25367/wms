package com.newage.wms.service.impl;

import com.newage.wms.entity.*;
import com.newage.wms.entity.QGrnDetail;
import com.newage.wms.entity.QGrnHeader;
import com.newage.wms.repository.GrnDetailRepository;
import com.newage.wms.service.GrnDetailService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class GrnDetailServiceImpl implements GrnDetailService {

    @Autowired
    private GrnDetailRepository grnDetailRepository;

    @Autowired
    private GrnCalculation grnCalculation;

    @Override
    public List<GrnDetail> findAllGrnDetailForTransactionIdAndTransactionDetailId(Long grnId, Long transactionDetailId) {
        List<GrnDetail> grnDetailList = grnDetailRepository.findAll();
        return grnDetailList.stream()
                .filter(detail -> detail.getTrnDetailMaster().getId().equals(transactionDetailId) )
                .filter(detail -> detail.getGrnHeader() != null && detail.getGrnHeader().getId() != null && detail.getGrnHeader().getId().equals(grnId))
                .filter(detail -> detail.getDeleted() != null && detail.getDeleted().equals("0"))
                .sorted(Comparator.comparingInt(GrnDetail::getTransactionSlNo))
                .collect(Collectors.toList());
    }

    @Override
    public List<GrnDetail> findAllGrnDetailForGrnId(Long grnId) {
        List<GrnDetail> grnDetailList = grnDetailRepository.findAll();
        return grnDetailList.stream()
                .filter(detail -> detail.getGrnHeader() != null && detail.getGrnHeader().getId() != null && detail.getGrnHeader().getId().equals(grnId))
                .filter(detail -> detail.getDeleted() != null && detail.getDeleted().equals("0"))
                .collect(Collectors.toList());
    }

    @Override
    public double getAllCompletedQtyForOneDetail(TrnDetail trnDetail, GrnHeader grnHeader,boolean isActualQty) {
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QGrnDetail.grnDetail.id.isNotNull());
        predicates.add(QGrnDetail.grnDetail.deleted.equalsIgnoreCase("0"));
        predicates.add(QGrnDetail.grnDetail.grnHeader.status.equalsIgnoreCase("Completed"));
        predicates.add(QGrnDetail.grnDetail.trnDetailMaster.id.eq(trnDetail.getId()));
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnDetail> grnDetailList = grnDetailRepository.findAll(predicateAll, Pageable.unpaged()).getContent();
        if (!grnDetailList.isEmpty()){
            double totalCompletedQty = 0.0;
            double toUomRatio;
            double rounding = 0;
            if (isActualQty) {
                toUomRatio = trnDetail.getUomMaster().getRatio();
                rounding = trnDetail.getUomMaster().getDecimalPlaces();
            }else {
                toUomRatio = trnDetail.getRUomMaster().getRatio();
                rounding = trnDetail.getRUomMaster().getDecimalPlaces();
            }
            for (GrnDetail grnDetail : grnDetailList){
                double fromUomRatio = grnDetail.getUomMaster().getRatio();
                totalCompletedQty += grnCalculation.getUomRatioConvertedQty(fromUomRatio,toUomRatio,grnDetail.getReceivingQty());
                totalCompletedQty = grnCalculation.getRoundedValue(rounding,totalCompletedQty);
            }
            return totalCompletedQty;
        }
        return 0.0;
    }

    @Override
    public void deleteAllIterables(Iterable<GrnDetail> grnDetailIterable) {
        grnDetailRepository.deleteAll(grnDetailIterable);
    }

    @Override
    public boolean checkIfDetailIdForDeletable(Long id) {
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QGrnDetail.grnDetail.id.isNotNull());
        predicates.add(QGrnDetail.grnDetail.deleted.equalsIgnoreCase("0"));
        if (id != null) {
            predicates.add(QGrnDetail.grnDetail.trnDetailMaster.id.eq(id));
            predicates.add(QGrnHeader.grnHeader.status.notEqualsIgnoreCase("Cancelled"));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        return grnDetailRepository.findAll(predicateAll, Pageable.unpaged()).isEmpty();

    }

    /*
     * Method to get all grn Detail
     * @return GrnDetail>
     */
    @Override
    public List<GrnDetail> findAll(Long grnHeaderId) {
        List<GrnDetail> grnDetailList = grnDetailRepository.findAll();
        return grnDetailList.stream()
                .filter(detail -> detail.getGrnHeader() != null && detail.getGrnHeader().getId() != null &&
                        detail.getGrnHeader().getId().equals(grnHeaderId))
                .filter(detail -> detail.getDeleted() != null && detail.getDeleted().equals("0"))
                .sorted(Comparator.comparingInt(GrnDetail::getTransactionSlNo))
                .collect(Collectors.toList());
    }

    @Override
    public GrnDetail getById(Long id) {
        return grnDetailRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.GRN_DETAIL_ID_NOT_FOUND.CODE,
                        ServiceErrors.GRN_DETAIL_ID_NOT_FOUND.KEY));
    }

    @Override
    public void deleteAllInIterable(List<GrnDetail> grnDetailListToBeDeleted) {
        grnDetailRepository.deleteAll(grnDetailListToBeDeleted);
    }

}
