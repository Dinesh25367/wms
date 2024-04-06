package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.DoorRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.DoorResponseDTO;
import com.newage.wms.application.dto.responsedto.ZoneWMSResponseDTO;
import com.newage.wms.entity.DoorMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import com.newage.wms.service.WareHouseService;
import lombok.Getter;
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
@Getter
public class DoorMapper {

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

    public Iterable<ZoneWMSResponseDTO.DoorDTO> convertEntityIterableToResponseIterable(Iterable<DoorMaster> doorMasterIterable) {
        return StreamSupport.stream(doorMasterIterable.spliterator(), false)
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    private ZoneWMSResponseDTO.DoorDTO convertEntityToResponse(DoorMaster doorMaster) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        ZoneWMSResponseDTO.DoorDTO doorDTO = modelMapper.map(doorMaster, ZoneWMSResponseDTO.DoorDTO.class);
        log.info("EXIT");
        return doorDTO;
    }

    /*
     * Method to convert StorageArea Page to StorageArea Response Page
     * @Return Page<StorageAreaResponseDTO>
     */
    public Page<DoorResponseDTO> convertDoorPageToDoorResponsePage(Page<DoorMaster> doorMasterPage){
        log.info("ENTRY - StorageAreaMaster Page to StorageAreaResponse Page mapper");
        List<DoorResponseDTO> doorResponseDTOList = doorMasterPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(doorResponseDTOList,doorMasterPage.getPageable(),doorMasterPage.getTotalElements());
    }

    /*
     * Method to convert StorageTypeRequestDto to StorageType entity
     * @Return Location entity
     */
    public DoorMaster convertRequestDtoToEntity(DoorRequestDTO doorRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - DoorRequestDTO to Door mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);           // Ensure strict matching
        DoorMaster doorMaster = modelMapper.map(doorRequestDTO,DoorMaster.class);
        modelMapper.map(dateAndTimeRequestDto,doorMaster);                                            // map date, time and version
        doorMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(doorRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        doorMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(doorRequestDTO.getCompanyId())));
        doorMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(doorRequestDTO.getBranchId())));
        doorMaster.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(doorRequestDTO.getWareHouse().getId())));
        doorMaster.setCode(doorRequestDTO.getCode().toUpperCase());
        doorMaster.setName(doorRequestDTO.getName().toUpperCase());
        log.info("EXIT");
        return doorMaster;
    }

    /*
     * Method to convert update StorageTypeRequestDto to StorageType entity
     */
    public void convertUpdateRequestToEntity(DoorRequestDTO doorRequestDTO, DoorMaster doorMaster, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - update DoorRequestDTO to Door mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(doorRequestDTO, doorMaster);
        modelMapper.map(dateAndTimeRequestDto, doorMaster);
        doorMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(doorRequestDTO.getGroupCompanyId())));
        doorMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(doorRequestDTO.getCompanyId())));
        doorMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(doorRequestDTO.getBranchId())));
        doorMaster.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(doorRequestDTO.getWareHouse().getId())));
        doorMaster.setCode(doorRequestDTO.getCode().toUpperCase());
        doorMaster.setName(doorRequestDTO.getName().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert LocTypeMaster entity to LocTypeResponseDTO
     * @Return LocTypeResponseDTO
     */
    public DoorResponseDTO convertEntityToDTO(DoorMaster doorMaster){
        log.info("ENTRY - DoorMaster to DoorResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        DoorResponseDTO doorResponseDTO = modelMapper.map(doorMaster, DoorResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(doorMaster.getBranchMaster()!=null) {
            modelMapper.map(doorMaster.getBranchMaster(), branchDTO);
            doorResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(doorMaster.getCompanyMaster()!=null) {
            modelMapper.map(doorMaster.getCompanyMaster(), companyDTO);
            doorResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(doorMaster.getGroupCompanyMaster()!=null) {
            modelMapper.map(doorMaster.getGroupCompanyMaster(), groupCompanyDTO);
            doorResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        DoorResponseDTO.WareHouseDTO wareHouseDTO=new DoorResponseDTO.WareHouseDTO();
        if(doorMaster.getWareHouseMaster()!=null){
            modelMapper.map(doorMaster.getWareHouseMaster(),wareHouseDTO);
            doorResponseDTO.setWareHouse(wareHouseDTO);
        }
        doorResponseDTO.setCreatedUser(doorMaster.getCreatedBy());
        doorResponseDTO.setUpdatedDate(doorMaster.getLastModifiedDate());
        doorResponseDTO.setUpdatedUser(doorMaster.getLastModifiedBy());
        log.info("EXIT");
        return doorResponseDTO;
    }

}
