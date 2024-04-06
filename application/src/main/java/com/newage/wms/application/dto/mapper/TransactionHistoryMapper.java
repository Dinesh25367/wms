package com.newage.wms.application.dto.mapper;


import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.PutAwayPopUpRequestDTO;
import com.newage.wms.application.dto.requestdto.PutAwayRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.PutAwaySummaryResponseDTO;
import com.newage.wms.application.dto.responsedto.TransactionHistoryResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QTransactionHistory;
import com.newage.wms.service.*;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


@Log4j2
@Component
public class TransactionHistoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private GrnHeaderService grnHeaderService;

    @Autowired
    private CustomerMasterService customerMasterService;

    @Autowired
    private TrnHeaderAsnService trnHeaderAsnService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private UomMasterService uomMasterService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private SkuMasterService skuMasterService;

    @Autowired
    private GrnDetailService grnDetailService;

    final String Cancelled = "CANCELLED";
    final String Generated = "GENERATED";
    final String Completed = "COMPLETED";


    public List<PutAwaySummaryResponseDTO> getAll(Long grnId, String customerName, String customerOrderNo) {
        Collection<Predicate> predicates = new ArrayList<>();
        List<PutAwaySummaryResponseDTO> putAwaySummaryResponseDTOList = new ArrayList<>();
        predicates.add(QTransactionHistory.transactionHistory.id.isNotNull());
        predicates.add(QTransactionHistory.transactionHistory.transactionStatus.eq("Received"));
        predicates.add(QTransactionHistory.transactionHistory.transactionType.eq("ASN"));
        predicates.add(QTransactionHistory.transactionHistory.inOut.eq("IN"));
        if (grnId != null) {
            GrnHeader grnHeader = grnHeaderService.findById(grnId);
            predicates.add(QTransactionHistory.transactionHistory.sourceTransactionNo.eq(grnHeader.getGrnRef()));
        }
        if (customerName != null) {
            predicates.add(QTransactionHistory.transactionHistory.customerMaster.name.equalsIgnoreCase(customerName));
        }
        if (customerOrderNo != null) {
            predicates.add(QTransactionHistory.transactionHistory.sourceTrnHeaderAsnMaster.customerOrderNo.equalsIgnoreCase(customerOrderNo));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<TransactionHistory> transactionHistoryList = transactionHistoryService.findAll(predicateAll, Pageable.unpaged());
        Map<Long, List<TransactionHistory>> transactionHistoryMap = transactionHistoryList.stream()
                .collect(Collectors.groupingBy(th -> th.getCustomerMaster().getId()));
        for (Map.Entry<Long, List<TransactionHistory>> entry : transactionHistoryMap.entrySet()) {
            Long customerId = entry.getKey();
            List<TransactionHistory> transactionHistoryList1 = entry.getValue();
            putAwaySummaryResponseDTOList.add(generatePutAwayList(transactionHistoryList1, customerId));
        }
        return putAwaySummaryResponseDTOList;
    }

    private PutAwaySummaryResponseDTO generatePutAwayList(List<TransactionHistory> transactionHistoryList, Long customerId) {
        log.info("ENTRY - TransactionHistoryList to TransactionList mapper");
        PutAwaySummaryResponseDTO putAwaySummaryResponseDTO = new PutAwaySummaryResponseDTO();
        CustomerMaster customer = customerMasterService.getCustomerById(customerId);
        putAwaySummaryResponseDTO.setCustomer(mapToDTOIfNotNull(customer, PutAwaySummaryResponseDTO.CustomerDTO.class));
        Set<String> uniqueCodes = new HashSet<>();
        Set<String> uniqueCustomerOrderNo = new HashSet<>();
        Set<String> uniqueSkuCode = new HashSet<>();
        Set<String> uniqueLpnId = new HashSet<>();
        Set<String> uniqueCartonId = new HashSet<>();
        Set<Double> uniqueVolume = new HashSet<>();
        Set<String> uniqueStatus = new HashSet<>();
        for (TransactionHistory transactionHistory : transactionHistoryList) {
            putAwaySummaryResponseDTO.setOrderNo(transactionHistory.getTrnHeaderAsnMaster().getCustomerOrderNo());
            uniqueCodes.add(transactionHistory.getCustomerMaster().getCode());
            uniqueCustomerOrderNo.add(transactionHistory.getTrnHeaderAsnMaster().getCustomerOrderNo());
            uniqueSkuCode.add(transactionHistory.getSkuMaster().getCode());
            uniqueLpnId.add(transactionHistory.getLpnId());
            uniqueCartonId.add(transactionHistory.getCartonId());
            uniqueVolume.add(transactionHistory.getVolume());
            uniqueStatus.add(transactionHistory.getTransactionStatus());
            putAwaySummaryResponseDTO.setCreatedUser(transactionHistory.getCreatedBy());
            putAwaySummaryResponseDTO.setCreatedDate(transactionHistory.getCreatedDate());
            putAwaySummaryResponseDTO.setUpdatedDate(transactionHistory.getLastModifiedDate());
            putAwaySummaryResponseDTO.setUpdatedUser(transactionHistory.getLastModifiedBy());
            DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
            if (transactionHistory.getBranchMaster() != null) {
                modelMapper.map(transactionHistory.getBranchMaster(), branchDTO);
                putAwaySummaryResponseDTO.setBranch(branchDTO);
            }
            DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
            if (transactionHistory.getCompanyMaster() != null) {
                modelMapper.map(transactionHistory.getCompanyMaster(), companyDTO);
                putAwaySummaryResponseDTO.setCompany(companyDTO);
            }
            DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
            if (transactionHistory.getGroupCompanyMaster() != null) {
                modelMapper.map(transactionHistory.getGroupCompanyMaster(), groupCompanyDTO);
                putAwaySummaryResponseDTO.setGroupCompany(groupCompanyDTO);
            }
        }
        putAwaySummaryResponseDTO.setNoOfLpn(String.valueOf(uniqueLpnId.size()));
        putAwaySummaryResponseDTO.setNoOfSku(String.valueOf(uniqueSkuCode.size()));
        putAwaySummaryResponseDTO.setNoOfCarton(String.valueOf(uniqueCartonId.size()));
        Double volume = 0.0;
        for (Double volume1 : uniqueVolume) {
            volume = volume + volume1;
        }
        putAwaySummaryResponseDTO.setVolume(String.valueOf(volume));
        putAwaySummaryResponseDTO.setStatus("Completed"); //Hardcode value
        putAwaySummaryResponseDTO.setTeam("TEAM 1");  //Hardcode value
        log.info("EXIT");
        return putAwaySummaryResponseDTO;
    }


    public List<TransactionHistoryResponseDTO> convertEntityListToResponseList(List<TransactionHistory> transactionHistoryList) {
        log.info("ENTRY - TransactionHistoryList to TransactionList mapper");
        List<TransactionHistoryResponseDTO> transactionHistoryResponseDTOList = transactionHistoryList
                .stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return transactionHistoryResponseDTOList;

    }

    /*
     * Method to map source to target
     * @Return TargetClass
     */
    private <T, U> U mapToDTOIfNotNull(T source, Class<U> targetClass) {
        log.info("ENTRY - source to target mapper");
        if (source != null) {
            return modelMapper.map(source, targetClass);
        }
        return null;
    }

    private TransactionHistoryResponseDTO convertEntityToResponseDTO(TransactionHistory transactionHistory) {
        TransactionHistoryResponseDTO transactionHistoryResponseDTO = modelMapper.map(transactionHistory, TransactionHistoryResponseDTO.class);
        transactionHistoryResponseDTO.setWareHouse((mapToDTOIfNotNull(transactionHistory.getWareHouseMaster(), TransactionHistoryResponseDTO.WareHouseDTO.class)));
        transactionHistoryResponseDTO.setCustomer(mapToDTOIfNotNull(transactionHistory.getCustomerMaster(), TransactionHistoryResponseDTO.CustomerDTO.class));
        transactionHistoryResponseDTO.setLocation(mapToDTOIfNotNull(transactionHistory.getLocationMaster(), TransactionHistoryResponseDTO.LocationDTO.class));
        transactionHistoryResponseDTO.setSku(mapToDTOIfNotNull(transactionHistory.getSkuMaster(), TransactionHistoryResponseDTO.SkuDTO.class));
        transactionHistoryResponseDTO.setUom(mapToDTOIfNotNull(transactionHistory.getUomMaster(), TransactionHistoryResponseDTO.UomDTO.class));
        transactionHistoryResponseDTO.setTrnHeaderAsnMaster(mapToDTOIfNotNull(transactionHistory.getTrnHeaderAsnMaster(), TransactionHistoryResponseDTO.TrnHeaderAsnDTO.class));
        transactionHistoryResponseDTO.setSourceTrnHeaderAsnMaster(mapToDTOIfNotNull(transactionHistory.getSourceTrnHeaderAsnMaster(), TransactionHistoryResponseDTO.TrnHeaderAsnDTO.class));
        transactionHistoryResponseDTO.setInvStatus(mapToDTOIfNotNull(transactionHistory.getInvStatus(), TransactionHistoryResponseDTO.InventoryStatusDTO.class));
        return transactionHistoryResponseDTO;
    }

    public List<PutAwaySummaryResponseDTO> getSortedResponseList(Pageable pageable, Predicate predicateAll) {
        List<PutAwaySummaryResponseDTO> putAwaySummaryResponseDTOList = new ArrayList<>();
        Sort sort = pageable.getSort();
        List<Sort.Order> orders = sort.toList();
        String sortProperty = null;
        String sortDirection = null;
        for (Sort.Order order : orders) {
            sortProperty = order.getProperty();
            sortDirection = order.getDirection().toString().toLowerCase();
        }
        List<TransactionHistory> transactionHistoryList = transactionHistoryService.findAll(predicateAll, Pageable.unpaged());
        Map<Long, List<TransactionHistory>> transactionHistoryMap = transactionHistoryList.stream()
                .collect(Collectors.groupingBy(th -> th.getCustomerMaster().getId()));
        for (Map.Entry<Long, List<TransactionHistory>> entry : transactionHistoryMap.entrySet()) {
            Long customerId = entry.getKey();
            List<TransactionHistory> transactionHistoryList1 = entry.getValue();
            putAwaySummaryResponseDTOList.add(generatePutAwayList(transactionHistoryList1, customerId));
        }
        return sortByAttribute(putAwaySummaryResponseDTOList, sortProperty, sortDirection);
    }

    public List<PutAwaySummaryResponseDTO> sortByAttribute(List<PutAwaySummaryResponseDTO> objects, String attribute, String sortDirection) {
        Comparator<PutAwaySummaryResponseDTO> comparator;
        switch (attribute) {
            case "customerName":
                comparator = Comparator.comparing(dto -> dto.getCustomer().getName(), Comparator.nullsFirst(String::compareTo));
                break;
            case "customerOrderNo":
                comparator = Comparator.comparing(dto -> dto.getOrderNo(), Comparator.nullsFirst(String::compareTo));
                break;
            case "createdDate":
                comparator = Comparator.comparing(dto -> dto.getCreatedDate(), Comparator.nullsFirst(Date::compareTo));
                break;
            default:
                comparator = Comparator.comparing(dto -> dto.getCustomer().getId(), Comparator.nullsLast(Long::compareTo));
        }
        if (sortDirection.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return objects.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<PutAwaySummaryResponseDTO> getPagedResponseList(Pageable pageable, List<PutAwaySummaryResponseDTO> putAwaySummaryResponseDTOListFilteredAndSorted) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        if (putAwaySummaryResponseDTOListFilteredAndSorted.size() < startItem) {
            putAwaySummaryResponseDTOListFilteredAndSorted = Collections.emptyList(); // Return empty list if start index is greater than list size
        } else {
            int toIndex = Math.min(startItem + pageSize, putAwaySummaryResponseDTOListFilteredAndSorted.size());
            putAwaySummaryResponseDTOListFilteredAndSorted = putAwaySummaryResponseDTOListFilteredAndSorted.subList(startItem, toIndex);
        }
        return putAwaySummaryResponseDTOListFilteredAndSorted;
    }

    public List<TransactionHistory> convertPopUpRequestToEntityList(List<TransactionHistory> filteredList, PutAwayPopUpRequestDTO putAwayPopUpRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        List<TransactionHistory> transactionHistoryList = new ArrayList<>();
        int index = filteredList.size() - 1;
        int slNo = filteredList.get(index).getTaskSlNo() + 1;
        TransactionHistory transactionHistory = filteredList.get(0);

        // Assuming TransactionHistory has setters for the fields to be updated
        for (PutAwayPopUpRequestDTO.PutAwayPopupDetails putAwayPopupDetails : putAwayPopUpRequestDTO.getPutawayPopupDetailsList()) {
            if (putAwayPopupDetails.getIsEditable().equalsIgnoreCase("Yes") && putAwayPopupDetails.getToLoc() != null) {
                Location location = locationService.getLocationById(putAwayPopupDetails.getToLoc().getId());
                if (!location.getLocTypeMaster().getType().equalsIgnoreCase("dock")) {
                    Integer taskSlNo = putAwayPopupDetails.getTaskSlNo();
                    boolean isDirectPutAway = isDirectPutAway(filteredList);
                    if (isDirectPutAway && filteredList.get(0).getTransactionStatus().equalsIgnoreCase(Generated)) {
                        for (TransactionHistory direct : filteredList) {
                            direct.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
                            direct.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
                            direct.setTransactionStatus(Cancelled);
                            direct.setTaskStatus(Cancelled);
                            transactionHistoryList.add(direct);
                        }
                    } else if (taskSlNo != null) {
                        List<TransactionHistory> isMatch = filteredList.stream()
                                .filter(transactionHistory1 ->
                                        transactionHistory1.getTaskSlNo().equals(taskSlNo)
                                )
                                .collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(isMatch) && isMatch.size() > 1) {
                            for (TransactionHistory transaction : isMatch) {
                                modelMapper.map(dateAndTimeRequestDto, transaction);
                                transaction.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
                                transaction.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
                                transaction.setTransactionStatus(Cancelled);
                                transaction.setTaskStatus(Cancelled);
                                transactionHistoryList.add(transaction);
                            }
                        }

                    }

                    // Create a new TransactionHistory for OUT operation
                    transactionHistoryList.add(createOutTransaction(transactionHistory, dateAndTimeRequestDto, putAwayPopupDetails, slNo));
                    // Create a new TransactionHistory for IN operation
                    transactionHistoryList.add(createInTransaction(transactionHistory, dateAndTimeRequestDto, putAwayPopupDetails, slNo));
                    slNo++;
                }
            }
        }
        return transactionHistoryList;
    }

    public List<TransactionHistory> convertPutAwayBulkRequestToEntity(PutAwayRequestDTO putAwayRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        List<TransactionHistory> transactionHistoryList = new ArrayList<>();

        for (PutAwayRequestDTO.PutAwayTaskDetailDTO putAwayTaskDetailDTO : putAwayRequestDTO.getPutAwayTaskDetailList()) {
            PutAwayRequestDTO.PutAwayFormDTO putAwayForm = putAwayTaskDetailDTO.getPutAwayForm();
            if (putAwayForm.getIsEditable().equalsIgnoreCase("Yes") && putAwayTaskDetailDTO.getPutAwayForm().getToLoc() != null) {
                Location location = locationService.getLocationById(putAwayTaskDetailDTO.getPutAwayForm().getToLoc().getId());
                if (!location.getLocTypeMaster().getType().equalsIgnoreCase("dock")) {
                    Long grnDetailId = putAwayForm.getGrnDetailId();
                    List<TransactionHistory> transactionHistoryList1 = transactionHistoryService.findByGrnDetailId(grnDetailId);
                    int index = transactionHistoryList1.size() - 1;
                    int slNo = transactionHistoryList1.get(index).getTaskSlNo() + 1;
                    TransactionHistory transactionHistory1 = transactionHistoryList1.get(0);
                    if (transactionHistoryList1.size() != 1 && transactionHistory1.getTransactionSlNo().equals(putAwayForm.getTransactionSlNo())) {
                        for (int i = 1; i < transactionHistoryList1.size(); i++) {
                            TransactionHistory transactionHistory = transactionHistoryList1.get(i);
                            transactionHistory.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
                            transactionHistory.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
                            transactionHistory.setTransactionStatus(Cancelled);
                            transactionHistory.setTaskStatus(Cancelled);
                            transactionHistoryList.add(transactionHistory);
                        }
                    }
                    // Create a new TransactionHistory for OUT operation
                    transactionHistoryList.add(createOutTransactionForBulk(transactionHistory1, dateAndTimeRequestDto, putAwayTaskDetailDTO, slNo));
                    // Create a new TransactionHistory for IN operation
                    transactionHistoryList.add(createInTransactionForBulk(transactionHistory1, dateAndTimeRequestDto, putAwayTaskDetailDTO, slNo));
                }
            }
        }
        return transactionHistoryList;
    }

    Boolean isDirectPutAway(List<TransactionHistory> filteredList) {
        return filteredList.stream()
                .allMatch(directPutAwayList -> directPutAwayList.getInOut().equalsIgnoreCase("IN"));
    }

    public TransactionHistory createOutTransaction(TransactionHistory transactionHistory, DateAndTimeRequestDto dateAndTimeRequestDto,
                                                   PutAwayPopUpRequestDTO.PutAwayPopupDetails putAwayPopupDetails, int slNo) {
        TransactionHistory outTransactionHistory = new TransactionHistory();
        modelMapper.map(transactionHistory, outTransactionHistory);
        outTransactionHistory.setId(null);
        outTransactionHistory.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
        outTransactionHistory.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
        outTransactionHistory.setInOut("OUT");
        outTransactionHistory.setTransactionStatus("RECEIVED");
        outTransactionHistory.setTaskStatus(Completed);
        outTransactionHistory.setQty(-putAwayPopupDetails.getPutAwayQty());
        outTransactionHistory.setVolume(-putAwayPopupDetails.getVolume());
        outTransactionHistory.setNetWeight(-putAwayPopupDetails.getWeight());
        outTransactionHistory.setGrossWeight(-putAwayPopupDetails.getWeight());
        outTransactionHistory.setTaskSlNo(slNo);
        return outTransactionHistory;
    }

    public TransactionHistory createInTransaction(TransactionHistory transactionHistory, DateAndTimeRequestDto dateAndTimeRequestDto,
                                                  PutAwayPopUpRequestDTO.PutAwayPopupDetails putAwayPopupDetails, int slNo) {
        TransactionHistory inTransactionHistory = new TransactionHistory();
        modelMapper.map(transactionHistory, inTransactionHistory);
        inTransactionHistory.setId(null);
        inTransactionHistory.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
        inTransactionHistory.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
        modelMapper.map(putAwayPopupDetails, inTransactionHistory);
        inTransactionHistory.setQty(putAwayPopupDetails.getPutAwayQty());
        inTransactionHistory.setNetWeight(putAwayPopupDetails.getWeight());
        inTransactionHistory.setGrossWeight(putAwayPopupDetails.getWeight());
        inTransactionHistory.setVolume(putAwayPopupDetails.getVolume());
        inTransactionHistory.setInOut("IN");
        inTransactionHistory.setTransactionStatus(Generated);
        inTransactionHistory.setTaskStatus(Completed);
        inTransactionHistory.setTaskSlNo(slNo);
        if (putAwayPopupDetails.getToLoc() != null) {
            Location location1 = locationService.getLocationById(putAwayPopupDetails.getToLoc().getId());
            inTransactionHistory.setLocationMaster(location1);
        }
        return inTransactionHistory;

    }

    public TransactionHistory createOutTransactionForBulk(TransactionHistory transactionHistory1, DateAndTimeRequestDto dateAndTimeRequestDto,
                                                          PutAwayRequestDTO.PutAwayTaskDetailDTO putAwayTaskDetailDTO, int slNo) {
        TransactionHistory outTransactionHistory = new TransactionHistory();
        modelMapper.map(transactionHistory1, outTransactionHistory);
        outTransactionHistory.setId(null);
        outTransactionHistory.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
        outTransactionHistory.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
        outTransactionHistory.setInOut("OUT");
        outTransactionHistory.setTaskSlNo(slNo);
        outTransactionHistory.setQty(-putAwayTaskDetailDTO.getPutAwayForm().getPutAwayQty());
        outTransactionHistory.setVolume(-putAwayTaskDetailDTO.getPutAwayForm().getVolume());
        outTransactionHistory.setNetWeight(-putAwayTaskDetailDTO.getPutAwayForm().getWeight());
        outTransactionHistory.setGrossWeight(-putAwayTaskDetailDTO.getPutAwayForm().getWeight());
        return outTransactionHistory;
    }

    public TransactionHistory createInTransactionForBulk(TransactionHistory transactionHistory1, DateAndTimeRequestDto dateAndTimeRequestDto,
                                                         PutAwayRequestDTO.PutAwayTaskDetailDTO putAwayTaskDetailDTO, int slNo) {
        TransactionHistory inTransactionHistory = new TransactionHistory();
        modelMapper.map(transactionHistory1, inTransactionHistory);
        inTransactionHistory.setId(null);
        inTransactionHistory.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
        inTransactionHistory.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
        modelMapper.map(putAwayTaskDetailDTO.getPutAwayForm(), inTransactionHistory);
        inTransactionHistory.setQty(putAwayTaskDetailDTO.getPutAwayForm().getPutAwayQty());
        inTransactionHistory.setInOut("IN");
        inTransactionHistory.setTransactionSlNo(transactionHistory1.getTransactionSlNo());
        inTransactionHistory.setTaskSlNo(slNo);
        inTransactionHistory.setNetWeight(putAwayTaskDetailDTO.getPutAwayForm().getWeight());
        inTransactionHistory.setGrossWeight(putAwayTaskDetailDTO.getPutAwayForm().getWeight());
        inTransactionHistory.setVolume(putAwayTaskDetailDTO.getPutAwayForm().getVolume());
        inTransactionHistory.setTransactionStatus(Generated);
        inTransactionHistory.setTaskStatus(Completed);
        if (putAwayTaskDetailDTO.getPutAwayForm().getToLoc() != null) {
            Location location1 = locationService.getLocationById(putAwayTaskDetailDTO.getPutAwayForm().getToLoc().getId());
            inTransactionHistory.setLocationMaster(location1);
        }
        return inTransactionHistory;

    }

}