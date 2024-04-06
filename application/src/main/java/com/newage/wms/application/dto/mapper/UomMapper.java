package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.UomRequestDTO;
import com.newage.wms.application.dto.responsedto.UomResponseDTO;
import com.newage.wms.entity.UomMaster;
import com.newage.wms.service.*;
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
public class UomMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UomMasterService uomMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private CategoryMasterService categoryMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    /*
     * Method to convert Uom Page to Uom Response Page
     * @Return Page<UomResponseDTO>
     */
    public Page<UomResponseDTO> convertUomPageToUomResponsePage(Page<UomMaster> uomMasterPage){
        log.info("ENTRY - Uom Page to Uom Response Page mapper");
        List<UomResponseDTO> uomResponseDTOList = uomMasterPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(uomResponseDTOList,uomMasterPage.getPageable(),uomMasterPage.getTotalElements());
    }

    /*
     * Method to convert UomMasterIterable to UomResponseDTOIterable
     * @Return Iterable<UomMasterDto>
     */
    public Iterable<UomResponseDTO> convertUomMasterIterableToUomResponseDTOIterable(Iterable<UomMaster> uomMasterIterable){
        log.info("ENTRY - UomMasterIterable to UomResponseDTOIterable mapper");
        return StreamSupport.stream(uomMasterIterable.spliterator(),false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());

    }

    /*
     * Method to convert UomRequestDto to UomMaster entity
     * @Return UomMaster
     */
    public UomMaster convertRequestDtoToEntity(UomRequestDTO uomRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - RackRequestDto to Rack mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        UomMaster uomMaster = modelMapper.map(uomRequestDTO,UomMaster.class);
        modelMapper.map(dateAndTimeRequestDto,uomMaster);                                            // map date, time and version
        uomMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(uomRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        uomMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(uomRequestDTO.getCompanyId())));
        uomMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(uomRequestDTO.getBranchId())));
        if (uomRequestDTO.getCategory() != null){
            uomMaster.setCategoryMaster(categoryMasterService.getCategoryById(Long.parseLong(uomRequestDTO.getCategory().getId())));
        }
        if (uomRequestDTO.getDecimalPlaces() == null){
            uomMaster.setDecimalPlaces(0L);
        }
        uomMaster.setCode(uomRequestDTO.getCode().toUpperCase());
        log.info("EXIT");
        return uomMaster;
    }

    /*
     * Method to convert update RackRequestDto to RackMaster entity
     */
    public void convertUpdateRequestToEntity(UomRequestDTO uomRequestDTO, UomMaster uomMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - update RackRequestDto to Rack mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(uomRequestDTO,uomMaster);
        modelMapper.map(dateAndTimeRequestDto,uomMaster);                                            // map date, time and version
        uomMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(uomRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        uomMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(uomRequestDTO.getCompanyId())));
        uomMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(uomRequestDTO.getBranchId())));
        if (uomRequestDTO.getCategory() != null){
            uomMaster.setCategoryMaster(categoryMasterService.getCategoryById(Long.parseLong(uomRequestDTO.getCategory().getId())));
        }else {uomMaster.setCategoryMaster(null);}
        if (uomRequestDTO.getDecimalPlaces() == null){
            uomMaster.setDecimalPlaces(0L);
        }
        uomMaster.setCode(uomRequestDTO.getCode().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert UomMaster entity to UomResponseDTO
     * @Return UomResponseDTO
     */
    public UomResponseDTO convertEntityToDTO(UomMaster uomMaster){
        log.info("ENTRY -  UomMaster to UomResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UomResponseDTO uomResponseDTO = modelMapper.map(uomMaster, UomResponseDTO.class);
        UomResponseDTO.GroupCompanyDTO groupCompanyDTO = new UomResponseDTO.GroupCompanyDTO();
        if(uomMaster.getGroupCompanyMaster()!=null){
            modelMapper.map(uomMaster.getGroupCompanyMaster(),groupCompanyDTO);
            uomResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        UomResponseDTO.CompanyDTO companyDTO = new UomResponseDTO.CompanyDTO();
        if(uomMaster.getCompanyMaster()!=null){
            modelMapper.map(uomMaster.getCompanyMaster(),companyDTO);
            uomResponseDTO.setCompany(companyDTO);
        }
        UomResponseDTO.BranchDTO branchDTO = new UomResponseDTO.BranchDTO();
        if(uomMaster.getBranchMaster()!=null){
            modelMapper.map(uomMaster.getBranchMaster(),branchDTO);
            uomResponseDTO.setBranch(branchDTO);
        }
        UomResponseDTO.CategoryResponseDTO categoryResponseDTO = new UomResponseDTO.CategoryResponseDTO();
        if(uomMaster.getCategoryMaster()!=null){
            modelMapper.map(uomMaster.getCategoryMaster(),categoryResponseDTO);
            uomResponseDTO.setCategory(categoryResponseDTO);
        }
        uomResponseDTO.setCreatedUser(uomMaster.getCreatedBy());
        uomResponseDTO.setUpdatedDate(uomMaster.getLastModifiedDate());
        uomResponseDTO.setUpdatedUser(uomMaster.getLastModifiedBy());
        log.info("EXIT");
        return uomResponseDTO;
    }

}

