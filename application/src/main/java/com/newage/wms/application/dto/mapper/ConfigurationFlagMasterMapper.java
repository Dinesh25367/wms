package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.ConfigurationFlagMasterRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.ConfigurationFlagMasterResponseDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.entity.ConfigurationFlagMaster;
import com.newage.wms.service.AuthUserProfileService;
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
public class ConfigurationFlagMasterMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private AuthUserProfileService authUserProfileService;



    /*
     * Method to convert ConfigurationFlag Page to ConfigurationFlag Response Page
     * @Return Page<ConfigurationFlagMasterResponseDTO>
     */
    public Page<ConfigurationFlagMasterResponseDTO> convertEntityPageToResponsePage(Page<ConfigurationFlagMaster> configurationFlagMasterPage){
        log.info("ENTRY EXIT - ConfigurationMaster to ConfigurationResponseDto.ConfigurationDTO mapper");
        List<ConfigurationFlagMasterResponseDTO> configurationFlagMasterResponseDTOS = configurationFlagMasterPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(configurationFlagMasterResponseDTOS,configurationFlagMasterPage.getPageable(),configurationFlagMasterPage.getTotalElements());
    }

    public ConfigurationFlagMasterResponseDTO convertEntityToResponse(ConfigurationFlagMaster configurationFlagMaster){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        ConfigurationFlagMasterResponseDTO configurationFlagMasterResponseDTO = modelMapper.map(configurationFlagMaster, ConfigurationFlagMasterResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(configurationFlagMaster.getBranchMaster()!=null) {
            modelMapper.map(configurationFlagMaster.getBranchMaster(), branchDTO);
            configurationFlagMasterResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(configurationFlagMaster.getCompanyMaster()!=null) {
            modelMapper.map(configurationFlagMaster.getCompanyMaster(), companyDTO);
            configurationFlagMasterResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(configurationFlagMaster.getGroupCompanyMaster()!=null) {
            modelMapper.map(configurationFlagMaster.getGroupCompanyMaster(), groupCompanyDTO);
            configurationFlagMasterResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        configurationFlagMasterResponseDTO.setCreatedUser(configurationFlagMaster.getCreatedBy());
        configurationFlagMasterResponseDTO.setUpdatedDate(configurationFlagMaster.getLastModifiedDate());
        configurationFlagMasterResponseDTO.setUpdatedUser(configurationFlagMaster.getLastModifiedBy());
        return configurationFlagMasterResponseDTO;
    }

    public ConfigurationFlagMaster convertRequestToEntity(ConfigurationFlagMasterRequestDTO configurationFlagMasterRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto1, Long userId) {
        log.info("ENTRY - ConfigurationRequest to ConfigurationDTO mapper");

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        ConfigurationFlagMaster configurationFlagMaster = modelMapper.map(configurationFlagMasterRequestDTO, ConfigurationFlagMaster.class);
        modelMapper.map(dateAndTimeRequestDto1,configurationFlagMaster);
        configurationFlagMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(configurationFlagMasterRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        configurationFlagMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(configurationFlagMasterRequestDTO.getCompanyId())));
        configurationFlagMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(configurationFlagMasterRequestDTO.getBranchId())));
        configurationFlagMaster.setAuthUserProfile(authUserProfileService.getById(userId));
        log.info("EXIT");
        return configurationFlagMaster;
    }

    public void convertUpdateRequestToEntity(ConfigurationFlagMasterRequestDTO configurationFlagMasterRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto1, ConfigurationFlagMaster configurationFlagMaster) {
        log.info("ENTRY - update ConfigurationRequestDto to Configuration mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(configurationFlagMasterRequestDTO,configurationFlagMaster);
        modelMapper.map(dateAndTimeRequestDto1,configurationFlagMaster);
        configurationFlagMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(configurationFlagMasterRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        configurationFlagMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(configurationFlagMasterRequestDTO.getCompanyId())));
        configurationFlagMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(configurationFlagMasterRequestDTO.getBranchId())));
        log.info("EXIT");

    }

    public Iterable<ConfigurationFlagMasterResponseDTO> convertConfigFlagMasterIterableToConfigFlagMasterDTOIterable(Iterable<ConfigurationFlagMaster> configurationFlagMasterIterable) {
        log.info("ENTRY - StorageMasterIterable to StorageMasterDTOIterable mapper");
        return StreamSupport.stream(configurationFlagMasterIterable.spliterator(), false)
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

}
