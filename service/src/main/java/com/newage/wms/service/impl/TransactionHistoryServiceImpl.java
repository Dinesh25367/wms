package com.newage.wms.service.impl;

import com.newage.wms.entity.*;
import com.newage.wms.repository.ConfigurationMasterRepository;
import com.newage.wms.repository.TransactionHistoryRepository;
import com.newage.wms.service.GrnDetailService;
import com.newage.wms.service.TransactionHistoryService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private ConfigurationMasterRepository configurationMasterRepository;

    @Autowired
    private GrnDetailService grnDetailService;

    private String completed="COMPLETED";

    private String generated= "GENERATED";


    @Override
    public List<TransactionHistory> findAll(Predicate predicate, Pageable pageable) {
        return transactionHistoryRepository.findAll(predicate,pageable).getContent();
    }

    @Override
    public List<TransactionHistory> save(List<TransactionHistory> transactionHistoryList) {
        return transactionHistoryRepository.saveAll(transactionHistoryList);
    }

    /*
     * Method to get generate new TaskID
     * @Return String
     */
    @Override
    public String generateTaskId() {
        log.info("ENTRY - generate new TaskID");
        List<TransactionHistory> transactionHistoryListAll = transactionHistoryRepository.findAll();
        List<TransactionHistory> transactionHistoryList = transactionHistoryListAll.stream()
                .filter(header -> header.getTaskId().startsWith("TASK"))
                .collect(Collectors.toList());
        List<ConfigurationMaster> configurationMasterList = configurationMasterRepository.findAll();
        List<ConfigurationMaster> taskConfigurationList = configurationMasterList.stream()                               //get Transaction UID format from configuration master
                .filter(configuration -> configuration.getModule().equals("Transaction task id"))
                .collect(Collectors.toList());
        String taskFormat = getTaskFormat(taskConfigurationList);
        if (transactionHistoryList.isEmpty()){
            return "TASK"+taskFormat+"00001";                                                                             //set default TransactionUID
        }
        else {                                                                                                          //get maximum TransactionUID and increment by 1
            TransactionHistory maxTransactionHistory = transactionHistoryList.stream()
                    .filter(transactionHistory -> transactionHistory.getTaskId().startsWith("TASK"))
                    .max(Comparator.comparing(
                            transactionHistory -> transactionHistory.getTaskId().
                                    substring(transactionHistory.getTaskId().length() - 5)
                    ))
                    .orElse(transactionHistoryList.get(0));
            String maxTaskId = maxTransactionHistory.getTaskId();
            if (maxTaskId.isEmpty()){
                return "TASK"+taskFormat+"00001";
            }else {
                String maxTaskIdNumericPart = maxTaskId.
                        substring(maxTaskId.length() - 5);
                try {
                    Integer newTaskIdNumericPart = Integer.parseInt(maxTaskIdNumericPart) + 1;
                    String newTaskIdNumericPartWithZeroes = String.format("%05d", newTaskIdNumericPart);
                    return "TASK" +taskFormat+ newTaskIdNumericPartWithZeroes;
                } catch (NumberFormatException e) {
                    // Handle parsing error, if any
                    throw new ServiceException(ServiceErrors.UNABLE_TO_GENERATE_TRANSACTION_UID.CODE,
                            ServiceErrors.UNABLE_TO_GENERATE_TRANSACTION_UID.KEY);
                }
            }
        }
    }

    @Override
    public List<TransactionHistory> filteredByGrnDetailId(Long grnDetailId) {
        return transactionHistoryRepository.findAll().stream()
                .filter(transactionHistory -> transactionHistory.getGrnDetail().getId().equals(grnDetailId) &&
                        transactionHistory.getInOut().equalsIgnoreCase("IN"))
                .sorted(Comparator.comparing(TransactionHistory::getTaskSlNo))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionHistory> findByGrnDetailId(Long grnDetailId) {
        return transactionHistoryRepository.findAll().stream()
                .filter(transactionHistory -> grnDetailId.equals(transactionHistory.getGrnDetail().getId()) &&
                        !transactionHistory.getTransactionStatus().equalsIgnoreCase("cancelled"))
                .sorted(Comparator.comparing(TransactionHistory::getTaskSlNo))
                .collect(Collectors.toList());
    }



    public String getTaskFormat( List<ConfigurationMaster> taskConfigurationList){
        LocalDate currentDate = LocalDate.now();
        String taskFormat = "";
        if (!CollectionUtils.isEmpty(taskConfigurationList)){                                                            //add day,month and year based on configuration master
            String day= "";
            String month = "";
            String year = "";
            for (ConfigurationMaster configurationMaster : taskConfigurationList){
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("transaction_history_taskid_day_included") && configurationMaster.getValue().equals("Yes")){
                    day = String.format("%02d", currentDate.getDayOfMonth());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("transaction_history_taskid_month_included") && configurationMaster.getValue().equals("Yes")){
                    month = String.format("%02d", currentDate.getMonthValue());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("transaction_history_taskid_year_included") && configurationMaster.getValue().equals("Yes")){
                    year = String.format("%02d", currentDate.getYear()%100);
                }
            }
            taskFormat= year+month+day;
            return taskFormat;
        }
        return taskFormat;
    }

    @Override
    public Boolean putAwayCancelRights(Long userId) {
        List<ConfigurationMaster> configurationMasterList = configurationMasterRepository.findAll();

        return configurationMasterList.stream()
                .anyMatch(configuration ->configuration.getAuthUserProfile()!=null &&configuration.getAuthUserProfile().getId().equals(userId)&&configuration.getConfigurationFlag().equalsIgnoreCase("putawaycancelrights") && configuration.getValue().equalsIgnoreCase("Yes"));

    }

    @Override
    public String getPutAwayStatus(GrnHeader grnHeader) {
        // Default status is "IN-PROGRESS"
        String status = "INPROGRESS";

        // Assuming transactionHistoryRepository.findAll() returns the list of all transaction histories
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();

        // Calculate the total receiving quantity
        double totalGrnDetailQty = grnHeader.getGrnDetailList().stream()
                .mapToDouble(GrnDetail::getReceivingQty)
                .sum();

        // Calculate the total put away quantity
        double putAwayQty = transactionHistoryList.stream()
                .filter(transactionHistory ->
                        grnHeader.getGrnDetailList().stream()
                                .anyMatch(grnDetail ->
                                        grnDetail.getId().equals(transactionHistory.getGrnDetail().getId())) &&
                                transactionHistory.getTransactionStatus().equalsIgnoreCase(generated) &&
                                transactionHistory.getTaskStatus().equalsIgnoreCase(completed))
                .mapToDouble(TransactionHistory::getQty)
                .sum();

        // Determine the status based on the comparison of total quantities
        if (totalGrnDetailQty == putAwayQty) {
            status = completed;
        } else if (putAwayQty == 0.0) {
            status = "PENDING";
        }

        return status;
    }



    @Override
    public Optional<TransactionHistory> getPutAwayTaskForGRNDetailIfExists(Long grnId) {
        return transactionHistoryRepository.findAll().stream()
                .filter(transactionHistory -> transactionHistory.getGrnDetail().getGrnHeader().getId().equals(grnId))
                .findFirst();
    }

    @Override
    public Double getPutAwayQty(Long grnDetailId){
        return transactionHistoryRepository.findAll().stream()
                .filter(transactionHistory ->
                        transactionHistory.getGrnDetail().getId().equals(grnDetailId) &&
                                transactionHistory.getTransactionStatus().equalsIgnoreCase(generated) &&
                                transactionHistory.getTaskStatus().equalsIgnoreCase(completed)
                )
                .mapToDouble(TransactionHistory::getQty) // Assuming 'putawayQty' is the property you want
                .sum(); // Calculate the sum of 'putawayQty'
    }

    @Override
    public Double getPutAwayVolume(Long grnDetailId){
        return transactionHistoryRepository.findAll().stream()
                .filter(transactionHistory ->
                        transactionHistory.getGrnDetail().getId().equals(grnDetailId) &&
                                transactionHistory.getTransactionStatus().equalsIgnoreCase(generated) &&
                                transactionHistory.getTaskStatus().equalsIgnoreCase(completed)
                )
                .mapToDouble(TransactionHistory::getVolume) // Assuming 'volume' is the property you want
                .sum(); // Calculate the sum of 'volume'
    }

    @Override
    public Double getPutAwayWeight(Long grnDetailId){
        return transactionHistoryRepository.findAll().stream()
                .filter(transactionHistory ->
                        transactionHistory.getGrnDetail().getId().equals(grnDetailId) &&
                                transactionHistory.getTransactionStatus().equalsIgnoreCase(generated) &&
                                transactionHistory.getTaskStatus().equalsIgnoreCase(completed)
                )
                .mapToDouble(TransactionHistory::getNetWeight) // Assuming 'weight' is the property you want
                .sum(); // Calculate the sum of 'weight'
    }

    @Override
    public String checkIfAtLeastOneGrnDetailIsGenerated(GrnHeader grnHeader) {
        List<GrnDetail> grnDetailList = grnHeader.getGrnDetailList();
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        // Check if the grnDetailList or transactionHistoryList is null or empty
        if (grnDetailList == null || grnDetailList.isEmpty() || transactionHistoryList.isEmpty()) {
            return "No";
        }

        for (GrnDetail grnDetail : grnDetailList){

            for (TransactionHistory transactionHistory : transactionHistoryList){
                if (grnDetail.getId().equals(transactionHistory.getGrnDetail().getId())
                        && transactionHistory.getTaskStatus().equalsIgnoreCase(completed)
                        && transactionHistory.getTransactionStatus().equalsIgnoreCase(generated)){
                    return "No";
                }
            }

        }
        return "Yes";
    }

    @Override
    public List<TransactionHistory> findFilteredData(Long grnDetailId, Integer taskSlNo) {
        // Filter the list based on the provided grnDetailId and taskSlNo
        return transactionHistoryRepository.findAll().stream()
                .filter(history -> grnDetailId.equals(history.getGrnDetail().getId()) && taskSlNo.equals(history.getTaskSlNo()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPopUpRights(Long grnDetailId) {
        GrnDetail grnDetail = grnDetailService.getById(grnDetailId);
        Double grnDetailQty = grnDetail.getReceivingQty();
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll().stream()
                .filter(transactionHistory -> transactionHistory.getGrnDetail() != null
                        && grnDetailId.equals(transactionHistory.getGrnDetail().getId())
                        && !"CANCELLED".equalsIgnoreCase(transactionHistory.getTaskStatus())
                        && "IN".equalsIgnoreCase(transactionHistory.getInOut()))
                .sorted(Comparator.comparing(TransactionHistory::getTaskSlNo))
                .collect(Collectors.toList());

        // Check if transactionHistoryList has at least two elements and if the second element is not null,
        // then compare the quantity
        return !transactionHistoryList.isEmpty() &&
                transactionHistoryList.size() > 1 &&
                transactionHistoryList.get(1) != null &&
                Objects.equals(grnDetailQty, transactionHistoryList.get(1).getQty());
    }

}
