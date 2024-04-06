package com.newage.wms.service.impl;

import com.newage.wms.entity.ConfigurationMaster;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.entity.TrnHeaderSo;
import com.newage.wms.repository.ConfigurationMasterRepository;
import com.newage.wms.repository.TrnHeaderAsnRepository;
import com.newage.wms.repository.TrnHeaderSoRepository;
import com.newage.wms.service.TrnHeaderAsnService;
import com.newage.wms.service.TrnHeaderSoService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service

public class TrnHeaderSoServiceImpl implements TrnHeaderSoService {

    @Autowired
    private TrnHeaderSoRepository trnHeaderSoRepository;

    @Autowired
    private TrnHeaderAsnRepository trnHeaderAsnRepository;

    @Autowired
    private ConfigurationMasterRepository configurationMasterRepository;


    /*
     * Method to get TrnHeaderAsn by id
     * @Return TrnHeaderAsn
     */
    @Override
    public TrnHeaderSo findById(Long id) {
        log.info("ENTRY-EXIT - Get TrnHeaderAsn by id");
        return trnHeaderSoRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.TRN_HEADER_SO_ID_NOT_FOUND.CODE,
                        ServiceErrors.TRN_HEADER_SO_ID_NOT_FOUND.KEY));
    }

    @Override
    public void deleteById(Long id) {
        trnHeaderSoRepository.deleteById(id);
    }

    /*
     * Method to get generate new TransactionUID
     * @Return String
     */
    @Override
    public String generateTransactionUid() {
        log.info("ENTRY - generate new TransactionUID");
        List<TrnHeaderAsn> trnHeaderAsnList = trnHeaderAsnRepository.findAll();
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

}
