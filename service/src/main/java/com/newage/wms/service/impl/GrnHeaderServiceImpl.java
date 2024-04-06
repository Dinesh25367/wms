package com.newage.wms.service.impl;

import com.newage.wms.entity.*;
import com.newage.wms.entity.QGrnHeader;
import com.newage.wms.repository.ConfigurationMasterRepository;
import com.newage.wms.repository.GrnHeaderRepository;
import com.newage.wms.repository.TransactionHistoryRepository;
import com.newage.wms.repository.TrnHeaderAsnRepository;
import com.newage.wms.service.GrnHeaderService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class GrnHeaderServiceImpl implements GrnHeaderService {

    @Autowired
    private GrnHeaderRepository grnHeaderRepository;

    @Autowired
    private TrnHeaderAsnRepository trnHeaderAsnRepository;

    @Autowired
    private ConfigurationMasterRepository configurationMasterRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    private String direct = "DIRECT";

    private String grn = "GRN";

    private String received = "Received";

    private String partReceived = "Part Received";

    private String number00001 = "00001";

    /*
     * Method to get all GrnHeader
     * @return Page<GrnHeader>
     */
    @Override
    public Page<GrnHeader> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all GrnHeader");
        return grnHeaderRepository.findAll(predicate,pageable);
    }


    /*
     * Method to get all GrnHeader for autocomplete
     * @Return Iterable<GrnHeader>
     */
    @Override
    public Iterable<GrnHeader> findAllAutoComplete(Predicate predicate, Pageable pageable) {
        return grnHeaderRepository.findAll(predicate,pageable);
    }

    /*
     * Method to save GrnHeader
     * @Return GrnHeader
     */
    @Override
    public GrnHeader save(GrnHeader grnHeader) {
        log.info("ENTRY-EXIT - Save GrnHeader ");
        return grnHeaderRepository.save(grnHeader);
    }

    /*
     *Method to update GrnHeader
     * @Return GrnHeader
     */
    @Override
    public GrnHeader update(GrnHeader grnHeader) {
        log.info("ENTRY-EXIT - Update GrnHeader by Id");
        return grnHeaderRepository.save(grnHeader);
    }

    /*
     *Method to get GrnHeader by id
     * @Return GrnHeader
     */
    @Override
    public GrnHeader findById(Long id) {
        log.info("ENTRY-EXIT - Update GrnHeader by id");
        return grnHeaderRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.GRN_HEADER_MASTER_ID_NOT_FOUND.CODE,
                        ServiceErrors.GRN_HEADER_MASTER_ID_NOT_FOUND.KEY));
    }

    @Override
    public String generateTransactionUidDirect() {
        log.info("ENTRY - generate new TransactionUID");
        List<TrnHeaderAsn> trnHeaderAsnListAll = trnHeaderAsnRepository.findAll();
        List<TrnHeaderAsn> trnHeaderAsnList = trnHeaderAsnListAll.stream()
                .filter(header -> header.getTransactionUid().startsWith(direct))
                .collect(Collectors.toList());
        List<ConfigurationMaster> configurationMasterList = configurationMasterRepository.findAll();
        List<ConfigurationMaster> asnConfigurationList = configurationMasterList.stream()                               //get Transaction UID format from configuration master
                .filter(configuration -> configuration.getModule().equals("DIRECT trans uid"))
                .collect(Collectors.toList());
        String asnFormat = getAsnFormat(asnConfigurationList);
        if (trnHeaderAsnList.isEmpty()){
            return direct+asnFormat+number00001;                                                                             //set default TransactionUID
        }
        else {                                                                                                          //get maximum TransactionUID and increment by 1
            TrnHeaderAsn maxTrnHeaderAsn = trnHeaderAsnList.stream()
                    .filter(trnHeader -> trnHeader.getTransactionUid().startsWith(direct))
                    .max(Comparator.comparing(
                            trnHeader -> trnHeader.getTransactionUid().
                                    substring(trnHeader.getTransactionUid().length() - 5)
                    ))
                    .orElse(trnHeaderAsnList.get(0));
            String maxTransactionUid = maxTrnHeaderAsn.getTransactionUid();
            if (maxTransactionUid.isEmpty()){
                return direct+asnFormat+number00001;
            }else {
                String maxTransactionUidNumericPart = maxTransactionUid.
                        substring(maxTransactionUid.length() - 5);
                try {
                    Integer newTransactionUidNumericPart = Integer.parseInt(maxTransactionUidNumericPart) + 1;
                    String newTransactionUidNumericPartWithZeroes = String.format("%05d", newTransactionUidNumericPart);
                    return direct+asnFormat+ newTransactionUidNumericPartWithZeroes;
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
                        equalsIgnoreCase("direct_transuid_day_included") && configurationMaster.getValue().equals("Yes")){
                    day = String.format("%02d", currentDate.getDayOfMonth());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("direct_transuid_month_included") && configurationMaster.getValue().equals("Yes")){
                    month = String.format("%02d", currentDate.getMonthValue());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("direct_transuid_year_included") && configurationMaster.getValue().equals("Yes")){
                    year = String.format("%02d", currentDate.getYear()%100);
                }
            }
            asnFormat= year+month+day;
            return asnFormat;
        }
        return asnFormat;
    }

    @Override
    public String generateGrnReference() {
        log.info("ENTRY - generate new TransactionUID");
        List<GrnHeader> grnHeaderListAll = grnHeaderRepository.findAll();
        List<GrnHeader> grnHeaderList = grnHeaderListAll.stream()
                .filter(header -> header.getGrnRef() != null && header.getGrnRef().startsWith(grn))
                .collect(Collectors.toList());
        List<ConfigurationMaster> configurationMasterList = configurationMasterRepository.findAll();
        List<ConfigurationMaster> grnConfigurationList = configurationMasterList.stream()                               //get Transaction UID format from configuration master
                .filter(configuration -> configuration.getModule().equals("GRN reference"))
                .collect(Collectors.toList());
        String grnRefFormat = getGrnRefFormat(grnConfigurationList);
        if (grnHeaderList.isEmpty()){
            return grn+grnRefFormat+number00001;                                                                             //set default TransactionUID
        }
        else {                                                                                                          //get maximum TransactionUID and increment by 1
            GrnHeader maxGrnHeader = grnHeaderList.stream()
                    .filter(grnHeader -> grnHeader.getGrnRef().startsWith(grn))
                    .max(Comparator.comparing(
                            grnHeader -> grnHeader.getGrnRef().
                                    substring(grnHeader.getGrnRef().length() - 5)
                    ))
                    .orElse(grnHeaderList.get(0));
            String maxGrnRef = maxGrnHeader.getGrnRef();
            if (maxGrnRef.isEmpty()){
                return grn+grnRefFormat+number00001;
            }else {
                String maxGrnRefNumericPart = maxGrnRef.
                        substring(maxGrnRef.length() - 5);
                try {
                    Integer newGrnRefNumericPart = Integer.parseInt(maxGrnRefNumericPart) + 1;
                    String newGrnRefNumericPartWithZeroes = String.format("%05d", newGrnRefNumericPart);
                    return grn+grnRefFormat+ newGrnRefNumericPartWithZeroes;
                } catch (NumberFormatException e) {
                    // Handle parsing error, if any
                    throw new ServiceException(ServiceErrors.UNABLE_TO_GENERATE_GRN_REF.CODE,
                            ServiceErrors.UNABLE_TO_GENERATE_GRN_REF.KEY);
                }
            }
        }
    }

    private String getGrnRefFormat(List<ConfigurationMaster> grnConfigurationList) {
        LocalDate currentDate = LocalDate.now();
        String grnRefFormat = "";
        if (!CollectionUtils.isEmpty(grnConfigurationList)){                                                            //add day,month and year based on configuration master
            String day= "";
            String month = "";
            String year = "";
            for (ConfigurationMaster configurationMaster : grnConfigurationList){
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("grnref_day_included") && configurationMaster.getValue().equals("Yes")){
                    day = String.format("%02d", currentDate.getDayOfMonth());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("grnref_transuid_month_included") && configurationMaster.getValue().equals("Yes")){
                    month = String.format("%02d", currentDate.getMonthValue());
                }
                if (configurationMaster.getConfigurationFlag().
                        equalsIgnoreCase("grnref_transuid_year_included") && configurationMaster.getValue().equals("Yes")){
                    year = String.format("%02d", currentDate.getYear()%100);
                }
            }
            grnRefFormat= year+month+day;
            return grnRefFormat;
        }
        return grnRefFormat;
    }

    @Override
    public boolean checkIfAsnCanBeCancelledFromGrn(Long trnId) {
        Collection<Predicate> predicates = new ArrayList<>();
        Collection<Predicate> predicatesOne = new ArrayList<>();
        predicates.add(QGrnHeader.grnHeader.id.isNotNull());
        predicatesOne.add(QGrnHeader.grnHeader.id.isNotNull());
        if (trnId != null){
            predicates.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(trnId));
            predicatesOne.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(trnId));
        }
        Predicate predicateOneAll = ExpressionUtils.allOf(predicatesOne);
        List<GrnHeader> grnHeaderList = grnHeaderRepository.findAll(predicateOneAll,Pageable.unpaged()).getContent().stream()
                .collect(Collectors.toList());
        if (grnHeaderList.isEmpty()){
            return true;
        }
        predicates.add(QGrnHeader.grnHeader.status.containsIgnoreCase("Completed")
                .or(QGrnHeader.grnHeader.status.containsIgnoreCase(received)
                        .or(QGrnHeader.grnHeader.status.containsIgnoreCase(partReceived))));
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        return grnHeaderRepository.findAll(predicateAll,Pageable.unpaged()).isEmpty();
    }

    @Override
    public boolean isCancelled(Long id) {
        Collection<Predicate> predicates = new ArrayList<>();
        if (id != null) {
            predicates.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(id).
                    and(QGrnHeader.grnHeader.status.equalsIgnoreCase("cancelled")).
                    and(QGrnHeader.grnHeader.trnHeaderAsnMaster.transactionStatus.containsIgnoreCase("received")));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        return !grnHeaderRepository.findAll(predicateAll,Pageable.unpaged()).isEmpty();
    }

    @Override
    public boolean isUnCompletedGrnPresent(Long id) {
        Collection<Predicate> predicates = new ArrayList<>();
        if (id != null) {
            predicates.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(id)
                    .and(QGrnHeader.grnHeader.status.containsIgnoreCase("received")));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnHeader> grnHeaderList = grnHeaderRepository.findAll(predicateAll,Pageable.unpaged()).getContent();
        return grnHeaderList.isEmpty();
    }

    @Override
    public boolean checkIfAsnHeaderCanBeEditableFromGrn(Long trnId) {
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QGrnHeader.grnHeader.id.isNotNull());
        if (trnId != null){
            predicates.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(trnId));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnHeader> grnHeaderList = grnHeaderRepository.findAll(predicateAll,Pageable.unpaged()).getContent();
        if (!grnHeaderList.isEmpty()){
            return grnHeaderList.stream().allMatch(grnHeader -> "completed".equals(grnHeader.getStatus()));
        }else {
            return false;
        }
    }

    @Override
    public GrnHeader getIncompleteGrn(Long transactionId) {
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QGrnHeader.grnHeader.id.isNotNull());
        if (transactionId != null){
            predicates.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(transactionId));
        }
        predicates.add((QGrnHeader.grnHeader.status.containsIgnoreCase(received)
                .or(QGrnHeader.grnHeader.status.containsIgnoreCase(partReceived))));
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnHeader> grnHeaderList = grnHeaderRepository.findAll(predicateAll,Pageable.unpaged()).getContent();
        if (!CollectionUtils.isEmpty(grnHeaderList)){
            return grnHeaderList.get(0);
        }
        return null;
    }

    @Override
    public List<GrnDetail> getCompletedGrnListForAsn(Long transactionId) {
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QGrnHeader.grnHeader.id.isNotNull());
        if (transactionId != null){
            predicates.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(transactionId));
        }
        predicates.add(QGrnHeader.grnHeader.status.equalsIgnoreCase("completed"));
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnHeader> grnHeaderList = grnHeaderRepository.findAll(predicateAll,Pageable.unpaged()).getContent();
        List<GrnDetail> grnDetailList = new ArrayList<>();
        for (GrnHeader grnHeader : grnHeaderList){
            grnDetailList.addAll(grnHeader.getGrnDetailList());
        }
        return grnDetailList;
    }

    @Override
    public void validateGrnForSave(Long transactionId) {
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QGrnHeader.grnHeader.id.isNotNull());
        predicates.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(transactionId));
        predicates.add((QGrnHeader.grnHeader.status.containsIgnoreCase(received)
                .or(QGrnHeader.grnHeader.status.containsIgnoreCase(partReceived))));
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnHeader> grnHeaderList = grnHeaderRepository.findAll(predicateAll,Pageable.unpaged()).getContent();
        if (!CollectionUtils.isEmpty(grnHeaderList)){
            throw new ServiceException(ServiceErrors.COMPLETE_ALREADY_EXISTING_GRN.CODE,
                    ServiceErrors.COMPLETE_ALREADY_EXISTING_GRN.KEY);
        }

    }

    @Override
    public void validateGrnForUpdate(Long transactionId,Long grnId) {
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QGrnHeader.grnHeader.id.isNotNull());
        predicates.add(QGrnHeader.grnHeader.trnHeaderAsnMaster.id.eq(transactionId));
        predicates.add((QGrnHeader.grnHeader.status.containsIgnoreCase(received)
                .or(QGrnHeader.grnHeader.status.containsIgnoreCase(partReceived))));
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnHeader> grnHeaderList = grnHeaderRepository.findAll(predicateAll,Pageable.unpaged()).getContent();
        if (!CollectionUtils.isEmpty(grnHeaderList)){
            Boolean codeExists = grnHeaderList.stream()
                    .allMatch(grnHeader -> (grnHeader.getId().equals(grnId)));
            if (!codeExists){
                throw new ServiceException(ServiceErrors.COMPLETE_ALREADY_EXISTING_GRN.CODE,
                        ServiceErrors.COMPLETE_ALREADY_EXISTING_GRN.KEY);
            }
        }
    }

    @Override
    public String getPutAwayTaskStatus(GrnHeader grnHeader) {
        String status = "";
        List<TransactionHistory> transHistoryDetail = transactionHistoryRepository.findAll();
        for (GrnDetail grnDetail : grnHeader.getGrnDetailList()) {
            double totQty = transHistoryDetail.stream()
                    .filter(transactionHistory -> Objects.equals(transactionHistory.getGrnDetail().getId(), grnDetail.getId()))
                    .filter(transactionHistory -> transactionHistory.getTransactionStatus().equalsIgnoreCase("GENERATED"))
                    .filter(transactionHistory -> transactionHistory.getTaskStatus().equalsIgnoreCase("COMPLETED"))
                    .mapToDouble(TransactionHistory::getQty)
                    .sum();
            status = (totQty == grnDetail.getReceivingQty()) ? "Complete" : "Pending";
            if (status.equalsIgnoreCase("Pending")) {
                break;
            }
        }
        return status;
    }

}
