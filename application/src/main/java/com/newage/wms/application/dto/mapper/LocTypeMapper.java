package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.*;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.LocTypeResponseDTO;
import com.newage.wms.entity.LocTypeMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import com.newage.wms.service.LocTypeMasterService;
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
public class LocTypeMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LocTypeMasterService locTypeMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;


    /*
     * Method to convert LocTypeIterable to LocTypeResponseDTO Iterable
     * @Return Iterable<LocTypeResponseDTO>
     */
    public Iterable<LocTypeResponseDTO> convertLocTypeMasterIterableToLocTypeMasterDTOIterable(Iterable<LocTypeMaster> locTypeMasterIterable){
        log.info("ENTRY - LocTypeMasterIterable to LocTypeMasterDTOIterable mapper");
        return StreamSupport.stream(locTypeMasterIterable.spliterator(), false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert LocType Page to LocType Response Page
     * @Return Page<LocTypeResponseDTO>
     */
    public Page<LocTypeResponseDTO> convertLocTypePageToLocTypeResponsePage(Page<LocTypeMaster> locTypeMasterPage){
        log.info("ENTRY - LocTypeMaster Page to LocTypeResponse Page mapper");
        List<LocTypeResponseDTO> locTypeResponseDTOList = locTypeMasterPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(locTypeResponseDTOList,locTypeMasterPage.getPageable(),locTypeMasterPage.getTotalElements());
    }

    /*
     * Method to convert StorageTypeRequestDto to StorageType entity
     * @Return Location entity
     */
    public LocTypeMaster convertRequestDtoToEntity(LocTypeRequestDTO locTypeRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - LocTypeRequestDTO to LocationType mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);           // Ensure strict matching
        LocTypeMaster locTypeMaster = modelMapper.map(locTypeRequestDTO,LocTypeMaster.class);
        modelMapper.map(dateAndTimeRequestDto,locTypeMaster);                                            // map date, time and version
        locTypeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(locTypeRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        locTypeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(locTypeRequestDTO.getCompanyId())));
        locTypeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(locTypeRequestDTO.getBranchId())));
        locTypeMaster.setCode(locTypeRequestDTO.getCode().toUpperCase());
        locTypeMaster.setName(locTypeRequestDTO.getName().toUpperCase());
        locTypeMaster.setType(locTypeRequestDTO.getType().toUpperCase());
        log.info("EXIT");
        return locTypeMaster;
    }

    /*
     * Method to convert update StorageTypeRequestDto to StorageType entity
     */
    public void convertUpdateRequestToEntity(LocTypeRequestDTO locTypeRequestDTO, LocTypeMaster locTypeMaster, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - update LocTypeRequestDTO to LocationType mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(locTypeRequestDTO, locTypeMaster);
        modelMapper.map(dateAndTimeRequestDto, locTypeMaster);
        locTypeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(locTypeRequestDTO.getGroupCompanyId())));
        locTypeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(locTypeRequestDTO.getCompanyId())));
        locTypeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(locTypeRequestDTO.getBranchId())));
        locTypeMaster.setCode(locTypeRequestDTO.getCode().toUpperCase());
        locTypeMaster.setName(locTypeRequestDTO.getName().toUpperCase());
        locTypeMaster.setType(locTypeRequestDTO.getType().toUpperCase());
        log.info("EXIT");
    }

    /*
     * Method to convert LocTypeMaster entity to LocTypeResponseDTO
     * @Return LocTypeResponseDTO
     */
    public LocTypeResponseDTO convertEntityToDTO(LocTypeMaster locTypeMaster){
        log.info("ENTRY - LocTypeMaster to LocTypeResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        LocTypeResponseDTO locTypeResponseDTO = modelMapper.map(locTypeMaster, LocTypeResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(locTypeMaster.getBranchMaster()!=null) {
            modelMapper.map(locTypeMaster.getBranchMaster(), branchDTO);
            locTypeResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(locTypeMaster.getCompanyMaster()!=null) {
            modelMapper.map(locTypeMaster.getCompanyMaster(), companyDTO);
            locTypeResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(locTypeMaster.getGroupCompanyMaster()!=null) {
            modelMapper.map(locTypeMaster.getGroupCompanyMaster(), groupCompanyDTO);
            locTypeResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        locTypeResponseDTO.setCreatedUser(locTypeMaster.getCreatedBy());
        locTypeResponseDTO.setUpdatedDate(locTypeMaster.getLastModifiedDate());
        locTypeResponseDTO.setUpdatedUser(locTypeMaster.getLastModifiedBy());
        log.info("EXIT");
        return locTypeResponseDTO;
    }

}
