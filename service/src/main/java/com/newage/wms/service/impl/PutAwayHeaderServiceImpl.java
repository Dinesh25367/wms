package com.newage.wms.service.impl;

import com.newage.wms.entity.ConfigurationMaster;
import com.newage.wms.entity.PutAwayTaskHeader;
import com.newage.wms.repository.ConfigurationMasterRepository;
import com.newage.wms.repository.PutAwayHeaderRepository;
import com.newage.wms.service.PutAwayHeaderService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PutAwayHeaderServiceImpl implements PutAwayHeaderService {

    @Autowired
    private PutAwayHeaderRepository putAwayHeaderRepository;

    @Autowired
    private ConfigurationMasterRepository configurationMasterRepository;


    @Override
    public PutAwayTaskHeader save(PutAwayTaskHeader putAwayTaskHeader) {
        return putAwayHeaderRepository.save(putAwayTaskHeader);
    }

    @Override
    public PutAwayTaskHeader findById(Long id) {
        return putAwayHeaderRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.PUTAWAY_HEADER_NOT_FOUND.CODE,
                        ServiceErrors.PUTAWAY_HEADER_NOT_FOUND.KEY));
    }

    @Override
    public List<PutAwayTaskHeader> fetchAll() {
        return putAwayHeaderRepository.findAll();
    }

    @Override
    public Optional<PutAwayTaskHeader> getPutAwayTaskForGRNIfExists(Long grnId) {
        return putAwayHeaderRepository.findAll().stream()
                .filter(putAwayTaskHeader -> putAwayTaskHeader.getGrnHeader().getId().equals(grnId))
                .findFirst();
    }

    @Override
    public String generatePutAwayTaskId() {
        log.info("ENTRY - generate new TransactionUID");
        List<PutAwayTaskHeader> putAwayTaskHeaders = putAwayHeaderRepository.findAll();
        List<PutAwayTaskHeader> putAwayHeaderList = putAwayTaskHeaders.stream()
                .filter(header -> header.getTaskId().startsWith("TASK"))
                .collect(Collectors.toList());
        String putAwayFormat = getPutAwayTaskIdFormat();
        if (putAwayHeaderList.isEmpty()){
            return "TASK"+putAwayFormat+"00001";                                                                             //set default TransactionUID
        }
        else {                                                                                                          //get maximum TransactionUID and increment by 1
            PutAwayTaskHeader maxPutAwayTaskHeader = putAwayHeaderList.stream()
                    .filter(trnHeader -> trnHeader.getTaskId().startsWith("TASK"))
                    .max(Comparator.comparing(
                            trnHeader -> trnHeader.getTaskId().
                                    substring(trnHeader.getTaskId().length() - 5)
                    ))
                    .orElse(putAwayHeaderList.get(0));
            String maxTaskId = maxPutAwayTaskHeader.getTaskId();
            if (maxTaskId.isEmpty()){
                return "TASK"+putAwayFormat+"00001";
            }else {
                String maxTaskIdNumericPart = maxTaskId.
                        substring(maxTaskId.length() - 5);
                try {
                    Integer newTransactionUidNumericPart = Integer.parseInt(maxTaskIdNumericPart) + 1;
                    String newTaskIdNumericPartWithZeroes = String.format("%05d", newTransactionUidNumericPart);
                    return "TASK" +putAwayFormat+ newTaskIdNumericPartWithZeroes;
                } catch (NumberFormatException e) {
                    // Handle parsing error, if any
                    throw new ServiceException(ServiceErrors.UNABLE_TO_GENERATE_TRANSACTION_UID.CODE,
                            ServiceErrors.UNABLE_TO_GENERATE_TRANSACTION_UID.KEY);
                }
            }
        }
    }

    public String getPutAwayTaskIdFormat(){
        List<ConfigurationMaster> configurationMasterList = configurationMasterRepository.findAll();
        List<ConfigurationMaster> asnConfigurationList = configurationMasterList.stream()                               //get Transaction UID format from configuration master
                .filter(configuration -> configuration.getModule().equalsIgnoreCase("PutAway Task ID"))
                .collect(Collectors.toList());
        LocalDate currentDate = LocalDate.now();
        String putAwayFormat = "";
        if (!CollectionUtils.isEmpty(asnConfigurationList)){                                                            //add day,month and year based on configuration master
            String day= "";
            String month = "";
            String year = "";
            for (ConfigurationMaster configurationMaster : asnConfigurationList){
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("putaway_taskid_day_included") && configurationMaster.getValue().equalsIgnoreCase("Yes")){
                    day = String.format("%02d", currentDate.getDayOfMonth());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("putaway_taskid_month_included") && configurationMaster.getValue().equalsIgnoreCase("Yes")){
                    month = String.format("%02d", currentDate.getMonthValue());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("putaway_taskid_year_included") && configurationMaster.getValue().equalsIgnoreCase("Yes")){
                    year = String.format("%02d", currentDate.getYear()%100);
                }
            }
            putAwayFormat= year+month+day;
            return putAwayFormat;
        }
        return putAwayFormat;
    }

}
