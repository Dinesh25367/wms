package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.PutAwayPopUpRequestDTO;
import com.newage.wms.application.dto.requestdto.PutAwayRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.PutAwayResponseDTO;
import com.newage.wms.application.dto.responsedto.PutAwaySummaryResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.*;
import com.newage.wms.service.impl.GrnCalculation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
public class PutAwayTaskHeaderMapper {


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GrnDetailService grnDetailService;

    @Autowired
    private GrnHeaderService grnHeaderService;

    @Autowired
    private CustomerMasterService customerMasterService;

    @Autowired
    private GrnCalculation grnCalculation;

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
    private PutAwayHeaderService putAwayHeaderService;

    @Autowired
    private GrnLotDetailService grnLotDetailService;

    @Autowired
    private AuthUserProfileService authUserProfileService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private GrnDetailMapper grnDetailMapper;

    private Double grnDetailQty = 0.0;

    private Double transactionHistoryQty = 0.0;

    private double totalPutAwayQty;

    public List<PutAwaySummaryResponseDTO>  convertGrnListToPutAwaySummaryResponseList(List<GrnHeader> grnHeaderList) {
        List<PutAwaySummaryResponseDTO> putAwaySummaryResponseDTOList = grnHeaderList.stream()
                .map(this::convertGrnHeaderToPutAwaySummaryResponse)
                .collect(Collectors.toList());
        log.info("mapper exit");
        return putAwaySummaryResponseDTOList;
    }

    private PutAwaySummaryResponseDTO convertGrnHeaderToPutAwaySummaryResponse(GrnHeader grnHeader) {
        Set<Long> uniqueSkuId = new HashSet<>();
        Set<String> uniqueLpnId = new HashSet<>();
        Set<String> uniqueCartonId = new HashSet<>();
        PutAwaySummaryResponseDTO putAwaySummaryResponseDTO = new PutAwaySummaryResponseDTO();
        putAwaySummaryResponseDTO.setId(grnHeader.getId());
        putAwaySummaryResponseDTO.setCreatedUser(grnHeader.getCreatedBy());
        putAwaySummaryResponseDTO.setCreatedDate(grnHeader.getCreatedDate());
        putAwaySummaryResponseDTO.setUpdatedDate(grnHeader.getLastModifiedDate());
        putAwaySummaryResponseDTO.setUpdatedUser(grnHeader.getLastModifiedBy());
        putAwaySummaryResponseDTO.setVersion(grnHeader.getVersion());
        putAwaySummaryResponseDTO.setTeam("Team one");
        putAwaySummaryResponseDTO.setCreateYesNo(transactionHistoryService.checkIfAtLeastOneGrnDetailIsGenerated(grnHeader));
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(grnHeader.getTrnHeaderAsnMaster().getBranchMaster() != null){
            modelMapper.map(grnHeader.getTrnHeaderAsnMaster().getBranchMaster(), branchDTO);
            putAwaySummaryResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(grnHeader.getTrnHeaderAsnMaster().getCompanyMaster() != null){
            modelMapper.map(grnHeader.getTrnHeaderAsnMaster().getCompanyMaster(), companyDTO);
            putAwaySummaryResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(grnHeader.getTrnHeaderAsnMaster().getGroupCompanyMaster() != null){
            modelMapper.map(grnHeader.getTrnHeaderAsnMaster().getGroupCompanyMaster(), groupCompanyDTO);
            putAwaySummaryResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        //check null and set empty string
        putAwaySummaryResponseDTO.setOrderNo(
                Optional.ofNullable(grnHeader.getTrnHeaderAsnMaster().getCustomerOrderNo())
                        .orElse("")
        );
        PutAwaySummaryResponseDTO.CustomerDTO customerDTO = new PutAwaySummaryResponseDTO.CustomerDTO();
        if(grnHeader.getTrnHeaderAsnMaster().getCustomerMaster() != null){
            modelMapper.map(grnHeader.getTrnHeaderAsnMaster().getCustomerMaster(), customerDTO);
            putAwaySummaryResponseDTO.setCustomer(customerDTO);
            if (putAwaySummaryResponseDTO.getCustomer().getName() == null){
                putAwaySummaryResponseDTO.getCustomer().setName("");
            }
        }
        String setStatus = transactionHistoryService.getPutAwayStatus(grnHeader);
        putAwaySummaryResponseDTO.setStatus(setStatus);
        //calculations
        double totalVolume = 0.0;
        for (GrnDetail grnDetail:grnHeader.getGrnDetailList()) {
            totalVolume = totalVolume + grnDetail.getVolume();
            totalVolume = grnCalculation.getRoundedValue(5,totalVolume);
            if (grnDetail.getCartonId() != null) {
                uniqueCartonId.add(grnDetail.getCartonId());
            }
            uniqueSkuId.add(grnDetail.getTrnDetailMaster().getSkuMaster().getId());
            if (grnDetail.getLpnId() != null) {
                uniqueLpnId.add(grnDetail.getLpnId());
            }
        }
        uniqueLpnId.removeIf(s -> s != null && s.isEmpty());
        uniqueCartonId.removeIf(c -> c != null && c.isEmpty());
        String formattedVolume = String.format("%8.5f", totalVolume);
        putAwaySummaryResponseDTO.setVolume(formattedVolume);
        putAwaySummaryResponseDTO.setNoOfCarton(String.valueOf(uniqueCartonId.size()));
        putAwaySummaryResponseDTO.setNoOfLpn(String.valueOf(uniqueLpnId.size()));
        putAwaySummaryResponseDTO.setNoOfSku(String.valueOf(uniqueSkuId.size()));
        putAwaySummaryResponseDTO.checkForNullAndSetDefaults();
        return putAwaySummaryResponseDTO;
    }
    public List<PutAwaySummaryResponseDTO> getFilteredResponseListSetOne(List<PutAwaySummaryResponseDTO> sortByAttribute,
                                                                         String customerName,String customerOrderNo,String team,String putAwayStatus,Pageable pageable) {
        if (customerName == null || customerName.isEmpty() || customerName.isBlank()){ customerName = ""; }
        String finalCustomerName = customerName.toLowerCase();
        if (customerOrderNo == null || customerOrderNo.isEmpty() || customerOrderNo.isBlank()) { customerOrderNo = ""; }
        String finalCustomerOrderNo = customerOrderNo.toLowerCase();
        if (team == null || team.isEmpty() || team.isBlank()) { team = ""; }
        String finalTeam = team.toLowerCase();
        if (putAwayStatus == null || putAwayStatus.isEmpty() || putAwayStatus.isBlank()) { putAwayStatus = ""; }
        String finalStatus = putAwayStatus.toLowerCase();
        List<PutAwaySummaryResponseDTO> putAwaySummaryResponseDTOList = sortByAttribute.stream()
                .filter(putAwaySummaryResponseDTO -> finalCustomerName.isEmpty() ||
                        Optional.ofNullable(putAwaySummaryResponseDTO.getCustomer().getName()).orElse("").toLowerCase().contains(finalCustomerName))
                .filter(putAwaySummaryResponseDTO -> finalTeam.isEmpty() ||
                        Optional.ofNullable(putAwaySummaryResponseDTO.getTeam()).orElse("").toLowerCase().contains(finalTeam))
                .filter(putAwaySummaryResponseDTO -> finalCustomerOrderNo.isEmpty() ||
                        Optional.ofNullable(putAwaySummaryResponseDTO.getOrderNo()).orElse("").toLowerCase().contains(finalCustomerOrderNo))
                .filter(putAwaySummaryResponseDTO -> finalStatus.isEmpty() ||
                        Optional.ofNullable(putAwaySummaryResponseDTO.getStatus()).orElse("").toLowerCase().contains(finalStatus))
                .collect(Collectors.toList());
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        if (putAwaySummaryResponseDTOList.size() < startItem) {
            putAwaySummaryResponseDTOList = Collections.emptyList(); // Return empty list if start index is greater than list size
        } else {
            int toIndex = Math.min(startItem + pageSize, putAwaySummaryResponseDTOList.size());
            putAwaySummaryResponseDTOList = putAwaySummaryResponseDTOList.subList(startItem, toIndex);
        }
        return putAwaySummaryResponseDTOList;
    }

    public List<PutAwaySummaryResponseDTO> getFilteredResponseListSetTwo(List<PutAwaySummaryResponseDTO> sortByAttribute,
                                                                         String noOfSku, String noOfLpn, String volume, String noOfCartons, Pageable pageable) {
        // Combine null, empty, and blank checks using StringUtils
        noOfSku = StringUtils.defaultString(noOfSku);
        noOfLpn = StringUtils.defaultString(noOfLpn);
        volume = StringUtils.defaultString(volume);
        noOfCartons = StringUtils.defaultString(noOfCartons);

        String finalNoOfSku = noOfSku;
        String finalNoOfCartons = noOfCartons;
        String finalNoOfLpn = noOfLpn;
        String finalVolume = volume;
        List<PutAwaySummaryResponseDTO> putAwaySummaryResponseDTOList = sortByAttribute.stream()
                .filter(dto ->
                        StringUtils.containsIgnoreCase(dto.getNoOfCarton(), finalNoOfCartons) &&
                                StringUtils.containsIgnoreCase(dto.getNoOfSku(), finalNoOfSku) &&
                                StringUtils.containsIgnoreCase(dto.getNoOfLpn(), finalNoOfLpn) &&
                                StringUtils.containsIgnoreCase(dto.getVolume(), finalVolume))
                .collect(Collectors.toList());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        if (startItem >= putAwaySummaryResponseDTOList.size()) {
            return Collections.emptyList(); // Return empty list if start index is greater than or equal to list size
        }
        int toIndex = Math.min(startItem + pageSize, putAwaySummaryResponseDTOList.size());
        return putAwaySummaryResponseDTOList.subList(startItem, toIndex);
    }

    public List<PutAwaySummaryResponseDTO> sortByAttribute(List<PutAwaySummaryResponseDTO> objects, Pageable pageable) {
        Sort sort = pageable.getSort();
        List<Sort.Order> orders = sort.toList();
        String sortProperty = null;
        String sortDirection = null;
        for (Sort.Order order : orders) {
            sortProperty = order.getProperty();
            sortDirection = order.getDirection().toString().toLowerCase();
        }
        Comparator<PutAwaySummaryResponseDTO> comparator;
        switch (sortProperty) {
            case "customerName":
                comparator = Comparator.comparing(dto -> dto.getCustomer().getName(),Comparator.nullsFirst(String::compareTo));
                break;
            case "team":
                comparator = Comparator.comparing(dto -> dto.getTeam(),Comparator.nullsFirst(String::compareTo));
                break;
            case "noOfCartons":
                comparator = Comparator.comparing(dto -> dto.getNoOfCarton(),Comparator.nullsFirst(String::compareTo));
                break;
            case "noOfLpn":
                comparator = Comparator.comparing(dto -> dto.getNoOfLpn(),Comparator.nullsFirst(String::compareTo));
                break;
            case "customerOrderNo":
                comparator = Comparator.comparing(dto -> dto.getOrderNo(),Comparator.nullsFirst(String::compareTo));
                break;
            case "noOfSku":
                comparator = Comparator.comparing(dto -> dto.getNoOfSku(),Comparator.nullsFirst(String::compareTo));
                break;
            case "grnRef":
                comparator = Comparator.comparing(dto -> dto.getVolume(),Comparator.nullsFirst(String::compareTo));
                break;
            case "status":
                comparator = Comparator.comparing(dto -> dto.getStatus(),Comparator.nullsFirst(String::compareTo));
                break;
            default:
                comparator = Comparator.comparing(dto -> dto.getId(),Comparator.nullsLast(Long::compareTo));
        }
        if ( sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return objects.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public PutAwayTaskHeader convertRequestToEntityWithGrnId(PutAwayPopUpRequestDTO putAwayPopUpRequestDTO, Long grnId, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY -  PutAway Header Request to PutAwayHeader entity mapper");
        //check if Grn Id is Already exists
        Long putAwayHeaderId = getPutAwayTaskHeaderIdIfExists(grnId);
        if (putAwayHeaderId != null) {
            return putAwayHeaderService.findById(putAwayHeaderId);
        } else {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure Strict matching
            PutAwayTaskHeader putAwayTaskHeader = modelMapper.map(putAwayPopUpRequestDTO, PutAwayTaskHeader.class);
            modelMapper.map(dateAndTimeRequestDto, putAwayTaskHeader);
            putAwayTaskHeader.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(putAwayPopUpRequestDTO.getGroupCompanyId()));        //Set unmapped objects
            putAwayTaskHeader.setCompanyMaster(companyMasterService.getCompanyById(putAwayPopUpRequestDTO.getCompanyId()));
            putAwayTaskHeader.setBranchMaster(branchMasterService.getBranchById(putAwayPopUpRequestDTO.getBranchId()));
            putAwayTaskHeader.setGrnHeader(grnHeaderService.findById(grnId));
            log.info("EXIT");
            return putAwayTaskHeader;
        }
    }

    public PutAwayTaskHeader convertRequestToEntity(PutAwayRequestDTO putAwayRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY -  PutAway Header Request to PutAwayHeader entity mapper");
        //if grn is already exists
        Long grnId = putAwayRequestDTO.getPutAwayTaskHeader().getGrnId();
        Long putAwayHeaderId= getPutAwayTaskHeaderIdIfExists(grnId);
        if (putAwayHeaderId != null) {
            return putAwayHeaderService.findById(putAwayHeaderId);

        }else {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure Strict matching
            PutAwayTaskHeader putAwayTaskHeader = modelMapper.map(putAwayRequestDTO.getPutAwayTaskHeader(), PutAwayTaskHeader.class);
            modelMapper.map(dateAndTimeRequestDto, putAwayTaskHeader);
            putAwayTaskHeader.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(putAwayRequestDTO.getGroupCompanyId()));        //Set unmapped objects
            putAwayTaskHeader.setCompanyMaster(companyMasterService.getCompanyById(putAwayRequestDTO.getCompanyId()));
            putAwayTaskHeader.setBranchMaster(branchMasterService.getBranchById(putAwayRequestDTO.getBranchId()));
            putAwayTaskHeader.setGrnHeader(grnHeaderService.findById(putAwayRequestDTO.getPutAwayTaskHeader().getGrnId()));
            log.info("EXIT");
            return putAwayTaskHeader;
        }
    }

    public PutAwayResponseDTO convertEntityToResponse(Long grnId,Optional<TransactionHistory> optionalTransactionHistory,Long userId) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        GrnHeader grnHeader = grnHeaderService.findById(grnId);
        PutAwayResponseDTO putAwayResponseDTO = new PutAwayResponseDTO();
        PutAwayResponseDTO.PutAwayTaskHeaderDTO putAwayTaskHeaderDTO = new PutAwayResponseDTO.PutAwayTaskHeaderDTO();
        if (optionalTransactionHistory.isPresent()) {
            //if PutAwayTaskHeader exists for GRN, then map PutAwayTask header to Response
            TransactionHistory transactionHistory = optionalTransactionHistory.get();
            putAwayTaskHeaderDTO = setPutAwayTaskHeaderResponseDTOWithPutAwayTaskHeader(transactionHistory,grnHeader);
            putAwayResponseDTO.setPutAwayTaskHeader(putAwayTaskHeaderDTO);
            setDefaultResponseInPutAwayResponseDTOFromPutAwayTaskHeader(putAwayResponseDTO,transactionHistory);
            putAwayResponseDTO.setId(transactionHistory.getId());
        } else {
            //if no PutAwayTak exists then map only available details from GRN
            putAwayTaskHeaderDTO = setPutAwayTaskHeaderResponseDTOWithoutPutAwayTaskHeader(grnHeader,userId);
            putAwayResponseDTO.setPutAwayTaskHeader(putAwayTaskHeaderDTO);
            putAwayResponseDTO.setId(null);
        }
        //set the putAwayTaskDetailList in response from GrnDetail list
        List<PutAwayResponseDTO.PutAwayTaskDetailDTO> putAwayTaskDetailDTOS = new ArrayList<>();
        List<GrnDetail> grnDetailList = grnHeader.getGrnDetailList();
        if (!CollectionUtils.isEmpty(grnDetailList)){
            int counter = 0;
            grnDetailQty = 0.0;
            for (GrnDetail grnDetail : grnDetailList) {

                GrnLotDetail grnLotDetail = grnDetail.getGrnLotDetail();
                UomMaster uomMaster = grnDetail.getUomMaster();
                double rounding = uomMaster.getDecimalPlaces();

                PutAwayResponseDTO.PutAwayTaskDetailDTO putAwayTaskDetailDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO();
                PutAwayResponseDTO.PutAwayTaskDetailDTO.SkuFormDTO skuFormDTO = setPutAwayTaskDetailWithGrnDetail(grnDetail);
                skuFormDTO.setTransactionSlNo(grnDetail.getTransactionSlNo());
                List<TransactionHistory> transactionHistoryList= transactionHistoryService.findByGrnDetailId(grnDetail.getId());
                boolean editRights = transactionHistoryService.putAwayCancelRights(userId);
                for (TransactionHistory transactionHistory:transactionHistoryList){
                    if (editRights || transactionHistory.getTransactionStatus().equalsIgnoreCase("RECEIVED")) {
                        skuFormDTO.setIsEditable("Yes");
                    } else {
                        skuFormDTO.setIsEditable("No");
                    }
                }
                boolean isPopUpDisable = transactionHistoryService.isPopUpRights(grnDetail.getId());
                skuFormDTO.setIsDisablePopup(!isPopUpDisable || editRights ? "No" : "Yes");

                putAwayTaskDetailDTO.setSkuForm(skuFormDTO);
                PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO grnDetailDTO = convertGrnDetailEntityToResponse(grnDetail);

                modelMapper.map(grnLotDetail,grnDetailDTO);
                if (grnLotDetail.getHsCodeMaster() != null){
                    PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.HsCodeDTO hsCodeDTO = modelMapper.map(grnLotDetail.getHsCodeMaster(),PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.HsCodeDTO.class);
                    grnDetailDTO.setHsCode(hsCodeDTO);
                }
                List<PutAwayResponseDTO.PutAwayTaskDetailDTO.LotFormDTO> lotFormDTO = new ArrayList<>();

                // Create a new LotFormDTO object for each index i
                PutAwayResponseDTO.PutAwayTaskDetailDTO.LotFormDTO lotItem = new PutAwayResponseDTO.PutAwayTaskDetailDTO.LotFormDTO();

                // Set properties from grnLotDetailList
                lotItem.setGrnLotId((grnLotDetail.getId()));
                lotItem.setLot01(grnLotDetail.getLot01());
                lotItem.setLot02(grnLotDetail.getLot02());
                lotItem.setLot03(grnLotDetail.getLot03());
                lotItem.setLot04(grnLotDetail.getLot04());
                lotItem.setLot05(grnLotDetail.getLot05());
                lotItem.setLot06(grnLotDetail.getLot06());
                lotItem.setLot07(grnLotDetail.getLot07());
                lotItem.setLot08(grnLotDetail.getLot08());
                lotItem.setLot09(grnLotDetail.getLot09());
                lotItem.setLot10(grnLotDetail.getLot10());

                // Add the created LotFormDTO object to lotFormDTO list
                lotFormDTO.add(lotItem);
                skuFormDTO.setLot(lotFormDTO);

                List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = grnDetailMapper.convertTrnDetailToLotListDTO(grnLotDetail, grnDetail.getTrnDetailMaster());
                skuFormDTO.setMoreLotList(lotDTOList);
                skuFormDTO.setGrnDetail(grnDetailDTO);

                List<PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails> putAwayPopupDetailsList =
                        setPutAwayTaskDetailsToPopupResponse(grnDetail,rounding,userId);
                putAwayTaskDetailDTO.setPutawayPopupDetailsList(putAwayPopupDetailsList);
                grnDetailQty = grnDetail.getReceivingQty();
                Double toPutAwayQty = grnDetailQty - totalPutAwayQty;
                Double putAwayQty= transactionHistoryService.getPutAwayQty(grnDetail.getId());
                toPutAwayQty = grnCalculation.getRoundedValue(rounding,toPutAwayQty);
                skuFormDTO.setToPutAwayQty(toPutAwayQty);
                skuFormDTO.setPutAwayQty(putAwayQty);
                Double putAwayVolume= transactionHistoryService.getPutAwayVolume(grnDetail.getId());
                Double putAwayWeight=transactionHistoryService.getPutAwayWeight(grnDetail.getId());
                putAwayVolume=grnCalculation.getRoundedValue(5,putAwayVolume);
                putAwayWeight=grnCalculation.getRoundedValue(5,putAwayWeight);
                if(putAwayVolume==0.0) {
                    skuFormDTO.setVolume(grnDetail.getVolume());
                }
                else {
                    skuFormDTO.setVolume(putAwayVolume);
                }
                if(putAwayWeight==0.0){
                    skuFormDTO.setWeight(grnDetail.getWeight());
                }else{
                    skuFormDTO.setWeight(putAwayWeight);
                }
                String detailStatus = getStatusForOneDetail(grnDetailQty,totalPutAwayQty);
                skuFormDTO.setStatus(detailStatus);
                putAwayTaskDetailDTOS.add(putAwayTaskDetailDTO);
                counter++;
            }
        }
        putAwayResponseDTO.setPutAwayTaskDetailList(putAwayTaskDetailDTOS);
        return putAwayResponseDTO;
    }

    private String getStatusForOneDetail(Double grnDetailQty, Double totalPutAwayQty) {
        String detailStatus ="COMPLETED";
        if (totalPutAwayQty.equals(0.0)){
            detailStatus = "PENDING";
        }
        if (totalPutAwayQty>0.0 && totalPutAwayQty < grnDetailQty){
            detailStatus = "INPROGRESS";
        }
        return  detailStatus;
    }

    private PutAwayResponseDTO.PutAwayTaskHeaderDTO setPutAwayTaskHeaderResponseDTOWithPutAwayTaskHeader(TransactionHistory transactionHistory,GrnHeader grnHeader) {
        PutAwayResponseDTO.PutAwayTaskHeaderDTO putAwayTaskHeaderDTO = new PutAwayResponseDTO.PutAwayTaskHeaderDTO();
        putAwayTaskHeaderDTO.setGrnId(transactionHistory.getGrnDetail().getId());
        putAwayTaskHeaderDTO.setTaskId(transactionHistory.getTaskId());
        putAwayTaskHeaderDTO.setTaskStatus(transactionHistoryService.getPutAwayStatus(grnHeader));
        putAwayTaskHeaderDTO.setCreatedBy(transactionHistory.getCreatedBy());
        putAwayTaskHeaderDTO.setCreatedOn(transactionHistory.getCreatedDate());
        putAwayTaskHeaderDTO.setTaskStarted(transactionHistory.getCreatedDate());
        String taskStatus=transactionHistoryService.getPutAwayStatus(grnHeader);
        if(taskStatus.equalsIgnoreCase("completed")) {
            putAwayTaskHeaderDTO.setTaskCompleted(transactionHistory.getLastModifiedDate());
        }
        return putAwayTaskHeaderDTO;
    }

    private PutAwayResponseDTO.PutAwayTaskHeaderDTO setPutAwayTaskHeaderResponseDTOWithoutPutAwayTaskHeader(GrnHeader grnHeader,Long userId) {
        AuthUserProfile authUserProfile = authUserProfileService.getById(userId);
        PutAwayResponseDTO.PutAwayTaskHeaderDTO putAwayTaskHeaderDTO = new PutAwayResponseDTO.PutAwayTaskHeaderDTO();
        putAwayTaskHeaderDTO.setGrnId(grnHeader.getId());
        String taskId = putAwayHeaderService.getPutAwayTaskIdFormat();
        putAwayTaskHeaderDTO.setTaskId(taskId+"XXXXX");
        String taskStatus = grnHeaderService.getPutAwayTaskStatus(grnHeader);
        putAwayTaskHeaderDTO.setTaskStatus(taskStatus);
        putAwayTaskHeaderDTO.setCreatedBy(authUserProfile.getUserName());
        putAwayTaskHeaderDTO.setCreatedOn(new Date());
        putAwayTaskHeaderDTO.setTaskStarted(new Date());
        return putAwayTaskHeaderDTO;
    }

    private PutAwayResponseDTO setDefaultResponseInPutAwayResponseDTOFromPutAwayTaskHeader(PutAwayResponseDTO putAwayResponseDTO,TransactionHistory transactionHistory) {
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(transactionHistory.getBranchMaster()!=null) {
            modelMapper.map(transactionHistory.getBranchMaster(), branchDTO);
            putAwayResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(transactionHistory.getCompanyMaster()!=null) {
            modelMapper.map(transactionHistory.getCompanyMaster(), companyDTO);
            putAwayResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(transactionHistory.getGroupCompanyMaster()!=null) {
            modelMapper.map(transactionHistory.getGroupCompanyMaster(), groupCompanyDTO);
            putAwayResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        putAwayResponseDTO.setCreatedUser(transactionHistory.getCreatedBy());
        putAwayResponseDTO.setCreatedDate(transactionHistory.getCreatedDate());
        putAwayResponseDTO.setUpdatedUser(transactionHistory.getLastModifiedBy());
        putAwayResponseDTO.setUpdatedDate(transactionHistory.getLastModifiedDate());
        return putAwayResponseDTO;
    }

//    private PutAwayResponseDTO setDefaultResponseInPutAwayResponseDTOWithoutPutAwayTaskHeader(PutAwayResponseDTO putAwayResponseDTO, GrnHeader grnHeader) {
//        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
//        if(putAwayTaskHeader.getBranchMaster()!=null) {
//            modelMapper.map(putAwayTaskHeader.getBranchMaster(), branchDTO);
//            putAwayResponseDTO.setBranch(branchDTO);
//        }
//        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
//        if(putAwayTaskHeader.getCompanyMaster()!=null) {
//            modelMapper.map(putAwayTaskHeader.getCompanyMaster(), companyDTO);
//            putAwayResponseDTO.setCompany(companyDTO);
//        }
//        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
//        if(putAwayTaskHeader.getGroupCompanyMaster()!=null) {
//            modelMapper.map(putAwayTaskHeader.getGroupCompanyMaster(), groupCompanyDTO);
//            putAwayResponseDTO.setGroupCompany(groupCompanyDTO);
//        }
//        putAwayResponseDTO.setCreatedUser("-");
//        putAwayResponseDTO.setCreatedDate(putAwayTaskHeader.getCreatedDate());
//        putAwayResponseDTO.setUpdatedUser(putAwayTaskHeader.getLastModifiedBy());
//        putAwayResponseDTO.setUpdatedDate(putAwayTaskHeader.getLastModifiedDate());
//        return putAwayResponseDTO;
//    }

    private PutAwayResponseDTO.PutAwayTaskDetailDTO.SkuFormDTO setPutAwayTaskDetailWithGrnDetail(GrnDetail grnDetail) {
        PutAwayResponseDTO.PutAwayTaskDetailDTO.SkuFormDTO skuFormDTO = modelMapper.map(grnDetail, PutAwayResponseDTO.PutAwayTaskDetailDTO.SkuFormDTO.class);
        skuFormDTO.setGrnDetailId(grnDetail.getId());
        PutAwayResponseDTO.PutAwayTaskDetailDTO.SkuDTO skuDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.SkuDTO();
        if (grnDetail.getTrnDetailMaster().getSkuMaster() != null) {
            modelMapper.map(grnDetail.getTrnDetailMaster().getSkuMaster(), skuDTO);
            skuFormDTO.setSku(skuDTO);
        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.UomDTO uomDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.UomDTO();
        if (grnDetail.getUomMaster() != null) {
            modelMapper.map(grnDetail.getUomMaster(), uomDTO);
            skuFormDTO.setUom(uomDTO);
        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.UomDTO ruomDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.UomDTO();
        if (grnDetail.getRUomMaster() != null) {
            modelMapper.map(grnDetail.getRUomMaster(), ruomDTO);
            skuFormDTO.setRUom(ruomDTO);
        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.LocationDTO toLoc = new PutAwayResponseDTO.PutAwayTaskDetailDTO.LocationDTO();
        if (grnDetail.getLocationMaster() != null) {
            modelMapper.map(grnDetail.getLocationMaster(), toLoc);
            skuFormDTO.setToLoc(toLoc);
        }
        Double putAwayVolume= transactionHistoryService.getPutAwayVolume(grnDetail.getId());
        Double putAwayWeight=transactionHistoryService.getPutAwayWeight(grnDetail.getId());
        skuFormDTO.setVolume(putAwayVolume);
        skuFormDTO.setWeight(putAwayWeight);
        skuFormDTO.setUomQty(grnDetail.getReceivingQty());
        skuFormDTO.setRuomQty(grnDetail.getReceivingRQty());
        return skuFormDTO;
    }

    private List<PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails> setPutAwayTaskDetailsToPopupResponse( GrnDetail grnDetail,double rounding, Long userId) {
        List<PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails> putAwayPopupDetailsList = new ArrayList<>();
        transactionHistoryQty = 0.0;
        List<TransactionHistory> transactionHistoryList = transactionHistoryService.filteredByGrnDetailId(grnDetail.getId());
        if (!CollectionUtils.isEmpty(transactionHistoryList)) {
            totalPutAwayQty = 0.0;
            for (TransactionHistory transactionHistory : transactionHistoryList) {
                if (transactionHistory.getTaskStatus().equalsIgnoreCase("COMPLETED") &&
                        transactionHistory.getTransactionStatus().equalsIgnoreCase("GENERATED")) {
                    PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails putAwayPopupDetails =
                            convertPopupDetailToResponse(transactionHistory,userId,rounding);
                    totalPutAwayQty += transactionHistory.getQty();
                    putAwayPopupDetailsList.add(putAwayPopupDetails);
                }
            }
            totalPutAwayQty = grnCalculation.getRoundedValue(rounding,totalPutAwayQty);
        }
        return putAwayPopupDetailsList;
    }



    public PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails convertPopupDetailToResponse(TransactionHistory transactionHistory, Long userId,double rounding) {
        PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails putAwayPopupDetails = modelMapper.map(transactionHistory, PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails.class);
        PutAwayResponseDTO.PutAwayTaskDetailDTO.SkuDTO skuDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.SkuDTO();
        if (transactionHistory.getSkuMaster() != null) {
            modelMapper.map(transactionHistory.getSkuMaster() ,skuDTO);
            putAwayPopupDetails.setSku(skuDTO);
        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.UomDTO uomDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.UomDTO();
        if (transactionHistory.getUomMaster() != null) {
            modelMapper.map(transactionHistory.getUomMaster(), uomDTO);
            putAwayPopupDetails.setUom(uomDTO);
        }

        boolean editRights = transactionHistoryService.putAwayCancelRights(userId);
        if (editRights || transactionHistory.getTransactionStatus().equalsIgnoreCase("RECEIVED")) {
            putAwayPopupDetails.setIsEditable("Yes");
        }
        else{
            putAwayPopupDetails.setIsEditable("No");
        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.UomDTO rUomDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.UomDTO();
        if (transactionHistory.getUomMaster() != null) {
            modelMapper.map(transactionHistory.getUomMaster(), rUomDTO);
            putAwayPopupDetails.setRuom(rUomDTO);
        }
//        PutAwayResponseDTO.PutAwayTaskDetailDTO.LocationDTO sugLoc = new PutAwayResponseDTO.PutAwayTaskDetailDTO.LocationDTO();
//        if (transactionHistory.getSugLoc() != null) {
//            modelMapper.map(transactionHistory.getSugLoc(), sugLoc);
//            putAwayPopupDetails.setSugLoc(sugLoc);
//        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.LocationDTO toLoc = new PutAwayResponseDTO.PutAwayTaskDetailDTO.LocationDTO();
        if (transactionHistory.getLocationMaster() != null) {
            modelMapper.map(transactionHistory.getLocationMaster() , toLoc);
            putAwayPopupDetails.setToLoc(toLoc);
        }
        putAwayPopupDetails.setGrnDetailId(transactionHistory.getGrnDetail().getId());
        grnDetailQty = transactionHistory.getGrnDetail().getReceivingQty();
        Double toPutAwayQty = grnDetailQty - totalPutAwayQty;
        Double putAwayQty= transactionHistoryService.getPutAwayQty(transactionHistory.getGrnDetail().getId());
        toPutAwayQty = grnCalculation.getRoundedValue(rounding,toPutAwayQty);
        putAwayPopupDetails.setToPutAwayQty(toPutAwayQty);
        putAwayPopupDetails.setPutAwayQty(putAwayQty);
        putAwayPopupDetails.setUomQty(grnDetailQty);
        putAwayPopupDetails.setWeight(transactionHistory.getNetWeight());
        putAwayPopupDetails.setVolume(transactionHistory.getVolume());
        putAwayPopupDetails.setRUomQty(grnDetailQty);
        transactionHistoryQty =transactionHistoryQty + transactionHistory.getQty();
        return putAwayPopupDetails;
    }

    private PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO convertGrnDetailEntityToResponse(GrnDetail grnDetail) {
        PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO grnDetailDTO = modelMapper.map(grnDetail,PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.class);
        PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.UomDTO uomDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.UomDTO();
        PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.DimensionalDTO dimensionalDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.DimensionalDTO() ;
        dimensionalDTO.setHeight(grnDetail.getHeight());
        dimensionalDTO.setWidth(grnDetail.getWidth());
        dimensionalDTO.setUnit(grnDetail.getUnit());
        dimensionalDTO.setLength(grnDetail.getLength());
        dimensionalDTO.setIsChecked(grnDetail.getIsChecked());
        grnDetailDTO.setDimensionalDetails(dimensionalDTO);

        if (grnDetail.getUomMaster() != null){
            modelMapper.map(grnDetail.getUomMaster(),uomDTO);
            grnDetailDTO.setUom(uomDTO);
        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.LocationDTO locationDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.LocationDTO();
        if (grnDetail.getLocationMaster() != null){
            modelMapper.map(grnDetail.getLocationMaster(),locationDTO);
            grnDetailDTO.setLocation(locationDTO);
        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.InventoryStatusDTO inventoryStatusDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.InventoryStatusDTO();
        if (grnDetail.getInventoryStatusMaster() != null) {
            modelMapper.map(grnDetail.getInventoryStatusMaster(),inventoryStatusDTO);
            grnDetailDTO.setInventoryStatus(inventoryStatusDTO);
        }
        PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.TrnHeaderTransportationDTO transportationDTO = new PutAwayResponseDTO.PutAwayTaskDetailDTO.GrnDetailDTO.TrnHeaderTransportationDTO();
        if (grnDetail.getTrnHeaderTransportation() != null) {
            modelMapper.map(grnDetail.getTrnHeaderTransportation(),transportationDTO);
            grnDetailDTO.setTransportation(transportationDTO);
        }
        grnDetailDTO.setGrnDetailId(grnDetail.getId());
        return grnDetailDTO;
    }



    public Long getPutAwayTaskHeaderIdIfExists(Long grnId) {
        log.info("Checking if the grnId  already exists");
        List<PutAwayTaskHeader> putAwayTaskHeaderList = putAwayHeaderService.fetchAll();

        return putAwayTaskHeaderList.stream()
                .filter(putAwayTaskHeader -> putAwayTaskHeader.getGrnHeader().getId().equals(grnId))
                .map(PutAwayTaskHeader::getId)
                .findFirst()
                .orElse(null);
    }


    public List<PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails> collectCompletedPutAwayDetails(PutAwayResponseDTO putAwayResponseDTO) {
        List<PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails> completedPutAwayPopupDetailsList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(putAwayResponseDTO.getPutAwayTaskDetailList())){
            for (PutAwayResponseDTO.PutAwayTaskDetailDTO putAwayTaskDetailDTO : putAwayResponseDTO.getPutAwayTaskDetailList()){
                if (!CollectionUtils.isEmpty(putAwayTaskDetailDTO.getPutawayPopupDetailsList())){
                    completedPutAwayPopupDetailsList.addAll(putAwayTaskDetailDTO.getPutawayPopupDetailsList());
                }
            }
        }
        return completedPutAwayPopupDetailsList;
    }

}