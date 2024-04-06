package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.requestdto.InventoryStatusRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.GrnHeaderResponseDTO;
import com.newage.wms.application.dto.responsedto.InventoryStatusResponseDTO;
import com.newage.wms.entity.InventoryStatusMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import com.newage.wms.service.InventoryStatusMasterService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class InventoryStatusMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InventoryStatusMasterService inventoryStatusMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private GrnHeaderMapper grnHeaderMapper;

    public Page<InventoryStatusResponseDTO> convertInventoryStatusPageToResponsePage(Page<InventoryStatusMaster> inventoryStatusMasterPage){
        log.info("ENTRY - InventoryStatus Page to InventoryStatusResponse Mapper");
        List<InventoryStatusResponseDTO> inventoryStatusResponseDTOList = inventoryStatusMasterPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(inventoryStatusResponseDTOList,inventoryStatusMasterPage.getPageable(),inventoryStatusMasterPage.getTotalElements());

    }

    public Iterable<InventoryStatusResponseDTO> convertInventoryStatusIterableToResponseIterable(Iterable<InventoryStatusMaster> inventoryStatusMasterIterable) {
        log.info("ENTRY - InventoryStatus Iterable to Response Iterable");
        return StreamSupport.stream(inventoryStatusMasterIterable.spliterator(),false)
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    public InventoryStatusResponseDTO convertEntityToResponse(InventoryStatusMaster inventoryStatusMaster){
        log.info("ENTRY - InventoryStatus Master to InventoryStatusResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        InventoryStatusResponseDTO inventoryStatusResponseDTO=modelMapper.map(inventoryStatusMaster,InventoryStatusResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO=new DefaultFieldsResponseDTO.BranchDTO();
        if (inventoryStatusMaster.getBranchMaster()!=null){
            modelMapper.map(inventoryStatusMaster.getBranchMaster(),branchDTO);
            inventoryStatusResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO=new DefaultFieldsResponseDTO.CompanyDTO();
        if (inventoryStatusMaster.getCompanyMaster()!=null){
            modelMapper.map(inventoryStatusMaster.getCompanyMaster(),companyDTO);
            inventoryStatusResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO= new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if (inventoryStatusMaster.getGroupCompanyMaster()!=null){
            modelMapper.map(inventoryStatusMaster.getGroupCompanyMaster(),groupCompanyDTO);
            inventoryStatusResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        inventoryStatusResponseDTO.setCreatedUser(inventoryStatusMaster.getCreatedBy());
        inventoryStatusResponseDTO.setCreatedDate(inventoryStatusMaster.getCreatedDate());
        inventoryStatusResponseDTO.setUpdatedUser(inventoryStatusMaster.getLastModifiedBy());
        inventoryStatusResponseDTO.setUpdatedDate(inventoryStatusMaster.getLastModifiedDate());
        log.info("EXIT");
        return inventoryStatusResponseDTO;
    }

    public InventoryStatusMaster convertRequestToEntity(InventoryStatusRequestDTO inventoryStatusRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - InventoryStatusRequest to InventoryStatus Mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        InventoryStatusMaster inventoryStatusMaster=modelMapper.map(inventoryStatusRequestDTO,InventoryStatusMaster.class);
        modelMapper.map(dateAndTimeRequestDto,inventoryStatusMaster);
        inventoryStatusMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(inventoryStatusRequestDTO.getGroupCompanyId())));
        inventoryStatusMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(inventoryStatusRequestDTO.getCompanyId())));
        inventoryStatusMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(inventoryStatusRequestDTO.getBranchId())));
        inventoryStatusMaster.setCode(inventoryStatusRequestDTO.getCode().toUpperCase());
        inventoryStatusMaster.setName(inventoryStatusRequestDTO.getName().toUpperCase());
        log.info("EXIT");
        return inventoryStatusMaster;
    }

    public void convertUpdateRequestToEntity(InventoryStatusRequestDTO inventoryStatusRequestDTO,InventoryStatusMaster inventoryStatusMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - Update InventoryStatusMasterRequest to InventoryStatus Master");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(inventoryStatusRequestDTO,inventoryStatusMaster);
        modelMapper.map(dateAndTimeRequestDto,inventoryStatusMaster);
        inventoryStatusMaster.setCode(inventoryStatusRequestDTO.getCode().toUpperCase());
        inventoryStatusMaster.setName(inventoryStatusRequestDTO.getName().toUpperCase());
    }

}
