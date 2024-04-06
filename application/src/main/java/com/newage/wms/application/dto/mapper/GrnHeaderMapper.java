package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.responsedto.CustomerAddressResponseDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.GrnHeaderResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QGrnHeader;
import com.newage.wms.service.*;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class GrnHeaderMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private TrnHeaderAsnService trnHeaderAsnService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private TrnHeaderPartyMapper trnHeaderPartyMapper;

    @Autowired
    private GrnHeaderService grnHeaderService;

    @Autowired
    private TransactionLotService transactionLotService;

    @Autowired
    private TrnHeaderFreightShippingMapper freightShippingMapper;

    @Autowired
    private GrnLotDetailService grnLotDetailService;

    @Autowired
    private CustomerMasterService customerMasterService;

    @Autowired
    private UserWareHouseService userWareHouseService;

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Autowired
    private PutAwayTaskHeaderMapper putAwayTaskHeaderMapper;

    @Autowired
    private PutAwayTaskDetailMapper putAwayTaskDetailMapper;

    @Autowired
    private PutAwayHeaderService putAwayHeaderService;

    @Autowired
    private PutAwayTaskDetailService putAwayTaskDetailService;

    private final String Completed = "COMPLETED";

    /*
     * Method to convert GrnHeader Page to GrnHeader Response Page
     * @Return Page<GrnHeaderResponseDTO>
     */
    public Page<GrnHeaderResponseDTO> convertEntityPageToResponsePage(Page<GrnHeader> grnHeaderPage) {
        log.info("ENTRY - GrnHeader Page to GrnHeader Response Page mapper");
        List<GrnHeaderResponseDTO> grnHeaderResponseDTOList = grnHeaderPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(grnHeaderResponseDTOList, grnHeaderPage.getPageable(), grnHeaderPage.getTotalElements());
    }

    /*
     * Method to convert GrnHeader Request to GrnHeader entity
     * @Return GrnHeader entity
     */
    public GrnHeader convertRequestToEntity(GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto, String status) {
        log.info("ENTRY - GrnHeader Request to GrnHeader entity mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        GrnHeader grnHeader = modelMapper.map(grnHeaderRequestDTO, GrnHeader.class);
        modelMapper.map(dateAndTimeRequestDto, grnHeader);                                            // map date, time and version
        grnHeader.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(grnHeaderRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        grnHeader.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(grnHeaderRequestDTO.getCompanyId())));
        grnHeader.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(grnHeaderRequestDTO.getBranchId())));
        if (grnHeaderRequestDTO.getTrnHeaderAsn().getTransaction() != null) {
            TrnHeaderAsn trnHeaderAsn = trnHeaderAsnService.findById(Long.parseLong(grnHeaderRequestDTO.getTrnHeaderAsn().getTransaction().getId()));
            grnHeader.setTrnHeaderAsnMaster(trnHeaderAsn);
            grnHeader.setTransactionUid(trnHeaderAsn.getTransactionUid());
        }
        setReferenceDateStatus(grnHeader, status);
        log.info("EXIT");
        return grnHeader;
    }

    private void setReferenceDateStatus(GrnHeader grnHeader, String status) {
        if (status.equalsIgnoreCase(Completed)) {
            grnHeader.setGrnDate(new Date());
            grnHeader.setGrnRef(grnHeaderService.generateGrnReference());
        }
    }

    /*
     * Method to convert update GrnHeader Request to GrnHeader entity
     */
    public void convertUpdateRequestToEntity(GrnHeaderRequestDTO grnHeaderRequestDTO, GrnHeader grnHeaderToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto, String status) {
        log.info("ENTRY - update GrnHeaderRequestDto to GrnHeader mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(grnHeaderRequestDTO, grnHeaderToBeUpdated);
        modelMapper.map(dateAndTimeRequestDto, grnHeaderToBeUpdated);
        grnHeaderToBeUpdated.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(grnHeaderRequestDTO.getGroupCompanyId())));
        grnHeaderToBeUpdated.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(grnHeaderRequestDTO.getCompanyId())));
        grnHeaderToBeUpdated.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(grnHeaderRequestDTO.getBranchId())));

        setReferenceDateStatus(grnHeaderToBeUpdated, status);
        log.info("EXIT");
    }

    /*
     * Method to convert GrnHeader entity to GrnHeader Response
     * @Return GrnHeaderResponseDTO
     */
    public GrnHeaderResponseDTO convertEntityToResponse(GrnHeader grnHeader) {
        log.info("ENTRY - GrnHeader entity to GrnHeader Response mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        GrnHeaderResponseDTO grnHeaderResponseDTO = modelMapper.map(grnHeader, GrnHeaderResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if (grnHeader.getBranchMaster() != null) {
            modelMapper.map(grnHeader.getBranchMaster(), branchDTO);
            grnHeaderResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if (grnHeader.getCompanyMaster() != null) {
            modelMapper.map(grnHeader.getCompanyMaster(), companyDTO);
            grnHeaderResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if (grnHeader.getGroupCompanyMaster() != null) {
            modelMapper.map(grnHeader.getGroupCompanyMaster(), groupCompanyDTO);
            grnHeaderResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        GrnHeaderResponseDTO.GrnHeaderDTO grnHeaderDTO = modelMapper.map(grnHeader, GrnHeaderResponseDTO.GrnHeaderDTO.class);
        if (grnHeaderDTO.getGrnRef() == null) {
            grnHeaderDTO.setGrnRef("");
        }
        if (grnHeaderDTO.getStatus() == null) {
            grnHeaderDTO.setStatus("");
        }
        grnHeaderResponseDTO.setGrnHeader(grnHeaderDTO);
        grnHeaderResponseDTO.setTrnHeaderAsn(getResponseTrnHeaderAsn(grnHeader.getTrnHeaderAsnMaster()));
        grnHeaderResponseDTO.setCreatedUser(grnHeader.getCreatedBy());
        grnHeaderResponseDTO.setUpdatedDate(grnHeader.getLastModifiedDate());
        grnHeaderResponseDTO.setUpdatedUser(grnHeader.getLastModifiedBy());
        log.info("EXIT");
        return grnHeaderResponseDTO;
    }

    public List<GrnHeaderResponseDTO> getSortedResponseList(Pageable pageable, Predicate predicateAll, Long userId) {
        Sort sort = pageable.getSort();
        List<Sort.Order> orders = sort.toList();
        String sortProperty = null;
        String sortDirection = null;
        for (Sort.Order order : orders) {
            sortProperty = order.getProperty();
            sortDirection = order.getDirection().toString().toLowerCase();
        }
        Page<TrnHeaderAsn> trnHeaderAsnPage = trnHeaderAsnService.findAll(predicateAll, Pageable.unpaged());
        List<GrnHeaderResponseDTO> grnHeaderResponseDTOList = new ArrayList<>();
        for (TrnHeaderAsn trnHeaderAsn : trnHeaderAsnPage.getContent()) {
            List<GrnHeaderResponseDTO> grnHeaderResponseDTOList1 = getGrnForTrn(trnHeaderAsn, userId);
            grnHeaderResponseDTOList.addAll(grnHeaderResponseDTOList1);
        }
        return sortByAttribute(grnHeaderResponseDTOList, sortProperty, sortDirection);
    }

    public GrnHeaderResponseDTO.TrnHeaderAsnDTO getResponseTrnHeaderAsn(TrnHeaderAsn trnHeaderAsn) {
        GrnHeaderResponseDTO.TrnHeaderAsnDTO trnHeaderAsnDTO = modelMapper.map(trnHeaderAsn, GrnHeaderResponseDTO.TrnHeaderAsnDTO.class);
        GrnHeaderResponseDTO.TrnHeaderAsnDTO.TransactionDTO transactionDTO = modelMapper.map(trnHeaderAsn, GrnHeaderResponseDTO.TrnHeaderAsnDTO.TransactionDTO.class);
        trnHeaderAsnDTO.setTransaction(transactionDTO);
        trnHeaderAsnDTO.setFrom(trnHeaderAsn.getTransactionType());
        if (trnHeaderAsn.getWareHouseMaster() != null) {
            GrnHeaderResponseDTO.TrnHeaderAsnDTO.WareHouseDTO wareHouseDTO = modelMapper.map(trnHeaderAsn.getWareHouseMaster(), GrnHeaderResponseDTO.TrnHeaderAsnDTO.WareHouseDTO.class);
            trnHeaderAsnDTO.setWarehouse(wareHouseDTO);
        }
        if (trnHeaderAsn.getCustomerMaster() != null) {
            GrnHeaderResponseDTO.TrnHeaderAsnDTO.CustomerDTO customerDTO = modelMapper.map(trnHeaderAsn.getCustomerMaster(), GrnHeaderResponseDTO.TrnHeaderAsnDTO.CustomerDTO.class);
            trnHeaderAsnDTO.setCustomer(customerDTO);
        }
        if (trnHeaderAsnDTO.getCustomerOrderNo() == null) {
            trnHeaderAsnDTO.setCustomerOrderNo("");
        }
        if (trnHeaderAsnDTO.getFrom().equalsIgnoreCase("DIRECT")) {
            trnHeaderAsnDTO.setFrom("Direct");
        }
        if (trnHeaderAsnDTO.getFrom().equalsIgnoreCase("ASN")) {
            trnHeaderAsnDTO.setFrom("Asn");
        }
        if (trnHeaderAsnDTO.getFrom().equalsIgnoreCase("PO")) {
            trnHeaderAsnDTO.setFrom("Po");
        }
        return trnHeaderAsnDTO;
    }

    public List<GrnHeaderResponseDTO> sortByAttribute(List<GrnHeaderResponseDTO> objects, String attribute, String sortDirection) {
        Comparator<GrnHeaderResponseDTO> comparator;
        switch (attribute) {
            case "customerName":
                comparator = Comparator.comparing(dto -> dto.getTrnHeaderAsn().getCustomer().getName(), Comparator.nullsFirst(String::compareTo));
                break;
            case "id":
                comparator = Comparator.comparing(dto -> dto.getTrnHeaderAsn().getId(), Comparator.nullsFirst(Long::compareTo));
                break;
            case "transactionUid":
                comparator = Comparator.comparing(dto -> dto.getTrnHeaderAsn().getTransaction().getTransactionUid(), Comparator.nullsFirst(String::compareTo));
                break;
            case "transactionType":
                comparator = Comparator.comparing(dto -> dto.getTrnHeaderAsn().getTransactionType(), Comparator.nullsFirst(String::compareTo));
                break;
            case "customerOrderNo":
                comparator = Comparator.comparing(dto -> dto.getTrnHeaderAsn().getCustomerOrderNo(), Comparator.nullsFirst(String::compareTo));
                break;
            case "createdDate":
                comparator = Comparator.comparing(dto -> dto.getTrnHeaderAsn().getCreatedDate(), Comparator.nullsFirst(Date::compareTo));
                break;
            case "grnRef":
                comparator = Comparator.comparing(dto -> dto.getGrnHeader().getGrnRef(), Comparator.nullsFirst(String::compareTo));
                break;
            case "grnDate":
                comparator = Comparator.comparing(dto -> dto.getGrnHeader().getGrnDate(), Comparator.nullsFirst(Date::compareTo));
                break;
            case "grnStatus":
                comparator = Comparator.comparing(dto -> dto.getGrnHeader().getStatus(), Comparator.nullsFirst(String::compareTo));
                break;
            default:
                comparator = Comparator.comparing(dto -> dto.getId(), Comparator.nullsLast(Long::compareTo));
        }
        if (sortDirection.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return objects.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<GrnHeaderResponseDTO> getFilteredResponseListWithGrnFilter(List<GrnHeaderResponseDTO> pagedGrnHeaderResponseDTOList,
                                                                           String grnRef, String grnStatus) {
        if (grnRef == null || grnRef.isEmpty() || grnRef.isBlank()) {
            grnRef = "";
        }
        if (grnStatus == null || grnStatus.isEmpty() || grnStatus.isBlank()) {
            grnStatus = "";
        }
        final String grnRef1 = grnRef.toLowerCase();
        final String grnStatus1 = grnStatus.toLowerCase();
        return pagedGrnHeaderResponseDTOList.stream()
                .filter(grnHeaderResponseDTO ->
                        (grnRef1.isEmpty() || grnHeaderResponseDTO.getGrnHeader().getGrnRef().toLowerCase().contains(grnRef1))
                                && (grnStatus1.isEmpty() || grnHeaderResponseDTO.getGrnHeader().getStatus().toLowerCase().equalsIgnoreCase(grnStatus1)))
                .collect(Collectors.toList());
    }

    public List<GrnHeaderResponseDTO> getGrnForTrn(TrnHeaderAsn trnHeaderAsn, Long userId) {
        Predicate predicate = QGrnHeader.grnHeader.id.isNotNull();
        Page<GrnHeader> grnHeaderPage = grnHeaderService.findAll(predicate, Pageable.unpaged());
        List<GrnHeader> filteredGrnHeaderList = grnHeaderPage.getContent().stream()
                .filter(grnHeader -> grnHeader.getTrnHeaderAsnMaster().getId().equals(trnHeaderAsn.getId()))
                .collect(Collectors.toList());
        List<GrnHeaderResponseDTO> grnHeaderResponseDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(filteredGrnHeaderList)) {
            grnHeaderResponseDTOList = filteredGrnHeaderList
                    .stream()
                    .map(grnHeader -> convertAndSetGrnResponse(grnHeader, userId))
                    .collect(Collectors.toList());
        } else {
            GrnHeaderResponseDTO grnHeaderResponseDTO = setGrnHeaderResponseWithOnlyTrn(trnHeaderAsn);
            grnHeaderResponseDTOList.add(grnHeaderResponseDTO);
        }
        return grnHeaderResponseDTOList;
    }

    public GrnHeaderResponseDTO convertAndSetGrnResponse(GrnHeader grnHeader, Long userId) {
        GrnHeaderResponseDTO grnHeaderResponseDTO = convertEntityToResponse(grnHeader);
        if (grnHeader.getTrnHeaderAsnMaster().getTrnHeaderParty() != null) {
            List<TrnResponseDTO.PartyCustomerDTO> partyCustomerDTOList = trnHeaderPartyMapper.convertEntityToResponseList(grnHeader.getTrnHeaderAsnMaster());
            grnHeaderResponseDTO.setParty(partyCustomerDTOList);
        }
        if (grnHeader.getTrnHeaderAsnMaster().getTrnHeaderFreightShipping() != null) {
            TrnResponseDTO.TrnHeaderFreightShippingDTO trnHeaderFreightShippingDTO = freightShippingMapper.convertEntityToResponse(grnHeader.getTrnHeaderAsnMaster());
            grnHeaderResponseDTO.setTrnHeaderFreightShipping(trnHeaderFreightShippingDTO);
        }
        if (grnHeader.getTrnHeaderAsnMaster().getCustomerMaster() != null) {
            CustomerMaster customerMaster = customerMasterService.getCustomerById(Long.parseLong(grnHeaderResponseDTO.getTrnHeaderAsn().getCustomer().getId()));
            CustomerAddressResponseDTO customerAddressResponseDTO = customerAddressMapper.convertEntityListToResponseList(customerMaster.getCustomerAddressMasterList());
            grnHeaderResponseDTO.setCustomerAddress(customerAddressResponseDTO);
        }
        if (grnHeader.getStatus().equalsIgnoreCase(Completed) || grnHeader.getStatus().equalsIgnoreCase("Cancelled")) {
            grnHeaderResponseDTO.setIsEditable("Yes");
        } else {
            grnHeaderResponseDTO.setIsEditable("No");
        }
        setCancelAndViewRights(grnHeaderResponseDTO, userId);

        return grnHeaderResponseDTO;
    }

    private void setCancelAndViewRights(GrnHeaderResponseDTO grnHeaderResponseDTO, Long userId) {
        boolean hasCancelUserRights = userWareHouseService.userIdCheckForGrn(userId);
        if (hasCancelUserRights) {
            grnHeaderResponseDTO.setIsGrnCancellable("Yes");
            grnHeaderResponseDTO.setIsGrnViewable("Yes");
        } else {
            grnHeaderResponseDTO.setIsGrnCancellable("No");
            grnHeaderResponseDTO.setIsGrnViewable("No");
        }
    }

    public GrnHeaderResponseDTO setGrnHeaderResponseWithOnlyTrn(TrnHeaderAsn trnHeaderAsn) {
        GrnHeaderResponseDTO grnHeaderResponseDTO = new GrnHeaderResponseDTO();
        grnHeaderResponseDTO.setCreatedUser(trnHeaderAsn.getCreatedBy());
        grnHeaderResponseDTO.setCreatedDate(trnHeaderAsn.getCreatedDate());
        grnHeaderResponseDTO.setUpdatedUser(trnHeaderAsn.getLastModifiedBy());
        grnHeaderResponseDTO.setUpdatedDate(trnHeaderAsn.getLastModifiedDate());
        GrnHeaderResponseDTO.TrnHeaderAsnDTO trnHeaderAsnDTO = getResponseTrnHeaderAsn(trnHeaderAsn);
        List<TrnResponseDTO.PartyCustomerDTO> partyCustomerDTOList = trnHeaderPartyMapper.convertEntityToResponseList(trnHeaderAsn);
        grnHeaderResponseDTO.setParty(partyCustomerDTOList);
        grnHeaderResponseDTO.setTrnHeaderAsn(trnHeaderAsnDTO);
        grnHeaderResponseDTO.setTrnHeaderFreightShipping(freightShippingMapper.convertEntityToResponse(trnHeaderAsn));
        CustomerMaster customerMaster = customerMasterService.getCustomerById(Long.parseLong(grnHeaderResponseDTO.getTrnHeaderAsn().getCustomer().getId()));
        CustomerAddressResponseDTO customerAddressResponseDTO = customerAddressMapper.convertEntityListToResponseList(customerMaster.getCustomerAddressMasterList());
        grnHeaderResponseDTO.setCustomerAddress(customerAddressResponseDTO);
        GrnHeaderResponseDTO.GrnHeaderDTO grnHeaderDTO = new GrnHeaderResponseDTO.GrnHeaderDTO();
        grnHeaderDTO.setGrnRef("");
        grnHeaderDTO.setStatus("");
        grnHeaderResponseDTO.setGrnHeader(grnHeaderDTO);
        return grnHeaderResponseDTO;
    }

    public List<GrnHeaderResponseDTO> getPagedResponseList(Pageable pageable, List<GrnHeaderResponseDTO> grnHeaderResponseDTOListFilteredAndSorted) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        if (grnHeaderResponseDTOListFilteredAndSorted.size() < startItem) {
            grnHeaderResponseDTOListFilteredAndSorted = Collections.emptyList(); // Return empty list if start index is greater than list size
        } else {
            int toIndex = Math.min(startItem + pageSize, grnHeaderResponseDTOListFilteredAndSorted.size());
            grnHeaderResponseDTOListFilteredAndSorted = grnHeaderResponseDTOListFilteredAndSorted.subList(startItem, toIndex);
        }
        return grnHeaderResponseDTOListFilteredAndSorted;
    }

    public List<GrnHeaderResponseDTO> getFilteredResponseListWithTrnFilter(List<GrnHeaderResponseDTO> grnHeaderResponseDTOListGRNSortedAndFiltered, String customerName,
                                                                           String transactionUid, String transactionType, String customerOrderNo) {
        if (customerName == null) {
            customerName = "";
        }
        if (transactionUid == null) {
            transactionUid = "";
        }
        if (transactionType == null) {
            transactionType = "";
        }
        if (customerOrderNo == null) {
            customerOrderNo = "";
        }
        final String customerName1 = customerName.toLowerCase();
        final String transactionUid1 = transactionUid.toLowerCase();
        final String transactionType1 = transactionType.toLowerCase();
        final String customerOrderNo1 = customerOrderNo.toLowerCase();
        return grnHeaderResponseDTOListGRNSortedAndFiltered.stream()
                .filter(grnHeaderResponseDTO -> grnHeaderResponseDTO.getTrnHeaderAsn().getCustomer().getName().toLowerCase().contains(customerName1))
                .filter(grnHeaderResponseDTO -> grnHeaderResponseDTO.getTrnHeaderAsn().getTransaction().getTransactionUid().toLowerCase().contains(transactionUid1))
                .filter(grnHeaderResponseDTO -> grnHeaderResponseDTO.getTrnHeaderAsn().getTransactionType().toLowerCase().contains(transactionType1))
                .filter(grnHeaderResponseDTO -> grnHeaderResponseDTO.getTrnHeaderAsn().getCustomerOrderNo().toLowerCase().contains(customerOrderNo1))
                .collect(Collectors.toList());
    }

    public List<GrnHeaderResponseDTO> getFilteredResponseListWithTrnDateFilter(List<GrnHeaderResponseDTO> grnHeaderResponseDTOListGRNTRNSortedAndFiltered, Date fromDate, Date toDate, String dateFilter, String createdDateOrgrnDate) {
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
        return filterDate(isdatefilter, createdDateOrgrnDate, dt1, dt2, grnHeaderResponseDTOListGRNTRNSortedAndFiltered);

    }

    private List<GrnHeaderResponseDTO> filterDate(boolean isdatefilter, String createdDateOrgrnDate, Date dt1, Date dt2,
                                                  List<GrnHeaderResponseDTO> grnHeaderResponseDTOListGRNTRNSortedAndFiltered) {
        final Date dt1Final = dt1;
        final Date dt2Final = dt2;
        if (isdatefilter) {
            if (createdDateOrgrnDate != null && createdDateOrgrnDate.equalsIgnoreCase("createdDate")) {
                return grnHeaderResponseDTOListGRNTRNSortedAndFiltered.stream()
                        .filter(grnHeaderResponseDTO -> grnHeaderResponseDTO.getCreatedDate().after(dt1Final) &&
                                grnHeaderResponseDTO.getCreatedDate().before(dt2Final))
                        .collect(Collectors.toList());
            } else {
                return grnHeaderResponseDTOListGRNTRNSortedAndFiltered.stream()
                        .filter(grnHeaderResponseDTO -> {
                            Date grnDate = grnHeaderResponseDTO.getGrnHeader().getGrnDate();
                            return grnDate != null &&
                                    grnDate.after(dt1Final) &&
                                    grnDate.before(dt2Final);
                        })
                        .collect(Collectors.toList());
            }
        } else {
            return grnHeaderResponseDTOListGRNTRNSortedAndFiltered;
        }
    }

    public void saveTransactionHistory(GrnHeader grnHeader, DateAndTimeRequestDto dateAndTimeRequestDto, String grnStatus, boolean isSave) {
        List<GrnDetail> grnDetailList = grnHeader.getGrnDetailList().stream()
                .sorted(Comparator.comparingInt(GrnDetail::getTransactionSlNo))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(grnDetailList)) {
            List<TransactionHistory> transactionHistoryList = new ArrayList<>();
            String taskId = this.setTaskIdForTransactionHistory(grnHeader);
            for (GrnDetail grnDetail : grnDetailList) {
                //If grn is Completed
                if (grnStatus.equalsIgnoreCase(Completed)) {
                    List<TransactionHistory> completedList = modifyExistingRecord(grnDetail, grnHeader, dateAndTimeRequestDto, taskId);
                    transactionHistoryList.addAll(completedList);
                }
                //if grn is received or part received
                else if (!grnStatus.equalsIgnoreCase(Completed) && grnDetail.getLocationMaster() != null &&
                        grnDetail.getLocationMaster().getId() != null &&
                        grnDetail.getLocationMaster().getLocationUid() != null &&
                        !grnDetail.getLocationMaster().getLocTypeMaster().getType().equalsIgnoreCase("DOCK")) {
                    TransactionHistory transactionHistory = createTransactionHistoryFromGrnDetail(grnDetail, grnHeader, dateAndTimeRequestDto);
                    transactionHistory.setTaskId(taskId);
                    transactionHistoryList.add(transactionHistory);

                }
            }
            transactionHistoryService.save(transactionHistoryList);
        }
    }

    // Method to create TransactionHistory from GrnDetail
    public TransactionHistory createTransactionHistoryFromGrnDetail(GrnDetail grnDetail, GrnHeader grnHeader, DateAndTimeRequestDto dateAndTimeRequestDto) {

        TransactionHistory transactionHistory = new TransactionHistory();
        modelMapper.map(dateAndTimeRequestDto, transactionHistory);
        transactionHistory.setGrnDetail(grnDetail);
        transactionHistory.setBranchMaster(grnHeader.getBranchMaster());
        transactionHistory.setCompanyMaster(grnHeader.getCompanyMaster());
        transactionHistory.setCustomerMaster(grnHeader.getTrnHeaderAsnMaster().getCustomerMaster());
        transactionHistory.setGroupCompanyMaster(grnHeader.getGroupCompanyMaster());
        transactionHistory.setLocationMaster(grnDetail.getLocationMaster());
        transactionHistory.setUomMaster(grnDetail.getUomMaster());
        transactionHistory.setSkuMaster(grnDetail.getTrnDetailMaster().getSkuMaster());
        transactionHistory.setTrnHeaderAsnMaster(grnHeader.getTrnHeaderAsnMaster());
        transactionHistory.setWareHouseMaster(grnHeader.getTrnHeaderAsnMaster().getWareHouseMaster());
        transactionHistory.setTransactionSlNo(grnDetail.getTransactionSlNo());
        transactionHistory.setTransactionNo(grnHeader.getGrnRef());
        transactionHistory.setTransactionStatus("GENERATED");
        transactionHistory.setTaskStatus("ACTIVE");
        transactionHistory.setTransactionType("GRN");
        transactionHistory.setGrossWeight(grnDetail.getWeight());
        transactionHistory.setActualDate(grnDetail.getCreatedDate());
        transactionHistory.setCartonId(grnDetail.getCartonId());
        transactionHistory.setInOut("IN");
        transactionHistory.setInvStatus(grnDetail.getInventoryStatusMaster());
        transactionHistory.setLpnId(grnDetail.getLpnId());
        transactionHistory.setNetWeight(grnDetail.getWeight());
        transactionHistory.setNote(grnHeader.getTrnHeaderAsnMaster().getNote());
        transactionHistory.setSourceTrnHeaderAsnMaster(grnHeader.getTrnHeaderAsnMaster());
        transactionHistory.setQty(grnDetail.getReceivingQty());
        transactionHistory.setSourceTransactionNo(grnHeader.getGrnRef());
        transactionHistory.setSourceTransactionSlNo(grnDetail.getTransactionSlNo());
        transactionHistory.setSourceTransactionType("GRN");
        transactionHistory.setGrossWeight(grnDetail.getWeight());
        transactionHistory.setVolume(grnDetail.getVolume());
        transactionHistory.setTaskSlNo(0);
        return transactionHistory;

    }


    public List<TransactionHistory> modifyExistingRecord(GrnDetail grnDetail, GrnHeader grnHeader, DateAndTimeRequestDto dateAndTimeRequestDto, String taskId) {
        List<TransactionHistory> transactionHistoryList = transactionHistoryService.findByGrnDetailId(grnDetail.getId());
        List<TransactionHistory> returnlist = new ArrayList<>();
        TransactionLot transactionLot = createTransactionLotFromGrnDetail(grnDetail.getGrnLotDetail(), grnHeader, grnDetail, dateAndTimeRequestDto);
        checkLotEquality(transactionLot);
        transactionLotService.save(transactionLot);

        if (!CollectionUtils.isEmpty(transactionHistoryList)) {
            // Updating existing transaction history records
            for (TransactionHistory existsRecord : transactionHistoryList) {
                existsRecord.setLastModifiedBy(dateAndTimeRequestDto.getLastModifiedBy());
                existsRecord.setLastModifiedDate(dateAndTimeRequestDto.getLastModifiedDate());
                existsRecord.setTaskStatus(Completed);
                existsRecord.setTransactionNo(grnHeader.getGrnRef());
                existsRecord.setSourceTransactionNo(grnHeader.getGrnRef());
                existsRecord.setTransactionLotMaster(transactionLot);
                existsRecord.setTaskId(taskId);
                returnlist.add(existsRecord);
            }
            return returnlist;

        } else {
            // Creating a new transaction history record
            TransactionHistory transactionHistory = new TransactionHistory();
            modelMapper.map(dateAndTimeRequestDto, transactionHistory);
            transactionHistory.setGrnDetail(grnDetail);
            transactionHistory.setBranchMaster(grnHeader.getBranchMaster());
            transactionHistory.setCompanyMaster(grnHeader.getCompanyMaster());
            transactionHistory.setCustomerMaster(grnHeader.getTrnHeaderAsnMaster().getCustomerMaster());
            transactionHistory.setGroupCompanyMaster(grnHeader.getGroupCompanyMaster());
            transactionHistory.setLocationMaster(grnDetail.getLocationMaster());
            transactionHistory.setUomMaster(grnDetail.getUomMaster());
            transactionHistory.setSkuMaster(grnDetail.getTrnDetailMaster().getSkuMaster());
            transactionHistory.setTrnHeaderAsnMaster(grnHeader.getTrnHeaderAsnMaster());
            transactionHistory.setWareHouseMaster(grnHeader.getTrnHeaderAsnMaster().getWareHouseMaster());
            transactionHistory.setTransactionSlNo(grnDetail.getTransactionSlNo());
            transactionHistory.setTransactionNo(grnHeader.getGrnRef());

            if (grnDetail.getLocationMaster() != null && grnDetail.getLocationMaster().getId() != null &&
                    grnDetail.getLocationMaster().getLocationUid() != null &&
                    !"DOCK".equalsIgnoreCase(grnDetail.getLocationMaster().getLocTypeMaster().getType())) {
                transactionHistory.setTransactionStatus("GENERATED");
            } else {
                transactionHistory.setTransactionStatus("RECEIVED");
            }

            transactionHistory.setTaskStatus(Completed);
            transactionHistory.setTransactionType("GRN");
            transactionHistory.setGrossWeight(grnDetail.getWeight());
            transactionHistory.setActualDate(grnDetail.getCreatedDate());
            transactionHistory.setCartonId(grnDetail.getCartonId());
            transactionHistory.setInOut("IN");
            transactionHistory.setInvStatus(grnDetail.getInventoryStatusMaster());
            transactionHistory.setLpnId(grnDetail.getLpnId());
            transactionHistory.setNetWeight(grnDetail.getWeight());
            transactionHistory.setNote(grnHeader.getTrnHeaderAsnMaster().getNote());
            transactionHistory.setSourceTrnHeaderAsnMaster(grnHeader.getTrnHeaderAsnMaster());
            transactionHistory.setQty(grnDetail.getReceivingQty());
            transactionHistory.setSourceTransactionNo(grnHeader.getGrnRef());
            transactionHistory.setSourceTransactionSlNo(grnDetail.getTransactionSlNo());
            transactionHistory.setSourceTransactionType("GRN");
            transactionHistory.setVolume(grnDetail.getVolume());
            transactionHistory.setTaskSlNo(0);
            transactionHistory.setTaskId(taskId);
            transactionHistory.setTransactionLotMaster(transactionLot);
            returnlist.add(transactionHistory);
        }
        return returnlist;
    }

    private TransactionLot createTransactionLotFromGrnDetail(GrnLotDetail grnLotDetail, GrnHeader grnHeader, GrnDetail grnDetail, DateAndTimeRequestDto dateAndTimeRequestDto) {
        TransactionLot transactionLot = new TransactionLot();
        modelMapper.map(dateAndTimeRequestDto, transactionLot);
        transactionLot.setCustomerMaster(grnHeader.getTrnHeaderAsnMaster().getCustomerMaster());
        transactionLot.setSkuMaster(grnDetail.getTrnDetailMaster().getSkuMaster());
        transactionLot.setBatch(grnLotDetail.getBatch());
        transactionLot.setCoo(grnLotDetail.getCoo());
        transactionLot.setExpDate(grnLotDetail.getExpDate());
        transactionLot.setMfgDate(grnLotDetail.getMfgDate());
        transactionLot.setSerialNumber(grnLotDetail.getSerialNumber());
        transactionLot.setLot01(grnLotDetail.getLot01());
        transactionLot.setLot02(grnLotDetail.getLot02());
        transactionLot.setLot03(grnLotDetail.getLot03());
        transactionLot.setLot04(grnLotDetail.getLot04());
        transactionLot.setLot05(grnLotDetail.getLot05());
        transactionLot.setLot06(grnLotDetail.getLot06());
        transactionLot.setLot07(grnLotDetail.getLot07());
        transactionLot.setLot08(grnLotDetail.getLot08());
        transactionLot.setLot09(grnLotDetail.getLot09());
        transactionLot.setLot10(grnLotDetail.getLot10());
        return transactionLot;
    }

    public TransactionLot checkLotEquality(TransactionLot transactionLot) {
        List<TransactionLot> transactionLotList = transactionLotService.findAll();
        int count = 0;
        for (TransactionLot transactionLot1 : transactionLotList) {
            count = getCountNumOne(transactionLot, transactionLot1) + getCountNumTwo(transactionLot, transactionLot1)
                    + getCountNumThree(transactionLot, transactionLot1) + getCountNumFour(transactionLot, transactionLot1)
                    + getCountNumFive(transactionLot, transactionLot1) + getCountNumSix(transactionLot, transactionLot1);
            if (count == 17) {
                return transactionLot1;
            }
        }
        return transactionLot;
    }

    public int getCountNumOne(TransactionLot transactionLot, TransactionLot transactionLot1) {
        int count = 0;
        if (transactionLot1.getCustomerMaster() != null
                && transactionLot.getCustomerMaster() != null && transactionLot1.getCustomerMaster().getId().equals(transactionLot.getCustomerMaster().getId())) {
            count++;
        }
        if (transactionLot1.getCustomerMaster() == null && transactionLot.getCustomerMaster() == null) {
            count++;
        }
        if (transactionLot1.getSkuMaster() != null
                && transactionLot.getSkuMaster() != null && transactionLot1.getSkuMaster().getId().equals(transactionLot.getSkuMaster().getId())) {
            count++;
        }
        if (transactionLot1.getSkuMaster() == null && transactionLot.getSkuMaster() == null) {
            count++;
        }
        if (transactionLot1.getBatch() != null && transactionLot1.getBatch().equals(transactionLot.getBatch())) {
            count++;
        }
        if (transactionLot1.getBatch() == null && transactionLot.getBatch() == null) {
            count++;
        }
        return count;
    }

    public int getCountNumTwo(TransactionLot transactionLot, TransactionLot transactionLot1) {
        int count = 0;
        if (transactionLot1.getCoo() != null && transactionLot1.getCoo().equals(transactionLot.getCoo())) {
            count++;
        }
        if (transactionLot1.getCoo() == null && transactionLot.getCoo() == null) {
            count++;
        }
        if (transactionLot1.getExpDate() != null &&
                transactionLot.getExpDate() != null && transactionLot1.getExpDate().toString().equals(transactionLot.getExpDate().toString())) {
            count++;
        }
        if (transactionLot1.getExpDate() == null && transactionLot.getExpDate() == null) {
            count++;
        }
        if (transactionLot1.getMfgDate() != null && transactionLot.getMfgDate() != null && transactionLot1.getMfgDate().toString().equals(transactionLot.getMfgDate().toString())) {
            count++;
        }
        if (transactionLot1.getMfgDate() == null && transactionLot.getMfgDate() == null) {
            count++;
        }
        return count;
    }

    public int getCountNumThree(TransactionLot transactionLot, TransactionLot transactionLot1) {
        int count = 0;
        if (transactionLot1.getSerialNumber() != null && transactionLot1.getSerialNumber().equals(transactionLot.getSerialNumber())) {
            count++;
        }
        if (transactionLot1.getSerialNumber() == null && transactionLot.getSerialNumber() == null) {
            count++;
        }
        if (transactionLot1.getLot01() != null && transactionLot1.getLot01().equals(transactionLot.getLot01())) {
            count++;
        }
        if (transactionLot1.getLot01() == null && transactionLot.getLot01() == null) {
            count++;
        }
        if (transactionLot1.getLot02() != null && transactionLot1.getLot02().equals(transactionLot.getLot02())) {
            count++;
        }
        if (transactionLot1.getLot02() == null && transactionLot.getLot02() == null) {
            count++;
        }
        return count;
    }

    public int getCountNumFour(TransactionLot transactionLot, TransactionLot transactionLot1) {
        int count = 0;
        if (transactionLot1.getLot03() != null && transactionLot1.getLot03().equals(transactionLot.getLot03())) {
            count++;
        }
        if (transactionLot1.getLot03() == null && transactionLot.getLot03() == null) {
            count++;
        }
        if (transactionLot1.getLot04() != null && transactionLot1.getLot04().equals(transactionLot.getLot04())) {
            count++;
        }
        if (transactionLot1.getLot04() == null && transactionLot.getLot04() == null) {
            count++;
        }
        if (transactionLot1.getLot05() != null && transactionLot1.getLot05().equals(transactionLot.getLot05())) {
            count++;
        }
        if (transactionLot1.getLot05() == null && transactionLot.getLot05() == null) {
            count++;
        }
        return count;
    }

    public int getCountNumFive(TransactionLot transactionLot, TransactionLot transactionLot1) {
        int count = 0;
        if (transactionLot1.getLot06() != null && transactionLot1.getLot06().equals(transactionLot.getLot06())) {
            count++;
        }
        if (transactionLot1.getLot06() == null && transactionLot.getLot06() == null) {
            count++;
        }
        if (transactionLot1.getLot07() != null && transactionLot1.getLot07().equals(transactionLot.getLot07())) {
            count++;
        }
        if (transactionLot1.getLot07() == null && transactionLot.getLot07() == null) {
            count++;
        }
        if (transactionLot1.getLot08() != null && transactionLot1.getLot08().equals(transactionLot.getLot08())) {
            count++;
        }
        if (transactionLot1.getLot08() == null && transactionLot.getLot08() == null) {
            count++;
        }
        return count;
    }

    public int getCountNumSix(TransactionLot transactionLot, TransactionLot transactionLot1) {
        int count = 0;
        if (transactionLot1.getLot09() != null && transactionLot1.getLot09().equals(transactionLot.getLot09())) {
            count++;
        }
        if (transactionLot1.getLot09() == null && transactionLot.getLot09() == null) {
            count++;
        }
        if (transactionLot1.getLot10() != null && transactionLot1.getLot10().equals(transactionLot.getLot10())) {
            count++;
        }
        if (transactionLot1.getLot10() == null && transactionLot.getLot10() == null) {
            count++;
        }
        return count;
    }

    public String setTaskIdForTransactionHistory(GrnHeader grnHeader) {
        boolean taskIdFound = false;
        String taskId = null;
        for (GrnDetail grnDetailId : grnHeader.getGrnDetailList()) {
            List<TransactionHistory> grnDetailList = transactionHistoryService.findByGrnDetailId(grnDetailId.getId());
            for (TransactionHistory grnDetail : grnDetailList) {
                if (grnDetail.getTaskId() != null) {
                    taskId = grnDetail.getTaskId();
                    taskIdFound = true;
                    break;
                }
            }
            if (taskIdFound) {
                break;
            } else if (grnDetailList.isEmpty()) {
                taskId = transactionHistoryService.generateTaskId();
            }
        }
        return taskId;
    }

}