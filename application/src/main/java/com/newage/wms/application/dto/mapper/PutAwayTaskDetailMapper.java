package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.PutAwayPopUpRequestDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class PutAwayTaskDetailMapper {
    @Autowired
    private ModelMapper modelMapper;

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

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private PutAwayTaskDetailService putAwayTaskDetailService;

    Boolean status = true;

    public List<PutAwayTaskDetails> convertPopUpRequestListToEntityList(PutAwayTaskHeader putAwayTaskHeader, PutAwayPopUpRequestDTO putAwayPopUpRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        if (!CollectionUtils.isEmpty(putAwayPopUpRequestDTO.getPutawayPopupDetailsList())) {
            List<PutAwayTaskDetails> putAwayTaskDetailsList = convertPopUpRequestListToEntityList(putAwayTaskHeader,putAwayPopUpRequestDTO.getPutawayPopupDetailsList(),dateAndTimeRequestDto,putAwayPopUpRequestDTO);

            //get all the grnDetailId's in the receiving data
            List<Long> grnDetailIds = putAwayTaskDetailsList.stream()
                    .map(taskDetail -> taskDetail.getGrnDetail().getId())
                    .collect(Collectors.toList());

            List<PutAwayTaskDetails> putAwayTaskDetailsList1=putAwayTaskDetailService.findAll();

            // compare the grnDetailId's in the PutAwayTaskDetails table
            PutAwayTaskDetails putAwayTaskDetails = putAwayTaskDetailsList1.stream()
                    .filter(taskDetail -> grnDetailIds.contains(taskDetail.getGrnDetail().getId()))
                    .findFirst()
                    .orElse(null);

            String taskId;
            if (putAwayTaskDetails != null) {
                taskId = putAwayTaskDetails.getPutAwayTaskHeader().getTaskId();
            }else {
                taskId = transactionHistoryService.generateTaskId();
            }
            putAwayTaskHeader.setTaskId(taskId);
            if (status) {
                putAwayTaskHeader.setTaskStatus("COMPLETED");
            } else {
                putAwayTaskHeader.setTaskStatus("IN PROGRESS");
            }
            return putAwayTaskDetailsList;
        } else {
            return Collections.emptyList();
        }
    }

    private List<PutAwayTaskDetails> convertPopUpRequestListToEntityList(PutAwayTaskHeader putAwayTaskHeader,List<PutAwayPopUpRequestDTO.PutAwayPopupDetails> putAwayPopupDetails1,
                                                                         DateAndTimeRequestDto dateAndTimeRequestDto,PutAwayPopUpRequestDTO putAwayPopUpRequestDTO) {

        List<PutAwayTaskDetails> putAwayTaskDetailsList = new ArrayList<>();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);// Ensure strict matching
        Integer slNo=1;
        for (PutAwayPopUpRequestDTO.PutAwayPopupDetails putAwayPopupDetails: putAwayPopupDetails1){
            PutAwayTaskDetails putAwayTaskDetails = modelMapper.map(putAwayPopupDetails, PutAwayTaskDetails.class);
            modelMapper.map(dateAndTimeRequestDto,putAwayTaskDetails);
            putAwayTaskDetails.setTransactionSlNo(slNo);

            putAwayTaskDetails.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(putAwayPopUpRequestDTO.getGroupCompanyId()));        //Set unmapped objects
            putAwayTaskDetails.setCompanyMaster(companyMasterService.getCompanyById(putAwayPopUpRequestDTO.getCompanyId()));
            putAwayTaskDetails.setBranchMaster(branchMasterService.getBranchById(putAwayPopUpRequestDTO.getBranchId()));

            if (putAwayPopupDetails.getRuom() != null) {
                UomMaster uomMaster = uomMasterService.getUomById(putAwayPopupDetails.getRuom().getId());
                putAwayTaskDetails.setRuomMaster(uomMaster);
            }
            if (putAwayPopupDetails.getUom() != null) {
                UomMaster uomMaster = uomMasterService.getUomById(putAwayPopupDetails.getUom().getId());
                putAwayTaskDetails.setUomMaster(uomMaster);
            }
            if (putAwayPopupDetails.getSku() != null) {
                SkuMaster skuMaster = skuMasterService.getById(putAwayPopupDetails.getSku().getId());
                putAwayTaskDetails.setSkuMaster(skuMaster);
            }
            if (putAwayPopupDetails.getSugLoc() != null) {
                Location location = locationService.getLocationById(putAwayPopupDetails.getSugLoc().getId());
                putAwayTaskDetails.setSugLoc(location);
            }
            if (putAwayPopupDetails.getToLoc() != null) {
                Location location = locationService.getLocationById(putAwayPopupDetails.getToLoc().getId());
                putAwayTaskDetails.setToLoc(location);
            }
            if (putAwayPopupDetails.getGrnDetailId()!= null) {
                GrnDetail grnDetail = grnDetailService.getById(putAwayPopupDetails.getGrnDetailId());
                putAwayTaskDetails.setGrnDetail(grnDetail);
                putAwayTaskDetails.setTransactionLot(null); // Hardcode value
            }
            if (!putAwayTaskDetails.getStatus().equalsIgnoreCase("completed")){
                status=false;
            }
            putAwayTaskDetails.setPutAwayTaskHeader(putAwayTaskHeader);
            putAwayTaskDetailsList.add(putAwayTaskDetails);
            slNo++;
        }
        return putAwayTaskDetailsList;
    }
}


