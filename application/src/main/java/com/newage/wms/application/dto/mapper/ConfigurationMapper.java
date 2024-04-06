package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.ConfigurationRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.ConfigurationMasterResponseDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.entity.ConfigurationMaster;
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
public class ConfigurationMapper {

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
     * Method to convert Configuration Page to Configuration Response Page
     * @Return Page<ConfigurationResponseDTO>
     */
    public Page<ConfigurationMasterResponseDTO> convertEntityPageToResponsePage(Page<ConfigurationMaster> configurationMasterPage){
        log.info("ENTRY EXIT - ConfigurationMaster to ConfigurationResponseDto.ConfigurationDTO mapper");
        List<ConfigurationMasterResponseDTO> configurationMasterResponseDTOList = configurationMasterPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(configurationMasterResponseDTOList,configurationMasterPage.getPageable(),configurationMasterPage.getTotalElements());
    }

    public Iterable<ConfigurationMasterResponseDTO> convertEntityIterableToResponseIterable(Iterable<ConfigurationMaster> configurationMasterIterable){
        return StreamSupport.stream(configurationMasterIterable.spliterator(), false)
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert RequestDTO to Entity
     * @Return ConfigurationMaster
     */
    public ConfigurationMaster convertRequestToEntity(ConfigurationRequestDTO configurationRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto,Long userId){
        log.info("ENTRY - ConfigurationRequest to ConfigurationDTO mapper");

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        ConfigurationMaster configurationMaster = modelMapper.map(configurationRequestDTO, ConfigurationMaster.class);
        modelMapper.map(dateAndTimeRequestDto,configurationMaster);
        configurationMaster.setDataType(configurationRequestDTO.getDataType().toLowerCase());
        configurationMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(configurationRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        configurationMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(configurationRequestDTO.getCompanyId())));
        configurationMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(configurationRequestDTO.getBranchId())));
        configurationMaster.setAuthUserProfile(authUserProfileService.getById(userId));
        log.info("EXIT");
        return configurationMaster;
    }

    /*
     * Method to convert update ConfigurationRequestDto to ConfigurationMaster entity
     */
    public void convertUpdateRequestToEntity(ConfigurationRequestDTO configurationRequestDTO,DateAndTimeRequestDto dateAndTimeRequestDto1,ConfigurationMaster configurationMaster){
        log.info("ENTRY - update ConfigurationRequestDto to Configuration mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(configurationRequestDTO,configurationMaster);
        modelMapper.map(dateAndTimeRequestDto1,configurationMaster);                                            // map date, time and version
        configurationMaster.setDataType(configurationRequestDTO.getDataType().toLowerCase());
        configurationMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(configurationRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        configurationMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(configurationRequestDTO.getCompanyId())));
        configurationMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(configurationRequestDTO.getBranchId())));
        log.info("EXIT");
    }


    public ConfigurationMasterResponseDTO convertEntityToResponse(ConfigurationMaster configurationMaster){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        ConfigurationMasterResponseDTO configurationMasterResponseDTO = modelMapper.map(configurationMaster, ConfigurationMasterResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(configurationMaster.getBranchMaster()!=null) {
            modelMapper.map(configurationMaster.getBranchMaster(), branchDTO);
            configurationMasterResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(configurationMaster.getCompanyMaster()!=null) {
            modelMapper.map(configurationMaster.getCompanyMaster(), companyDTO);
            configurationMasterResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(configurationMaster.getGroupCompanyMaster()!=null) {
            modelMapper.map(configurationMaster.getGroupCompanyMaster(), groupCompanyDTO);
            configurationMasterResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        configurationMasterResponseDTO.setDataType(configurationMaster.getDataType().toLowerCase());
        configurationMasterResponseDTO.setCreatedUser(configurationMaster.getCreatedBy());
        configurationMasterResponseDTO.setUpdatedDate(configurationMaster.getLastModifiedDate());
        configurationMasterResponseDTO.setUpdatedUser(configurationMaster.getLastModifiedBy());
        return configurationMasterResponseDTO;
    }

}

