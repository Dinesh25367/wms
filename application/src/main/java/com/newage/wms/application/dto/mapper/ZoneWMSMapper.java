package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.ZoneWMSRequestDTO;
import com.newage.wms.application.dto.responsedto.LocationResponseDTO;
import com.newage.wms.application.dto.responsedto.ZoneWMSResponseDTO;
import com.newage.wms.entity.ZoneMasterWMS;
import com.newage.wms.service.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class ZoneWMSMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private StorageAreaMasterService storageAreaMasterService;

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private StorageTypeMasterService storageTypeMasterService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DoorMasterService doorMasterService;

    /*
     * Method to convert Zone Page to Zone Response Page
     * @Return Page<ZoneResponseDTO>
     */
    public Page<ZoneWMSResponseDTO> convertZonePageToZoneResponsePage(Page<ZoneMasterWMS> zoneMasterWMSPage){
        log.info("ENTRY - ZoneMasterWMS Page to ZoneResponse Page mapper");
        List<ZoneWMSResponseDTO> zoneResponseDTOList = zoneMasterWMSPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(zoneResponseDTOList,zoneMasterWMSPage.getPageable(),zoneMasterWMSPage.getTotalElements());
    }

    /*
     * Method to convert ZoneMasterWMSIterable to ZoneMasterWMSDTOIterable
     * @Return Iterable<LocationResponseDto.ZoneDTO>
     */
    public Iterable<LocationResponseDTO.ZoneDTO> convertZoneMasterIterableToZoneMasterDTOIterable(Iterable<ZoneMasterWMS> zoneMasterWMSIterable){
        log.info("ENTRY - ZoneMasterIterable to LocationResponseDto.ZoneDTO mapper");
        return StreamSupport.stream(zoneMasterWMSIterable.spliterator(), false)
                .map(this::convertEntityToZoneAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert ZoneRequestDto to ZoneWMS entity
     * @Return ZoneMasterWMS entity
     */
    public ZoneMasterWMS convertRequestDtoToEntity(ZoneWMSRequestDTO zoneRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - ZoneRequestDto to Zone mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        ZoneMasterWMS zoneMasterWMS = modelMapper.map(zoneRequestDTO,ZoneMasterWMS.class);
        modelMapper.map(dateAndTimeRequestDto,zoneMasterWMS);                                            // map date, time and version
        zoneMasterWMS.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(zoneRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        zoneMasterWMS.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(zoneRequestDTO.getCompanyId())));
        zoneMasterWMS.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(zoneRequestDTO.getBranchId())));
        if (zoneRequestDTO.getWareHouse() != null){
            zoneMasterWMS.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(zoneRequestDTO.getWareHouse().getId())));
        }
        if (zoneRequestDTO.getStorageArea() != null){
            zoneMasterWMS.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(zoneRequestDTO.getStorageArea().getId())));
        }
        if (zoneRequestDTO.getStorageType() != null){
            zoneMasterWMS.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(zoneRequestDTO.getStorageType().getId())));
        }
        if (zoneRequestDTO.getInLocationId() != null){
            zoneMasterWMS.setInLocationMaster(locationService.getLocationById(Long.parseLong(zoneRequestDTO.getInLocationId().getId())));
        }
        if (zoneRequestDTO.getOutLocationId() != null){
            zoneMasterWMS.setOutLocationMaster(locationService.getLocationById(Long.parseLong(zoneRequestDTO.getOutLocationId().getId())));
        }
        if (zoneRequestDTO.getDoorId() != null){
            zoneMasterWMS.setDoorMaster(doorMasterService.findById(Long.parseLong(zoneRequestDTO.getDoorId().getId())));
        }
        zoneMasterWMS.setCode(zoneRequestDTO.getCode().toUpperCase());
        zoneMasterWMS.setName(zoneRequestDTO.getName().toUpperCase());
        log.info("EXIT");
        return zoneMasterWMS;
    }

    /*
     * Method to convert update ZoneRequestDto to zoneMasterWMS entity
     */
    public void convertUpdateRequestToEntity(ZoneWMSRequestDTO zoneRequestDTO, ZoneMasterWMS zoneMasterWMS, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - update ZoneRequestDto to zoneMasterWMS mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(zoneRequestDTO, zoneMasterWMS);
        modelMapper.map(dateAndTimeRequestDto, zoneMasterWMS);
        zoneMasterWMS.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(zoneRequestDTO.getGroupCompanyId())));
        zoneMasterWMS.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(zoneRequestDTO.getCompanyId())));
        zoneMasterWMS.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(zoneRequestDTO.getBranchId())));
        if (zoneRequestDTO.getWareHouse() != null){
            zoneMasterWMS.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(zoneRequestDTO.getWareHouse().getId())));
        }else {zoneMasterWMS.setWareHouseMaster(null);}
        if (zoneRequestDTO.getStorageArea() != null){
            zoneMasterWMS.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(zoneRequestDTO.getStorageArea().getId())));
        }else {zoneMasterWMS.setStorageAreaMaster(null);}
        if (zoneRequestDTO.getStorageType() != null){
            zoneMasterWMS.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(zoneRequestDTO.getStorageType().getId())));
        }else {zoneMasterWMS.setStorageTypeMaster(null);}
        if (zoneRequestDTO.getInLocationId() != null){
            zoneMasterWMS.setInLocationMaster(locationService.getLocationById(Long.parseLong(zoneRequestDTO.getInLocationId().getId())));
        }else {zoneMasterWMS.setInLocationMaster(null);}
        if (zoneRequestDTO.getOutLocationId() != null){
            zoneMasterWMS.setOutLocationMaster(locationService.getLocationById(Long.parseLong(zoneRequestDTO.getOutLocationId().getId())));
        }else {zoneMasterWMS.setOutLocationMaster(null);}
        if (zoneRequestDTO.getDoorId() != null){
            zoneMasterWMS.setDoorMaster(doorMasterService.findById(Long.parseLong(zoneRequestDTO.getDoorId().getId())));
        }else {zoneMasterWMS.setDoorMaster(null);}
        zoneMasterWMS.setCode(zoneRequestDTO.getCode().toUpperCase());
        zoneMasterWMS.setName(zoneRequestDTO.getName().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert ZoneMasterWMS entity to DTO
     * @Return ZoneResponseDTO
     */
    public ZoneWMSResponseDTO convertEntityToDTO(ZoneMasterWMS zoneMasterWMS){
        log.info("ENTRY - ZoneMaster to ZoneMasterDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        ZoneWMSResponseDTO zoneResponseDTO = modelMapper.map(zoneMasterWMS, ZoneWMSResponseDTO.class);
        ZoneWMSResponseDTO.BranchDTO branchDTO = new ZoneWMSResponseDTO.BranchDTO();
        if(zoneMasterWMS.getBranchMaster()!=null) {
            modelMapper.map(zoneMasterWMS.getBranchMaster(), branchDTO);
            zoneResponseDTO.setBranch(branchDTO);
        }
        ZoneWMSResponseDTO.CompanyDTO companyDTO = new ZoneWMSResponseDTO.CompanyDTO();
        if(zoneMasterWMS.getCompanyMaster()!=null) {
            modelMapper.map(zoneMasterWMS.getCompanyMaster(), companyDTO);
            zoneResponseDTO.setCompany(companyDTO);
        }
        ZoneWMSResponseDTO.GroupCompanyDTO groupCompanyDTO = new ZoneWMSResponseDTO.GroupCompanyDTO();
        if(zoneMasterWMS.getGroupCompanyMaster()!=null) {
            modelMapper.map(zoneMasterWMS.getGroupCompanyMaster(), groupCompanyDTO);
            zoneResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        ZoneWMSResponseDTO.WareHouseDTO wareHouseDTO=new ZoneWMSResponseDTO.WareHouseDTO();
        if(zoneMasterWMS.getWareHouseMaster()!=null) {
            modelMapper.map(zoneMasterWMS.getWareHouseMaster(), wareHouseDTO);
            zoneResponseDTO.setWareHouse(wareHouseDTO);
        }
        ZoneWMSResponseDTO.StorageAreaDTO storageAreaDTO=new ZoneWMSResponseDTO.StorageAreaDTO();
        if(zoneMasterWMS.getStorageAreaMaster()!=null){
            modelMapper.map(zoneMasterWMS.getStorageAreaMaster() , storageAreaDTO);
            zoneResponseDTO.setStorageArea(storageAreaDTO);
        }
        ZoneWMSResponseDTO.StorageTypeDTO storageTypeDTO=new ZoneWMSResponseDTO.StorageTypeDTO();
        if(zoneMasterWMS.getStorageTypeMaster()!=null){
            modelMapper.map(zoneMasterWMS.getStorageTypeMaster(),storageTypeDTO);
            zoneResponseDTO.setStorageType(storageTypeDTO);
        }
        ZoneWMSResponseDTO.LocationDTO inLocationDTO=new ZoneWMSResponseDTO.LocationDTO();
        if(zoneMasterWMS.getInLocationMaster()!=null){
            modelMapper.map(zoneMasterWMS.getInLocationMaster(),inLocationDTO);
            zoneResponseDTO.setInLocationId(inLocationDTO);
        }
        ZoneWMSResponseDTO.LocationDTO outLocationDTO=new ZoneWMSResponseDTO.LocationDTO();
        if(zoneMasterWMS.getOutLocationMaster()!=null){
            modelMapper.map(zoneMasterWMS.getOutLocationMaster(),outLocationDTO);
            zoneResponseDTO.setOutLocationId(outLocationDTO);
        }
        ZoneWMSResponseDTO.DoorDTO doorDTO=new ZoneWMSResponseDTO.DoorDTO();
        if(zoneMasterWMS.getDoorMaster()!=null){
            modelMapper.map(zoneMasterWMS.getDoorMaster(),doorDTO);
            zoneResponseDTO.setDoorId(doorDTO);
        }
        zoneResponseDTO.setCreatedUser(zoneMasterWMS.getCreatedBy());
        zoneResponseDTO.setUpdatedDate(zoneMasterWMS.getLastModifiedDate());
        zoneResponseDTO.setUpdatedUser(zoneMasterWMS.getLastModifiedBy());
        log.info("EXIT");
        return zoneResponseDTO;
    }

    /*
     *Method to convert ZoneWMS Entity to ZoneAutoCompleteDTO
     * @Return ZoneAutoCompleteResponseDTO
     */
    public LocationResponseDTO.ZoneDTO convertEntityToZoneAutoCompleteDTO(ZoneMasterWMS zoneMasterWMS){
        log.info("ENTRY - ZoneMasterWMS to ZoneAutoCompleteDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        LocationResponseDTO.ZoneDTO zoneAutoCompleteResponseDTO = modelMapper.map(zoneMasterWMS, LocationResponseDTO.ZoneDTO.class);
        if (zoneMasterWMS.getStorageAreaMaster()!=null){
            zoneAutoCompleteResponseDTO.setStorageAreaId(zoneMasterWMS.getStorageAreaMaster().getId());
        }
        if (zoneMasterWMS.getStorageTypeMaster()!=null){
            zoneAutoCompleteResponseDTO.setStorageTypeId(zoneMasterWMS.getStorageTypeMaster().getId());
        }
        if (zoneMasterWMS.getWareHouseMaster()!=null){
            zoneAutoCompleteResponseDTO.setWarehouseId(zoneMasterWMS.getWareHouseMaster().getId());
        }
        if (zoneMasterWMS.getInLocationMaster()!=null){
            zoneAutoCompleteResponseDTO.setInLocationId(zoneMasterWMS.getInLocationMaster().getId());
        }
        if (zoneMasterWMS.getOutLocationMaster()!=null){
            zoneAutoCompleteResponseDTO.setOutLocationId(zoneMasterWMS.getOutLocationMaster().getId());
        }
        if (zoneMasterWMS.getDoorMaster()!=null){
            zoneAutoCompleteResponseDTO.setDoorId(zoneMasterWMS.getDoorMaster().getId());
        }
        log.info("EXIT");
        return zoneAutoCompleteResponseDTO;
    }

}
