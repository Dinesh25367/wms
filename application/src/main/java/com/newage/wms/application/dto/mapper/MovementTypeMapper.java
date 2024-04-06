package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.MovementTypeRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.MovementTypeResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.MovementTypeMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import com.newage.wms.service.MovementTypeMasterService;
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
public class MovementTypeMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MovementTypeMasterService movementTypeMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    /*
     * Method to convert MovementTypeMaster Page to MovementTypeMaster Response Page
     * @Return MovementTypeMaster response Page
     */
    public Page<MovementTypeResponseDTO> convertMovementTypePageToResponsePage(Page<MovementTypeMaster> movementTypeMasterPage){
        log.info("ENTRY - HsCode Page to HsCodeResponse Mapper");
        List<MovementTypeResponseDTO> movementTypeResponseDTOList = movementTypeMasterPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(movementTypeResponseDTOList,movementTypeMasterPage.getPageable(),movementTypeMasterPage.getTotalElements());
    }

    /*
     * Method to convert MovementTypeMaster Iterable to MovementType AutoCompleteDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnHeaderDTO.MovementTypeDTO>
     */
    public Iterable<TrnResponseDTO.TrnHeaderAsnDTO.MovementTypeDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<MovementTypeMaster> movementTypeMasterIterable){
        log.info("ENTRY-EXIT - MovementTypeMaster Iterable to MovementType AutoCompleteDTO Iterable mapper");
        return StreamSupport.stream(movementTypeMasterIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert MovementTypeMaster to MovementType AutoCompleteDTO
     * @Return Iterable<TrnResponseDTO.TrnHeaderDTO.MovementTypeDTO>
     */
    private TrnResponseDTO.TrnHeaderAsnDTO.MovementTypeDTO convertEntityToAutoCompleteDTO(MovementTypeMaster movementTypeMaster) {
        log.info("ENTRY-EXIT - MovementTypeMaster Iterable to MovementType AutoCompleteDTO Iterable mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(movementTypeMaster, TrnResponseDTO.TrnHeaderAsnDTO.MovementTypeDTO.class);
    }

    /*
     * Method to convert MovementTypeRequestDTO to MovementTypeMaster Entity
     * @Return MovementTypeMaster Entity
     */
    public MovementTypeMaster convertRequestToEntity(MovementTypeRequestDTO movementTypeRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - HsCodeRequest to HsCodeMaster Mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                           //Ensure Strict Matching
        MovementTypeMaster movementTypeMaster = modelMapper.map(movementTypeRequestDTO,MovementTypeMaster.class);

        modelMapper.map(dateAndTimeRequestDto,movementTypeMaster);
        movementTypeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(movementTypeRequestDTO.getGroupCompanyId())));
        movementTypeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(movementTypeRequestDTO.getCompanyId())));
        movementTypeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(movementTypeRequestDTO.getBranchId())));
        movementTypeMaster.setCode(movementTypeRequestDTO.getCode().toUpperCase());
        log.info("EXIT");
        return movementTypeMaster;
    }

    /*
     * Method to convert MovementTypeMaster entity to MovementTypeResponseDTO
     * @Return MovementTypeResponseDTO
     */
    public MovementTypeResponseDTO convertEntityToResponse(MovementTypeMaster movementTypeMaster){
        log.info("ENTRY - MovementTypeMaster to MovementTypeResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                   //Ensure Strict Matching
        MovementTypeResponseDTO movementTypeResponseDTO = modelMapper.map(movementTypeMaster,MovementTypeResponseDTO.class);
        movementTypeResponseDTO.setGroupCompany(mapToDTOIfNotNull(movementTypeMaster.getGroupCompanyMaster(), DefaultFieldsResponseDTO.GroupCompanyDTO.class));
        movementTypeResponseDTO.setCompany(mapToDTOIfNotNull(movementTypeMaster.getCompanyMaster(),DefaultFieldsResponseDTO.CompanyDTO.class));
        movementTypeResponseDTO.setBranch(mapToDTOIfNotNull(movementTypeMaster.getBranchMaster(),DefaultFieldsResponseDTO.BranchDTO.class));
        movementTypeResponseDTO.setCreatedUser(movementTypeMaster.getCreatedBy());
        movementTypeResponseDTO.setCreatedDate(movementTypeMaster.getCreatedDate());
        movementTypeResponseDTO.setUpdatedUser(movementTypeMaster.getLastModifiedBy());
        movementTypeResponseDTO.setUpdatedDate(movementTypeMaster.getLastModifiedDate());
        log.info("EXIT");
        return movementTypeResponseDTO;
    }
    /*
     * Method to convert update MovementTypeRequestDTO to MovementTypeMaster entity
     */
    public void convertUpdateRequestToEntity(MovementTypeRequestDTO movementTypeRequestDTO,MovementTypeMaster movementTypeMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - Update ImcoCodeRequest to ImcoCodeMaster");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                       //Ensure Strict Matching
        modelMapper.map(movementTypeRequestDTO,movementTypeMaster);
        modelMapper.map(dateAndTimeRequestDto,movementTypeMaster);
        movementTypeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(movementTypeRequestDTO.getGroupCompanyId())));
        movementTypeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(movementTypeRequestDTO.getCompanyId())));
        movementTypeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(movementTypeRequestDTO.getBranchId())));
        movementTypeMaster.setCode(movementTypeRequestDTO.getCode().toUpperCase());
        log.info("EXIT");
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
