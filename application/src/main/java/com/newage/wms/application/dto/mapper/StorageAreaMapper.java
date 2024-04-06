package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.StorageAreaRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.StorageAreaResponseDTO;
import com.newage.wms.entity.StorageAreaMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
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
public class StorageAreaMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    /*
     * Method to convert StorageArea Page to StorageArea Response Page
     * @Return Page<StorageAreaResponseDTO>
     */
    public Page<StorageAreaResponseDTO> convertStoragePageToStorageResponsePage(Page<StorageAreaMaster> storageAreaMasterPage){
        log.info("ENTRY - StorageAreaMaster Page to StorageAreaResponse Page mapper");
        List<StorageAreaResponseDTO> storageAreaResponseDTOList = storageAreaMasterPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(storageAreaResponseDTOList,storageAreaMasterPage.getPageable(),storageAreaMasterPage.getTotalElements());
    }

    /*
     * Method to convert StorageMasterIterable to StorageAreaMasterDTOIterable
     * @Return Iterable<StorageAreaResponseDTO>
     */
    public List<StorageAreaResponseDTO> convertStorageMasterIterableToStorageMasterDTOIterable(Iterable<StorageAreaMaster> storageAreaMasterIterable){
        log.info("ENTRY - StorageMasterIterable to StorageMasterDTOIterable mapper");
        return StreamSupport.stream(storageAreaMasterIterable.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert StorageAreaRequestDto to StorageArea entity
     * @Return StorageAreaMaster entity
     */
    public StorageAreaMaster convertRequestDtoToEntity(StorageAreaRequestDTO storageAreaRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - StorageAreaRequestDto to StorageArea mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);           // Ensure strict matching
        StorageAreaMaster storageAreaMaster = modelMapper.map(storageAreaRequestDTO,StorageAreaMaster.class);
        modelMapper.map(dateAndTimeRequestDto,storageAreaMaster);                                            // map date, time and version
        storageAreaMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(storageAreaRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        storageAreaMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(storageAreaRequestDTO.getCompanyId())));
        storageAreaMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(storageAreaRequestDTO.getBranchId())));
        storageAreaMaster.setCode(storageAreaRequestDTO.getCode().toUpperCase());
        storageAreaMaster.setName(storageAreaRequestDTO.getName().toUpperCase());
        log.info("EXIT");
        return storageAreaMaster;
    }

    /*
     * Method to convert update StorageAreaRequestDto to StorageArea entity
     */
    public void convertUpdateRequestToEntity(StorageAreaRequestDTO storageAreaRequestDTO, StorageAreaMaster storageAreaMaster, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - update storageAreaRequestDTO to StorageArea mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(storageAreaRequestDTO, storageAreaMaster);
        modelMapper.map(dateAndTimeRequestDto, storageAreaMaster);
        storageAreaMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(storageAreaRequestDTO.getGroupCompanyId())));
        storageAreaMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(storageAreaRequestDTO.getCompanyId())));
        storageAreaMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(storageAreaRequestDTO.getBranchId())));
        storageAreaMaster.setCode(storageAreaRequestDTO.getCode().toUpperCase());
        storageAreaMaster.setName(storageAreaRequestDTO.getName().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert StorageTypeMaster entity to StorageAreaResponseDTO
     * @Return StorageAreaResponseDTO
     */
    public StorageAreaResponseDTO convertEntityToDTO(StorageAreaMaster storageAreaMaster){
        log.info("ENTRY - StorageAreaMaster to StorageTypeDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        StorageAreaResponseDTO storageAreaResponseDTO = modelMapper.map(storageAreaMaster, StorageAreaResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(storageAreaMaster.getBranchMaster()!=null) {
            modelMapper.map(storageAreaMaster.getBranchMaster(), branchDTO);
            storageAreaResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(storageAreaMaster.getCompanyMaster()!=null) {
            modelMapper.map(storageAreaMaster.getCompanyMaster(), companyDTO);
            storageAreaResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(storageAreaMaster.getGroupCompanyMaster()!=null) {
            modelMapper.map(storageAreaMaster.getGroupCompanyMaster(), groupCompanyDTO);
            storageAreaResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        storageAreaResponseDTO.setCreatedUser(storageAreaMaster.getCreatedBy());
        storageAreaResponseDTO.setUpdatedDate(storageAreaMaster.getLastModifiedDate());
        storageAreaResponseDTO.setUpdatedUser(storageAreaMaster.getLastModifiedBy());
        log.info("EXIT");
        return storageAreaResponseDTO;
    }

}
