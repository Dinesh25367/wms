package com.newage.wms.service.impl;

import com.newage.wms.entity.TrnDetail;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.repository.TrnDetailRepository;
import com.newage.wms.repository.TrnHeaderAsnRepository;
import com.newage.wms.service.TrnDetailService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Log4j2
public class TrnDetailServiceImpl implements TrnDetailService {

    @Autowired
    private TrnDetailRepository trnDetailRepository;

    @Autowired
    private TrnHeaderAsnRepository trnHeaderAsnRepository;

    @Override
    public TrnDetail save(TrnDetail trnDetail) {
        return trnDetailRepository.save(trnDetail);
    }

    /*
     * Method to get TrnDetail by id
     * @Return TrnDetail
     */
    @Override
    public TrnDetail getById(Long id) {
        log.info("ENTRY-EXIT - Update TrnDetail by Id");
        return trnDetailRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.TRANSACTION_DETAIL_ID_NOT_FOUND.CODE,
                        ServiceErrors.TRANSACTION_DETAIL_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to delete all TrnDetail Iterable
     */
    @Override
    public void deleteAllInIterable(Iterable<TrnDetail> trnDetailIterable) {
        log.info("ENTRY-EXIT - Delete all TrnDetail Iterable");
        trnDetailRepository.deleteAll(trnDetailIterable);
    }

    @Override
    public Integer findMaxTransactionSerialNo(Long transactionId) {
        TrnHeaderAsn trnHeaderAsn = trnHeaderAsnRepository.findById(transactionId).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.TRN_HEADER_ID_NOT_FOUND.CODE,
                        ServiceErrors.TRN_HEADER_ID_NOT_FOUND.KEY));
        List<TrnDetail> trnDetailList = trnHeaderAsn.getTrnDetailList();
        if (trnDetailList != null) {
            TrnDetail maxTrnDetail = trnDetailList.stream()
                    .max(Comparator.comparing(
                            trnDetail -> trnDetail.getTransactionSlNo()
                    ))
                    .orElse(null);
            if (maxTrnDetail != null && maxTrnDetail.getTransactionSlNo() != null) {
                return maxTrnDetail.getTransactionSlNo();
            } else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    @Override
    public Boolean getSkuEditFlag(Long id) {
        List<TrnDetail> trnDetailList = trnDetailRepository.findAll();
        Boolean codeExists = trnDetailList.stream()
                .anyMatch(trnDetail -> trnDetail.getSkuMaster() != null
                        && trnDetail.getSkuMaster().getId().equals(id) && !trnDetail.getTrnHeaderAsn().getTransactionStatus().toLowerCase().contains("cancel"));
        return codeExists;
    }
}
