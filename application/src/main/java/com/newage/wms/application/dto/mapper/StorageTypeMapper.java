package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.StorageTypeRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.StorageTypeResponseDTO;
import com.newage.wms.entity.StorageTypeMaster;
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
public class StorageTypeMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    /*
     * Method to convert StorageType Page to StorageType Response Page
     * @Return Page<StorageTypeResponseDTO>
     */
    public Page<StorageTypeResponseDTO> convertStoragePageToStorageResponsePage(Page<StorageTypeMaster> storageTypeMasterPage){
        log.info("ENTRY - StorageTypeMaster Page to StorageTypeResponse Page mapper");
        List<StorageTypeResponseDTO> storageTypeResponseDTOList = storageTypeMasterPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(storageTypeResponseDTOList,storageTypeMasterPage.getPageable(),storageTypeMasterPage.getTotalElements());
    }

    /*
     * Method to convert StorageMasterIterable to StorageMasterDTOIterable
     * @Return Iterable<CityMasterDto>
     */
    public Iterable<StorageTypeResponseDTO> convertStorageMasterIterableToStorageMasterDTOIterable(Iterable<StorageTypeMaster> storageTypeMasterIterable){
        log.info("ENTRY - EXIT - StorageMasterIterable to StorageMasterDTOIterable mapper");
        return StreamSupport.stream(storageTypeMasterIterable.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert StorageTypeRequestDto to StorageType entity
     * @Return Location entity
     */
    public StorageTypeMaster convertRequestDtoToEntity(StorageTypeRequestDTO storageTypeRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - StorageTypeRequestDto to StorageType mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);           // Ensure strict matching
        StorageTypeMaster storageTypeMaster = modelMapper.map(storageTypeRequestDTO,StorageTypeMaster.class);
        modelMapper.map(dateAndTimeRequestDto,storageTypeMaster);                                            // map date, time and version
        storageTypeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(storageTypeRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        storageTypeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(storageTypeRequestDTO.getCompanyId())));
        storageTypeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(storageTypeRequestDTO.getBranchId())));
        storageTypeMaster.setCode(storageTypeRequestDTO.getCode().toUpperCase());
        storageTypeMaster.setName(storageTypeRequestDTO.getName().toUpperCase());
        log.info("EXIT");
        return storageTypeMaster;
    }



    /*
     * Method to convert update StorageTypeRequestDto to StorageType entity
     */
    public void convertUpdateRequestToEntity(StorageTypeRequestDTO storageTypeRequestDTO, StorageTypeMaster storageTypeMaster, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - update storageTypeRequestDTO to StorageType mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(storageTypeRequestDTO, storageTypeMaster);
        modelMapper.map(dateAndTimeRequestDto, storageTypeMaster);
        storageTypeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(storageTypeRequestDTO.getGroupCompanyId())));
        storageTypeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(storageTypeRequestDTO.getCompanyId())));
        storageTypeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(storageTypeRequestDTO.getBranchId())));
        storageTypeMaster.setCode(storageTypeRequestDTO.getCode().toUpperCase());
        storageTypeMaster.setName(storageTypeRequestDTO.getName().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert StorageTypeMaster entity to StorageTypeDTO
     * @Return StorageTypeDTO
     */
    public StorageTypeResponseDTO convertEntityToDTO(StorageTypeMaster storageTypeMaster){
        log.info("ENTRY - StorageTypeMaster to StorageTypeDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        StorageTypeResponseDTO storageTypeResponseDTO = modelMapper.map(storageTypeMaster, StorageTypeResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(storageTypeMaster.getBranchMaster()!=null) {
            modelMapper.map(storageTypeMaster.getBranchMaster(), branchDTO);
            storageTypeResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(storageTypeMaster.getCompanyMaster()!=null) {
            modelMapper.map(storageTypeMaster.getCompanyMaster(), companyDTO);
            storageTypeResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(storageTypeMaster.getGroupCompanyMaster()!=null) {
            modelMapper.map(storageTypeMaster.getGroupCompanyMaster(), groupCompanyDTO);
            storageTypeResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        storageTypeResponseDTO.setCreatedUser(storageTypeMaster.getCreatedBy());
        storageTypeResponseDTO.setUpdatedDate(storageTypeMaster.getLastModifiedDate());
        storageTypeResponseDTO.setUpdatedUser(storageTypeMaster.getLastModifiedBy());
        log.info("EXIT");
        return storageTypeResponseDTO;
    }

}
