package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.UserWareHouseRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.UserWareHouseResponseDTO;
import com.newage.wms.entity.UserWareHouse;
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
public class UserWareHouseMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private AuthUserProfileService authUserProfileService;

    /*
     * Method to convert UserWareHouse Page to UserWareHouse Response Page
     * @Return UserWareHouse response Page
     */
    public Page<UserWareHouseResponseDTO> convertUserWareHousePageToResponsePage(Page<UserWareHouse> userWareHousePage){
        log.info("ENTRY - UserWareHouse Page to UserWareHouseResponse Mapper");
        List<UserWareHouseResponseDTO> houseResponseDTOList = userWareHousePage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(houseResponseDTOList,userWareHousePage.getPageable(),userWareHousePage.getTotalElements());
    }

    /*
     * Method to convert UserWareHouseRequestDTO to UserWareHouse Entity
     * @Return UserWareHouse Entity
     */
    public UserWareHouse convertRequestToEntity(UserWareHouseRequestDTO userWareHouseRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - UserWareHouseRequestDTO To UserWareHouse Mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                   //Ensure strict matching
        UserWareHouse userWareHouse = modelMapper.map(userWareHouseRequestDTO,UserWareHouse.class);
        modelMapper.map(dateAndTimeRequestDto,userWareHouse);
        userWareHouse.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(userWareHouseRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        userWareHouse.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(userWareHouseRequestDTO.getCompanyId())));
        userWareHouse.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(userWareHouseRequestDTO.getBranchId())));
        if (userWareHouseRequestDTO.getWareHouse() != null){
            userWareHouse.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(userWareHouseRequestDTO.getWareHouse().getId())));
        }
        if (userWareHouseRequestDTO.getUserName() != null){
            userWareHouse.setUserMaster(authUserProfileService.getById(Long.parseLong((userWareHouseRequestDTO.getUserName().getId()))));
        }
        log.info("EXIT");
        return userWareHouse;
    }

    /*
     * Method to convert Update UserWareHouseRequestDto to UserWareHouse Entity
     */
    public void convertUpdateRequestToEntity(UserWareHouseRequestDTO userWareHouseRequestDTO,UserWareHouse userWareHouse,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - update UserWareHouseRequestDTO to UserWareHouse mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(userWareHouseRequestDTO,userWareHouse);
        modelMapper.map(dateAndTimeRequestDto,userWareHouse);
        if (userWareHouseRequestDTO.getWareHouse() != null){
            userWareHouse.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(userWareHouseRequestDTO.getWareHouse().getId())));
        }
        if (userWareHouseRequestDTO.getUserName() != null){
            userWareHouse.setUserMaster(authUserProfileService.getById(Long.parseLong(userWareHouseRequestDTO.getUserName().getId())));
        }
        log.info("EXIT");
    }

    /*
     * Method to convert UserWareHouse entity to UserWareHouseResponseDTO
     * @Return UserWareHouseResponseDTO
     */
    public UserWareHouseResponseDTO convertEntityToResponse(UserWareHouse userWareHouse){
        log.info("ENTRY - UserWareHouse to UserWareHouseResponseDTO Mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);       //Ensure Strict Matching
        UserWareHouseResponseDTO userWareHouseResponseDTO = modelMapper.map(userWareHouse, UserWareHouseResponseDTO.class);
        userWareHouseResponseDTO.setWareHouse(mapToDTOIfNotNull(userWareHouse.getWareHouseMaster(),UserWareHouseResponseDTO.WareHouseDTO.class));
        userWareHouseResponseDTO.setUserName(mapToDTOIfNotNull(userWareHouse.getUserMaster(),UserWareHouseResponseDTO.UserNameDTO.class));
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(userWareHouse.getBranchMaster()!=null) {
            modelMapper.map(userWareHouse.getBranchMaster(), branchDTO);
            userWareHouseResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(userWareHouse.getCompanyMaster()!=null) {
            modelMapper.map(userWareHouse.getCompanyMaster(), companyDTO);
            userWareHouseResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(userWareHouse.getGroupCompanyMaster()!=null) {
            modelMapper.map(userWareHouse.getGroupCompanyMaster(), groupCompanyDTO);
            userWareHouseResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        userWareHouseResponseDTO.setCreatedUser(userWareHouse.getCreatedBy());
        userWareHouseResponseDTO.setCreatedDate(userWareHouse.getCreatedDate());
        userWareHouseResponseDTO.setUpdatedUser(userWareHouse.getLastModifiedBy());
        userWareHouseResponseDTO.setUpdatedDate(userWareHouse.getLastModifiedDate());
        log.info("EXIT");
        return userWareHouseResponseDTO;
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
