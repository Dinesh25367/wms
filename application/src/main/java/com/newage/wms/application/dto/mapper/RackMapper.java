package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.RackRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.LocationResponseDTO;
import com.newage.wms.application.dto.responsedto.RackResponseDTO;
import com.newage.wms.entity.RackMaster;
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
public class RackMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AisleMasterService aisleMasterService;
    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private StorageAreaMasterService storageAreaMasterService;

    @Autowired
    private StorageTypeMasterService storageTypeMasterService;

    /*
     * Method to convert Rack Page to Rack Response Page
     * @Return Page<RackResponseDTO>
     */
    public Page<RackResponseDTO> convertRackPageToRackResponsePage(Page<RackMaster> rackPage){
        log.info("ENTRY - RackMaster Page to RackResponse Page mapper");
        List<RackResponseDTO> rackResponseDTOList = rackPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(rackResponseDTOList,rackPage.getPageable(),rackPage.getTotalElements());
    }

    /*
     * Method to convert RackMasterIterable to RackMasterDTOIterable
     * @Return Iterable<LocationResponseDto.RackDTO>
     */
    public Iterable<LocationResponseDTO.RackDTO> convertRackMasterIterableToRackMasterDTOIterable(Iterable<RackMaster> rackMasterIterable){
        log.info("ENTRY EXIT - RackMasterIterable to LocationResponseDto.RackDTO mapper");
        return StreamSupport.stream(rackMasterIterable.spliterator(), false)
                .map(this::convertRackEntityToRackAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert RackRequestDto to RackMaster entity
     * @Return RackMaster
     */
    public RackMaster convertRequestDtoToEntity(RackRequestDTO rackRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - RackRequestDto to Rack mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        RackMaster rackMaster = modelMapper.map(rackRequestDTO,RackMaster.class);
        modelMapper.map(dateAndTimeRequestDto,rackMaster);                                            // map date, time and version
        rackMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(rackRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        rackMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(rackRequestDTO.getCompanyId())));
        rackMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(rackRequestDTO.getBranchId())));
        if (rackRequestDTO.getWareHouse() != null){
            rackMaster.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(rackRequestDTO.getWareHouse().getId())));
        }
        if (rackRequestDTO.getAisle() != null){
            rackMaster.setAisleMaster(aisleMasterService.getAisleById(Long.parseLong(rackRequestDTO.getAisle().getId())));
        }
        if (rackRequestDTO.getStorageArea() != null){
            rackMaster.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(rackRequestDTO.getStorageArea().getId())));
        }
        if (rackRequestDTO.getStorageType() != null){
            rackMaster.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(rackRequestDTO.getStorageType().getId())));
        }
        rackMaster.setName(rackRequestDTO.getName().toUpperCase());
        rackMaster.setCode(rackRequestDTO.getCode().toUpperCase());
        log.info("EXIT");
        return rackMaster;
    }

    /*
     * Method to convert update RackRequestDto to RackMaster entity
     */
    public void convertUpdateRequestToEntity(RackRequestDTO rackRequestDTO, RackMaster rackMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - update RackRequestDto to Rack mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(rackRequestDTO,rackMaster);
        modelMapper.map(dateAndTimeRequestDto,rackMaster);                                            // map date, time and version
        rackMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(rackRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        rackMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(rackRequestDTO.getCompanyId())));
        rackMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(rackRequestDTO.getBranchId())));
        if (rackRequestDTO.getWareHouse() != null){
            rackMaster.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(rackRequestDTO.getWareHouse().getId())));
        }else {rackMaster.setWareHouseMaster(null);}
        if (rackRequestDTO.getAisle() != null){
            rackMaster.setAisleMaster(aisleMasterService.getAisleById(Long.parseLong(rackRequestDTO.getAisle().getId())));
        }else {rackMaster.setAisleMaster(null);}
        if (rackRequestDTO.getStorageArea() != null){
            rackMaster.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(rackRequestDTO.getStorageArea().getId())));
        }else {rackMaster.setStorageAreaMaster(null);}
        if (rackRequestDTO.getStorageType() != null){
            rackMaster.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(rackRequestDTO.getStorageType().getId())));
        }else {rackMaster.setStorageTypeMaster(null);}
        rackMaster.setName(rackRequestDTO.getName().toUpperCase());
        rackMaster.setCode(rackRequestDTO.getCode().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert RackMaster entity to RackResponseDTO
     * @Return RackResponseDTO
     */
    public RackResponseDTO convertEntityToDTO(RackMaster rackMaster){
        log.info("ENTRY - RackMaster to RackResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        RackResponseDTO rackMasterDTO = modelMapper.map(rackMaster, RackResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(rackMaster.getBranchMaster()!=null) {
            modelMapper.map(rackMaster.getBranchMaster(), branchDTO);
            rackMasterDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(rackMaster.getCompanyMaster()!=null) {
            modelMapper.map(rackMaster.getCompanyMaster(), companyDTO);
            rackMasterDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(rackMaster.getGroupCompanyMaster()!=null) {
            modelMapper.map(rackMaster.getGroupCompanyMaster(), groupCompanyDTO);
            rackMasterDTO.setGroupCompany(groupCompanyDTO);
        }
        RackResponseDTO.StorageAreaDTO storageAreaDTO=new RackResponseDTO.StorageAreaDTO();
        if(rackMaster.getStorageAreaMaster()!=null){
            modelMapper.map(rackMaster.getStorageAreaMaster(), storageAreaDTO);
            rackMasterDTO.setStorageArea(storageAreaDTO);
        }
        RackResponseDTO.StorageTypeDTO storageTypeDTO=new RackResponseDTO.StorageTypeDTO();
        if(rackMaster.getStorageTypeMaster()!=null){
            modelMapper.map(rackMaster.getStorageTypeMaster(), storageTypeDTO);
            rackMasterDTO.setStorageType(storageTypeDTO);
        }
        RackResponseDTO.AisleDTO aisleDTO=new RackResponseDTO.AisleDTO();
        if(rackMaster.getAisleMaster()!=null){
            modelMapper.map(rackMaster.getAisleMaster(), aisleDTO);
            rackMasterDTO.setAisle(aisleDTO);
            if (rackMaster.getAisleMaster().getWareHouseMaster() != null) {
                aisleDTO.setWareHouseId(rackMaster.getAisleMaster().getWareHouseMaster().getId());
            }
            if (rackMaster.getAisleMaster().getZoneMaster() != null) {
                aisleDTO.setZoneId(rackMaster.getAisleMaster().getZoneMaster().getId());
            }
            if (rackMaster.getAisleMaster().getStorageAreaMaster() != null) {
                aisleDTO.setStorageAreaId(rackMaster.getAisleMaster().getStorageAreaMaster().getId());
            }
            if (rackMaster.getAisleMaster().getStorageTypeMaster() != null) {
                aisleDTO.setStorageTypeId(rackMaster.getAisleMaster().getStorageTypeMaster().getId());
            }
            rackMasterDTO.setAisle(aisleDTO);
        }
        RackResponseDTO.WareHouseDTO wareHouseDTO = new RackResponseDTO.WareHouseDTO();
        if(rackMaster.getWareHouseMaster()!=null){
            modelMapper.map(rackMaster.getWareHouseMaster(), wareHouseDTO);
            rackMasterDTO.setWareHouse(wareHouseDTO);
        }
        rackMasterDTO.setCreatedUser(rackMaster.getCreatedBy());
        rackMasterDTO.setUpdatedDate(rackMaster.getLastModifiedDate());
        rackMasterDTO.setUpdatedUser(rackMaster.getLastModifiedBy());
        log.info("EXIT");
        return rackMasterDTO;
    }

    /*
     * Method to convert RackMaster entity to LocationResponseDto.RackDTO
     * @Return LocationResponseDto.RackDTO
     */
    public LocationResponseDTO.RackDTO convertRackEntityToRackAutoCompleteDTO(RackMaster rackMaster){
        log.info("ENTRY - RackMaster to LocationResponseDto.RackDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        LocationResponseDTO.RackDTO rackAutoCompleteResponseDTO = modelMapper.map(rackMaster, LocationResponseDTO.RackDTO.class);
        if (rackMaster.getStorageAreaMaster()!=null){
            rackAutoCompleteResponseDTO.setStorageAreaId(rackMaster.getStorageAreaMaster().getId());
        }
        if (rackMaster.getStorageTypeMaster()!=null){
            rackAutoCompleteResponseDTO.setStorageTypeId(rackMaster.getStorageTypeMaster().getId());
        }
        if (rackMaster.getAisleMaster()!=null){
            rackAutoCompleteResponseDTO.setAisleId(rackMaster.getAisleMaster().getId());
        }
        if (rackMaster.getWareHouseMaster()!=null){
            rackAutoCompleteResponseDTO.setWarehouseId(rackMaster.getWareHouseMaster().getId());
        }
        log.info("EXIT");
        return rackAutoCompleteResponseDTO;
    }

}
