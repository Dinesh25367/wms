package com.newage.wms.service.impl;

import com.newage.wms.entity.ConfigurationMaster;
import com.newage.wms.entity.TrnDetail;
import com.newage.wms.entity.TrnDetailSo;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.repository.ConfigurationMasterRepository;
import com.newage.wms.repository.TrnDetailRepository;
import com.newage.wms.repository.TrnDetailSoRepository;
import com.newage.wms.repository.TrnHeaderAsnRepository;
import com.newage.wms.service.TrnDetailService;
import com.newage.wms.service.TrnDetailSoService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2

public class TrnDetailSoServiceImpl implements TrnDetailSoService {

    @Autowired
    private TrnDetailSoRepository trnDetailSoRepository;

    @Autowired
    private TrnDetailRepository trnDetailRepository;

    @Autowired
    private TrnHeaderAsnRepository trnHeaderAsnRepository;

    @Autowired
    private ConfigurationMasterRepository configurationMasterRepository;

    /*
     * Method to get TrnDetailSo by id
     * @Return TrnDetail
     */
    @Override
    public TrnDetailSo getById(Long id) {
        log.info("ENTRY-EXIT - Update TrnDetail by Id");
        return trnDetailSoRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.TRANSACTION_DETAIL_ID_NOT_FOUND.CODE,
                        ServiceErrors.TRANSACTION_DETAIL_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to delete all TrnDetail Iterable
     */
    @Override
    public void deleteAllInIterable(Iterable<TrnDetailSo> trnDetailSoIterable) {
        log.info("ENTRY-EXIT - Delete all TrnDetail Iterable");
        trnDetailSoRepository.deleteAll(trnDetailSoIterable);
    }

    @Override
    public String generateTransactionUid() {
        log.info("ENTRY - generate new TransactionUID");
        List<TrnHeaderAsn> trnHeaderAsnListAll = trnHeaderAsnRepository.findAll();
        List<TrnHeaderAsn> trnHeaderAsnList = trnHeaderAsnListAll.stream()
                .filter(header -> header.getTransactionUid().startsWith("SO"))
                .collect(Collectors.toList());
        List<ConfigurationMaster> configurationMasterList = configurationMasterRepository.findAll();
        List<ConfigurationMaster> asnConfigurationList = configurationMasterList.stream()                               //get Transaction UID format from configuration master
                .filter(configuration -> configuration.getModule().equals("SO trans uid"))
                .collect(Collectors.toList());
        String asnFormat = getAsnFormat(asnConfigurationList);
        if (trnHeaderAsnList.isEmpty()){
            return "SO"+asnFormat+"00001";                                                                             //set default TransactionUID
        }
        else {                                                                                                          //get maximum TransactionUID and increment by 1
            TrnHeaderAsn maxTrnHeaderAsn = trnHeaderAsnList.stream()
                    .filter(trnHeader -> trnHeader.getTransactionUid().startsWith("SO"))
                    .max(Comparator.comparing(
                            trnHeader -> trnHeader.getTransactionUid().
                                    substring(trnHeader.getTransactionUid().length() - 5)
                    ))
                    .orElse(trnHeaderAsnList.get(0));
            String maxTransactionUid = maxTrnHeaderAsn.getTransactionUid();
            if (maxTransactionUid.isEmpty()){
                return "SO"+asnFormat+"00001";
            }else {
                String maxTransactionUidNumericPart = maxTransactionUid.
                        substring(maxTransactionUid.length() - 5);
                try {
                    Integer newTransactionUidNumericPart = Integer.parseInt(maxTransactionUidNumericPart) + 1;
                    String newTransactionUidNumericPartWithZeroes = String.format("%05d", newTransactionUidNumericPart);
                    return "SO" +asnFormat+ newTransactionUidNumericPartWithZeroes;
                } catch (NumberFormatException e) {
                    // Handle parsing error, if any
                    throw new ServiceException(ServiceErrors.UNABLE_TO_GENERATE_TRANSACTION_UID.CODE,
                            ServiceErrors.UNABLE_TO_GENERATE_TRANSACTION_UID.KEY);
                }
            }
        }
    }

    @Override
    public Boolean isBackOrder(Long transId) {
        List<TrnDetail> trnDetailList = trnDetailRepository.findAll();
        return trnDetailList.stream()
                .anyMatch(trnDetail ->trnDetail.getTrnHeaderAsn()!=null&&trnDetail.getIsBackOrder()!=null &&trnDetail.getTrnHeaderAsn().getId().equals(transId)&&trnDetail.getIsBackOrder().equalsIgnoreCase("Yes"));
    }


    public String getAsnFormat( List<ConfigurationMaster> asnConfigurationList){
        LocalDate currentDate = LocalDate.now();
        String asnFormat = "";
        if (!CollectionUtils.isEmpty(asnConfigurationList)){                                                            //add day,month and year based on configuration master
            String day= "";
            String month = "";
            String year = "";
            for (ConfigurationMaster configurationMaster : asnConfigurationList){
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("so_transuid_day_included") && configurationMaster.getValue().equals("Yes")){
                    day = String.format("%02d", currentDate.getDayOfMonth());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("so_transuid_month_included") && configurationMaster.getValue().equals("Yes")){
                    month = String.format("%02d", currentDate.getMonthValue());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("so_transuid_year_included") && configurationMaster.getValue().equals("Yes")){
                    year = String.format("%02d", currentDate.getYear()%100);
                }
            }
            asnFormat= year+month+day;
            return asnFormat;
        }
        return asnFormat;
    }

    @Override
    public void deleteById(Long id) {
        trnDetailSoRepository.deleteById(id);
    }

}
