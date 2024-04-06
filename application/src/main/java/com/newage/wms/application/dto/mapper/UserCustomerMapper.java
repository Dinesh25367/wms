package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.UserCustomerRequestDto;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.UserCustomerResponseDTO;
import com.newage.wms.entity.UserCustomer;
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

@Log4j2
@Component
public class UserCustomerMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserCustomerService userCustomerService;

    @Autowired
    private CustomerMasterService customerMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private AuthUserProfileService authUserProfileService;

    /*
     * Method to convert UserCustomer Page to UserCustomer Response Page
     * @Return UserCustomer response Page
     */

    public Page<UserCustomerResponseDTO> convertUserCustomerPageToResponsePage(Page<UserCustomer> userCustomerPage){
        log.info("ENTRY - UserCustomer Page to UserCustomerResponse mapper");
        List<UserCustomerResponseDTO> userCustomerResponseDtoList = userCustomerPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(userCustomerResponseDtoList,userCustomerPage.getPageable(),userCustomerPage.getTotalElements());
    }

    /*
     * Method to convert UserCustomerRequestDTO to UserCustomer Entity
     * @Return UserCustomer Entity
     */
    public UserCustomer convertRequestToEntity(UserCustomerRequestDto userCustomerRequestDto, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - UserCustomerRequest to UserCustomer Mapper ");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserCustomer userCustomer = modelMapper.map(userCustomerRequestDto,UserCustomer.class);
        modelMapper.map(dateAndTimeRequestDto,userCustomer);
        userCustomer.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(userCustomerRequestDto.getGroupCompanyId())));        //Set unmapped objects
        userCustomer.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(userCustomerRequestDto.getCompanyId())));
        userCustomer.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(userCustomerRequestDto.getBranchId())));
        if (userCustomerRequestDto.getCustomer() != null){
            userCustomer.setCustomerMaster(customerMasterService.getCustomerById(Long.parseLong(userCustomerRequestDto.getCustomer().getId())));
        }
        if (userCustomerRequestDto.getUserName() != null){
            userCustomer.setUserMaster(authUserProfileService.getById(Long.parseLong((userCustomerRequestDto.getUserName().getId()))));
        }

        log.info("EXIT");
        return userCustomer;
    }
    /*
     * Method to convert Update UserCustomerRequestDto to UserCustomer Entity
     */

    public  void convertUpdateRequestToEntity(UserCustomerRequestDto userCustomerRequestDto, UserCustomer userCustomer, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - Update UserCustomerRequest to UserCustomer mapper ");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(userCustomerRequestDto,userCustomer);
        modelMapper.map(dateAndTimeRequestDto,userCustomer);
        if (userCustomerRequestDto.getCustomer() != null){
            userCustomer.setCustomerMaster(customerMasterService.getCustomerById(Long.parseLong(userCustomerRequestDto.getCustomer().getId())));
        }
        if (userCustomerRequestDto.getUserName() != null){
            userCustomer.setUserMaster(authUserProfileService.getById(Long.parseLong(userCustomerRequestDto.getUserName().getId())));
        }

        log.info("EXIT");
    }

    /*
     * Method to convert UserCustomer entity to UserCustomerResponseDTO
     * @Return UserCustomerResponseDTO
     */

    public UserCustomerResponseDTO convertEntityToResponse(UserCustomer userCustomer){
        log.info("ENTRY - UserCustomer Entity to UserCustomerResponse mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserCustomerResponseDTO userCustomerResponseDto = modelMapper.map(userCustomer, UserCustomerResponseDTO.class);
        userCustomerResponseDto.setCustomer(mapToDTOIfNotNull(userCustomer.getCustomerMaster(), UserCustomerResponseDTO.CustomerDTO.class));
        userCustomerResponseDto.setUsername(mapToDTOIfNotNull(userCustomer.getUserMaster(), UserCustomerResponseDTO.UserNameDTO.class));
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(userCustomer.getBranchMaster()!=null) {
            modelMapper.map(userCustomer.getBranchMaster(), branchDTO);
            userCustomerResponseDto.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(userCustomer.getCompanyMaster()!=null) {
            modelMapper.map(userCustomer.getCompanyMaster(), companyDTO);
            userCustomerResponseDto.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(userCustomer.getGroupCompanyMaster()!=null) {
            modelMapper.map(userCustomer.getGroupCompanyMaster(), groupCompanyDTO);
            userCustomerResponseDto.setGroupCompany(groupCompanyDTO);
        }
        userCustomerResponseDto.setCreatedUser(userCustomer.getCreatedBy());
        userCustomerResponseDto.setCreatedDate(userCustomer.getCreatedDate());
        userCustomerResponseDto.setUpdatedUser(userCustomer.getLastModifiedBy());
        userCustomerResponseDto.setUpdatedDate(userCustomer.getLastModifiedDate());
        log.info("EXIT");
        return userCustomerResponseDto;
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

}
