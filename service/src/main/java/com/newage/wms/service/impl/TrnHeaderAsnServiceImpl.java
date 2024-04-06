package com.newage.wms.service.impl;

import com.newage.wms.entity.ConfigurationMaster;
import com.newage.wms.entity.QTrnHeaderAsn;
import com.newage.wms.entity.TrnDetail;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.repository.ConfigurationMasterRepository;
import com.newage.wms.repository.TrnHeaderAsnRepository;
import com.newage.wms.service.TrnHeaderAsnService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TrnHeaderAsnServiceImpl implements TrnHeaderAsnService {

    @Autowired
    private TrnHeaderAsnRepository trnHeaderAsnRepository;

    @Autowired
    private ConfigurationMasterRepository configurationMasterRepository;

    /*
     * Method to get all TrnHeaderAsn with pagination, sort and filter
     * @return Page<TrnHeaderAsn>
     */
    @Override
    public Page<TrnHeaderAsn> findAll(Predicate predicate, Pageable pageable,
                                      Date fromDate, Date toDate,String dateFilter) {
        log.info("ENTRY-EXIT - List all TrnHeaderAsn with pagination, sort and filter");
        QTrnHeaderAsn trnHeaderAsn = QTrnHeaderAsn.trnHeaderAsn;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().atStartOfDay();
        Date d2 = Date.from(midnight.atZone(ZoneId.of("Asia/Kolkata")).toInstant());
        Date dt1 = null;
        Date dt2 = null;
        boolean isdatefilter = false;
        if (dateFilter != null) {
            isdatefilter = true;
            if (dateFilter.equalsIgnoreCase("TODAY")) {
                dt1 = DateUtils.addDays(d2, 0);
                dt2 = DateUtils.addDays(d2, 1);
            } else if (dateFilter.equalsIgnoreCase("LAST7DAYS")) {
                dt1 = DateUtils.addDays(d2, -7);
                dt2 = DateUtils.addDays(d2, 1);
            } else if (dateFilter.equalsIgnoreCase("LAST15DAYS")) {
                dt1 = DateUtils.addDays(d2, -15);
                dt2 = DateUtils.addDays(d2, 1);
            } else if (dateFilter.equalsIgnoreCase("LAST30DAYS")) {
                dt1 = DateUtils.addDays(d2, -30);
                dt2 = DateUtils.addDays(d2, 1);
            }
        }
        if (fromDate != null && toDate != null) {
            isdatefilter = true;
            dt1 = DateUtils.addDays(fromDate, 0);
            dt2 = DateUtils.addDays(toDate, 1);
        }
        Predicate predicate1;
        if (isdatefilter) {
            predicate1 = trnHeaderAsn.expectedReceivingDate.between(dt1, dt2);
            Collection<Predicate> predicates = new ArrayList<>();
            predicates.add(predicate);
            predicates.add(predicate1);
            Predicate predicateAll = ExpressionUtils.allOf(predicates);
            return trnHeaderAsnRepository.findAll(predicateAll, pageable);
        } else {
            return trnHeaderAsnRepository.findAll(predicate, pageable);
        }
    }

    @Override
    public Page<TrnHeaderAsn> findAll(Predicate predicate, Pageable pageable) {
        return trnHeaderAsnRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save TrnHeaderAsn
     * @Return TrnHeaderAsn
     */
    @Override
    public TrnHeaderAsn save(TrnHeaderAsn trnHeaderAsn) {
        log.info("ENTRY-EXIT - Save TrnHeaderAsn ");
        return trnHeaderAsnRepository.save(trnHeaderAsn);
    }

    /*
     * Method to update TrnHeaderAsn
     * @Return TrnHeaderAsn
     */
    @Override
    public TrnHeaderAsn update(TrnHeaderAsn trnHeaderAsn) {
        log.info("ENTRY-EXIT - Update TrnHeaderAsn ");
        return trnHeaderAsnRepository.save(trnHeaderAsn);
    }

    /*
     * Method to get TrnHeaderAsn by id
     * @Return TrnHeaderAsn
     */
    @Override
    public TrnHeaderAsn findById(Long id) {
        log.info("ENTRY-EXIT - Get TrnHeaderAsn by id");
        return trnHeaderAsnRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.TRN_HEADER_ID_NOT_FOUND.CODE,
                        ServiceErrors.TRN_HEADER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get generate new TransactionUID
     * @Return String
     */
    @Override
    public String generateTransactionUid() {
        log.info("ENTRY - generate new TransactionUID");
        List<TrnHeaderAsn> trnHeaderAsnListAll = trnHeaderAsnRepository.findAll();
        List<TrnHeaderAsn> trnHeaderAsnList = trnHeaderAsnListAll.stream()
                .filter(header -> header.getTransactionUid().startsWith("ASN"))
                .collect(Collectors.toList());
        List<ConfigurationMaster> configurationMasterList = configurationMasterRepository.findAll();
        List<ConfigurationMaster> asnConfigurationList = configurationMasterList.stream()                               //get Transaction UID format from configuration master
                .filter(configuration -> configuration.getModule().equals("ASN trans uid"))
                .collect(Collectors.toList());
        String asnFormat = getAsnFormat(asnConfigurationList);
        if (trnHeaderAsnList.isEmpty()){
            return "ASN"+asnFormat+"00001";                                                                             //set default TransactionUID
        }
        else {                                                                                                          //get maximum TransactionUID and increment by 1
            TrnHeaderAsn maxTrnHeaderAsn = trnHeaderAsnList.stream()
                    .filter(trnHeader -> trnHeader.getTransactionUid().startsWith("ASN"))
                    .max(Comparator.comparing(
                            trnHeader -> trnHeader.getTransactionUid().
                                    substring(trnHeader.getTransactionUid().length() - 5)
                    ))
                    .orElse(trnHeaderAsnList.get(0));
            String maxTransactionUid = maxTrnHeaderAsn.getTransactionUid();
            if (maxTransactionUid.isEmpty()){
                return "ASN"+asnFormat+"00001";
            }else {
                String maxTransactionUidNumericPart = maxTransactionUid.
                        substring(maxTransactionUid.length() - 5);
                try {
                    Integer newTransactionUidNumericPart = Integer.parseInt(maxTransactionUidNumericPart) + 1;
                    String newTransactionUidNumericPartWithZeroes = String.format("%05d", newTransactionUidNumericPart);
                    return "ASN" +asnFormat+ newTransactionUidNumericPartWithZeroes;
                } catch (NumberFormatException e) {
                    // Handle parsing error, if any
                    throw new ServiceException(ServiceErrors.UNABLE_TO_GENERATE_TRANSACTION_UID.CODE,
                            ServiceErrors.UNABLE_TO_GENERATE_TRANSACTION_UID.KEY);
                }
            }
        }
    }

    /*
     * Method to validate unique customerOrderNo attributes in Save
     */
    @Override
    public void validateUniqueCustomerOrderNoSave(String customerOrderNo,Long warehouseId,Long customerId) {
        log.info("ENTRY - validate unique customerOrderNo in save");
        List<TrnHeaderAsn> trnHeaderAsnList = trnHeaderAsnRepository.findAll();
        Boolean codeExists = trnHeaderAsnList.stream()
                .anyMatch(trnHeaderAsn -> trnHeaderAsn.getCustomerOrderNo() != null && trnHeaderAsn.getCustomerOrderNo().equals(customerOrderNo)&& trnHeaderAsn.getWareHouseMaster().getId().equals(warehouseId)&&trnHeaderAsn.getCustomerMaster().getId().equals(customerId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.CUSTOMER_ORDER_NO_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.CUSTOMER_ORDER_NO_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique customerOrderNo attributes in Update
     */
    @Override
    public void validateUniqueCustomerOrderNoUpdate(String customerOrderNo, Long id,Long warehouseId,Long customerId) {
        log.info("ENTRY - validate unique customerOrderNo in update");
        List<TrnHeaderAsn> trnHeaderAsnList = trnHeaderAsnRepository.findAll();
        Boolean codeExists = trnHeaderAsnList.stream()
                .anyMatch(trnHeaderAsn -> trnHeaderAsn.getCustomerOrderNo() != null && trnHeaderAsn.getCustomerOrderNo().equals(customerOrderNo) && !trnHeaderAsn.getId().equals(id)&&trnHeaderAsn.getWareHouseMaster().getId().equals(warehouseId)&&trnHeaderAsn.getCustomerMaster().getId().equals(customerId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.CUSTOMER_ORDER_NO_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.CUSTOMER_ORDER_NO_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique customerOrderNo attributes in Save
     */
    @Override
    public void validateUniqueCustomerInvoiceNoSave(String customerInvoiceNo,Long warehouseId,Long customerId) {
        log.info("ENTRY - validate unique CustomerInvoiceNo in save");
        List<TrnHeaderAsn> trnHeaderAsnList = trnHeaderAsnRepository.findAll();
        Boolean codeExists = trnHeaderAsnList.stream()
                .anyMatch(trnHeaderAsn -> trnHeaderAsn.getCustomerInvoiceNo() != null && trnHeaderAsn.getCustomerInvoiceNo().equals(customerInvoiceNo)&&trnHeaderAsn.getWareHouseMaster().getId().equals(warehouseId)&&trnHeaderAsn.getCustomerMaster().getId().equals(customerId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.CUSTOMER_INVOICE_NO_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.CUSTOMER_INVOICE_NO_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique customerOrderNo attributes in Update
     */
    @Override
    public void validateUniqueCustomerInvoiceNoUpdate(String customerInvoiceNo, Long id,Long warehouseId,Long customerId) {
        log.info("ENTRY - validate unique customerInvoiceNo in update");
        List<TrnHeaderAsn> trnHeaderAsnList = trnHeaderAsnRepository.findAll();
        Boolean codeExists = trnHeaderAsnList.stream()
                .anyMatch(trnHeaderAsn -> trnHeaderAsn.getCustomerInvoiceNo() != null && trnHeaderAsn.getCustomerInvoiceNo().equals(customerInvoiceNo) && !trnHeaderAsn.getId().equals(id)&&trnHeaderAsn.getWareHouseMaster().getId().equals(warehouseId)&&trnHeaderAsn.getCustomerMaster().getId().equals(customerId));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.CUSTOMER_INVOICE_NO_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.CUSTOMER_INVOICE_NO_SHOULD_BE_UNIQUE.KEY);
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
                        equalsIgnoreCase("asn_transuid_day_included") && configurationMaster.getValue().equals("Yes")){
                    day = String.format("%02d", currentDate.getDayOfMonth());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("asn_transuid_month_included") && configurationMaster.getValue().equals("Yes")){
                    month = String.format("%02d", currentDate.getMonthValue());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("asn_transuid_year_included") && configurationMaster.getValue().equals("Yes")){
                    year = String.format("%02d", currentDate.getYear()%100);
                }
            }
            asnFormat= year+month+day;
            return asnFormat;
        }
        return asnFormat;
    }

    @Override
    public String getTransactionStatus(TrnHeaderAsn trnHeaderAsn) {
        List<TrnDetail> trnDetailList = trnHeaderAsn.getTrnDetailList();
        if (!CollectionUtils.isEmpty(trnDetailList)){
            int zeroQtyCounter = 0;
            for (TrnDetail trnDetail : trnDetailList){
                double expQty = trnDetail.getExpQty();
                double actualQty = trnDetail.getTrnDetailAsn().getActualQty();
                if (expQty> actualQty && actualQty > 0){
                    return "PART RECEIVED";
                }
                if (actualQty == 0.0){
                    zeroQtyCounter++;
                }
            }
            if (trnDetailList.size() == zeroQtyCounter){
                return "BOOKED";
            }
            return "RECEIVED";
        }
        return "BOOKED";
    }

}
