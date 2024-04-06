package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.AisleRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.AisleResponseDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.LocationResponseDTO;
import com.newage.wms.entity.AisleMaster;
import com.newage.wms.entity.ZoneMasterWMS;
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
public class AisleMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private ZoneMasterWMSService zoneMasterWMSService;

    @Autowired
    private StorageAreaMasterService storageAreaMasterService;

    @Autowired
    private StorageTypeMasterService storageTypeMasterService;

    /*
     * Method to convert Aisle Page to Aisle Response Page
     * @Return Aisle response Page
     */
    public Page<AisleResponseDTO> convertAislePageToAisleResponsePage(Page<AisleMaster> aislePage){
        log.info("ENTRY - AisleMaster Page to AisleResponse Page mapper");
        List<AisleResponseDTO> aisleResponseDTOList = aislePage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(aisleResponseDTOList,aislePage.getPageable(),aislePage.getTotalElements());
    }

    /*
     * Method to convert AisleMasterIterable to AisleAutoCompleteResponseDTO Iterable
     * @Return Iterable<LocationResponseDto.AisleDTO>
     */
    public Iterable<LocationResponseDTO.AisleDTO> convertAisleMasterIterableToAilseMasterDTOIterable(Iterable<AisleMaster> aisleMasterIterable){
        log.info("ENTRY -EXIT - AisleMasterIterable to LocationResponseDto.AisleDTO mapper");
        return StreamSupport.stream(aisleMasterIterable.spliterator(), false)
                .map(this::convertAisleEntityToAisleAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert AisleRequestDto to AisleMaster entity
     * @Return AisleMaster entity
     */
    public AisleMaster convertRequestDtoToEntity(AisleRequestDTO aisleRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - AisleRequestDto to Aisle mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        AisleMaster aisleMaster = modelMapper.map(aisleRequestDTO,AisleMaster.class);
        modelMapper.map(dateAndTimeRequestDto,aisleMaster);                                            // map date, time and version
        aisleMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(aisleRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        aisleMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(aisleRequestDTO.getCompanyId())));
        aisleMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(aisleRequestDTO.getBranchId())));
        if (aisleRequestDTO.getWareHouse() != null){
            aisleMaster.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(aisleRequestDTO.getWareHouse().getId())));
        }
        if (aisleRequestDTO.getZone() != null){
            aisleMaster.setZoneMaster(zoneMasterWMSService.getZoneMasterWMSById(Long.parseLong(aisleRequestDTO.getZone().getId())));
        }
        if (aisleRequestDTO.getStorageArea() != null){
            aisleMaster.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(aisleRequestDTO.getStorageArea().getId())));
        }
        if (aisleRequestDTO.getStorageType() != null){
            aisleMaster.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(aisleRequestDTO.getStorageType().getId())));
        }
        aisleMaster.setCode(aisleRequestDTO.getCode().toUpperCase());
        aisleMaster.setName(aisleRequestDTO.getName().toUpperCase());
        log.info("EXIT");
        return aisleMaster;
    }

    /*
     * Method to convert update AisleRequestDto to AisleMaster entity
     */
    public void convertUpdateRequestToEntity(AisleRequestDTO aisleRequestDTO, AisleMaster aisleMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - update AisleRequestDto to Aisle mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(aisleRequestDTO,aisleMaster);
        modelMapper.map(dateAndTimeRequestDto,aisleMaster);                                            // map date, time and version
        aisleMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(aisleRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        aisleMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(aisleRequestDTO.getCompanyId())));
        aisleMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(aisleRequestDTO.getBranchId())));
        if (aisleRequestDTO.getWareHouse() != null){
            aisleMaster.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(aisleRequestDTO.getWareHouse().getId())));
        }else {aisleMaster.setWareHouseMaster(null);}
        if (aisleRequestDTO.getZone() != null){
            aisleMaster.setZoneMaster(zoneMasterWMSService.getZoneMasterWMSById(Long.parseLong(aisleRequestDTO.getZone().getId())));
        }else {aisleMaster.setZoneMaster(null);}
        if (aisleRequestDTO.getStorageArea() != null){
            aisleMaster.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(aisleRequestDTO.getStorageArea().getId())));
        }else {aisleMaster.setStorageAreaMaster(null);}
        if (aisleRequestDTO.getStorageType() != null){
            aisleMaster.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(aisleRequestDTO.getStorageType().getId())));
        }else {aisleMaster.setStorageTypeMaster(null);}
        aisleMaster.setCode(aisleRequestDTO.getCode().toUpperCase());
        aisleMaster.setName(aisleRequestDTO.getName().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert AisleMaster entity to AisleResponseDTO
     * @Return AisleResponseDTO
     */
    public AisleResponseDTO convertEntityToDTO(AisleMaster aisleMaster){
        log.info("ENTRY - AisleMaster to AisleResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        AisleResponseDTO aisleResponseDTO = modelMapper.map(aisleMaster, AisleResponseDTO.class);
        aisleResponseDTO.setBranch(mapToDTOIfNotNull(aisleMaster.getBranchMaster(), DefaultFieldsResponseDTO.BranchDTO.class));
        aisleResponseDTO.setCompany(mapToDTOIfNotNull(aisleMaster.getCompanyMaster(), DefaultFieldsResponseDTO.CompanyDTO.class));
        aisleResponseDTO.setGroupCompany(mapToDTOIfNotNull(aisleMaster.getGroupCompanyMaster(), DefaultFieldsResponseDTO.GroupCompanyDTO.class));
        aisleResponseDTO.setWareHouse(mapToDTOIfNotNull(aisleMaster.getWareHouseMaster(), AisleResponseDTO.WareHouseDTO.class));
        aisleResponseDTO.setStorageArea(mapToDTOIfNotNull(aisleMaster.getStorageAreaMaster(), AisleResponseDTO.StorageAreaDTO.class));
        aisleResponseDTO.setStorageType(mapToDTOIfNotNull(aisleMaster.getStorageTypeMaster(), AisleResponseDTO.StorageTypeDTO.class));
        aisleResponseDTO.setZone(mapToZoneDTOIfNotNull(aisleMaster));
        aisleResponseDTO.setCreatedUser(aisleMaster.getCreatedBy());
        aisleResponseDTO.setUpdatedDate(aisleMaster.getLastModifiedDate());
        aisleResponseDTO.setUpdatedUser(aisleMaster.getLastModifiedBy());
        log.info("EXIT");
        return aisleResponseDTO;
    }

    /*
     * Method to convert Aisle Master entity to AisleAutoCompleteResponseDTO
     * @Return LocationResponseDto.AisleDTO
     */
    public LocationResponseDTO.AisleDTO convertAisleEntityToAisleAutoCompleteDTO(AisleMaster aisleMaster){
        log.info("ENTRY - AisleMaster to LocationResponseDto.AisleDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        LocationResponseDTO.AisleDTO aisleAutoCompleteResponseDTO = modelMapper.map(aisleMaster, LocationResponseDTO.AisleDTO.class);
        if (aisleMaster.getStorageAreaMaster()!=null){
            aisleAutoCompleteResponseDTO.setStorageAreaId(aisleMaster.getStorageAreaMaster().getId());
        }
        if (aisleMaster.getStorageTypeMaster()!=null){
            aisleAutoCompleteResponseDTO.setStorageTypeId(aisleMaster.getStorageTypeMaster().getId());
        }
        if (aisleMaster.getZoneMaster()!=null){
            aisleAutoCompleteResponseDTO.setZoneId(aisleMaster.getZoneMaster().getId());
        }
        if (aisleMaster.getWareHouseMaster()!=null){
            aisleAutoCompleteResponseDTO.setWarehouseId(aisleMaster.getWareHouseMaster().getId());
        }
        log.info("EXIT");
        return aisleAutoCompleteResponseDTO;
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

    /*
     * Method to map to ZoneDTO
     * @Return AisleResponseDTO.ZoneDTO
     */
    private AisleResponseDTO.ZoneDTO mapToZoneDTOIfNotNull(AisleMaster aisleMaster) {
        log.info("ENTRY - ZoneDTO mapper");
        AisleResponseDTO.ZoneDTO zoneDTO=new AisleResponseDTO.ZoneDTO();
        if(aisleMaster.getZoneMaster()!=null){
            modelMapper.map(aisleMaster.getZoneMaster(),zoneDTO);
            if (aisleMaster.getZoneMaster().getWareHouseMaster() != null) {
                zoneDTO.setWarehouseId(aisleMaster.getZoneMaster().getWareHouseMaster().getId());
            }
            if (aisleMaster.getZoneMaster().getStorageAreaMaster() != null) {
                zoneDTO.setStorageAreaId(aisleMaster.getZoneMaster().getStorageAreaMaster().getId());
            }
            if (aisleMaster.getZoneMaster().getStorageTypeMaster() != null) {
                zoneDTO.setStorageTypeId(aisleMaster.getZoneMaster().getStorageTypeMaster().getId());
            }
            if (aisleMaster.getZoneMaster().getInLocationMaster() != null) {
                zoneDTO.setInLocationId(aisleMaster.getZoneMaster().getInLocationMaster().getId());
            }
            if (aisleMaster.getZoneMaster().getOutLocationMaster() != null) {
                zoneDTO.setOutLocationId(aisleMaster.getZoneMaster().getOutLocationMaster().getId());
            }
            if (aisleMaster.getZoneMaster().getDoorMaster() != null) {
                zoneDTO.setDoorId(aisleMaster.getZoneMaster().getDoorMaster().getId());
            }
            return zoneDTO;
        }
        return null;
    }

}
