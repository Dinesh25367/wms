package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.ImcoCodeRequestDTO;
import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.ImcoCodeResponseDTO;
import com.newage.wms.entity.ImcoCodeMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import com.newage.wms.service.ImcoCodeMasterService;
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
public class ImcoCodeMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImcoCodeMasterService imcoCodeMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    /*
     * Method to convert ImcoCodeMaster Page to ImcoCodeMaster Response Page
     * @Return ImcoCodeMaster response Page
     */
    public Page<ImcoCodeResponseDTO> convertImcoCodePageToResponsePage(Page<ImcoCodeMaster> imcoCodeMasterPage){
        log.info("ENTRY - HsCode Page to HsCodeResponse Mapper");
        List<ImcoCodeResponseDTO> imcoCodeResponseDTOList = imcoCodeMasterPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(imcoCodeResponseDTOList,imcoCodeMasterPage.getPageable(),imcoCodeMasterPage.getTotalElements());
    }

    /*
     * Method to convert ImcoCodeMasterIterable to SkuRequestDTO.ImcoCodeDTO Iterable
     * @Return Iterable<SkuRequestDTO.ImcoCodeDTO>
     */
    public Iterable<SkuRequestDTO.ImcoCodeDTO> convertEntityIterableToAutoCompleteDtoIterable(Iterable<ImcoCodeMaster> imcoCodeMasterIterable){
        log.info("ENTRY - HsCodeMasterIterable to SkuRequestDTO.HsCodeDTO mapper");
        return StreamSupport.stream(imcoCodeMasterIterable.spliterator(), false)
                .map(this::convertEntitytoAutoCompleteDto)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert ImcoCode entity to SkuRequestDTO.ImcoCodeDTO
     * @Return SkuRequestDTO.ImcoCodeDTO
     */
    public SkuRequestDTO.ImcoCodeDTO convertEntitytoAutoCompleteDto(ImcoCodeMaster imcoCodeMaster){
        log.info("ENTRY - Customer entity to SkuRequestDTO.HsCodeDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        SkuRequestDTO.ImcoCodeDTO imcoCodeDTO = modelMapper.map(imcoCodeMaster, SkuRequestDTO.ImcoCodeDTO.class);
        log.info("EXIT");
        return imcoCodeDTO;
    }

    /*
     * Method to convert ImcoCodeRequestDTO to ImcoCodeMaster Entity
     * @Return ImcoCodeMaster Entity
     */

    public ImcoCodeMaster convertRequestToEntity(ImcoCodeRequestDTO imcoCodeRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - HsCodeRequest to HsCodeMaster Mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ImcoCodeMaster imcoCodeMaster = modelMapper.map(imcoCodeRequestDTO,ImcoCodeMaster.class);
        modelMapper.map(dateAndTimeRequestDto,imcoCodeMaster);
        imcoCodeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(imcoCodeRequestDTO.getGroupCompanyId())));        //Set unmapped objects
        imcoCodeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(imcoCodeRequestDTO.getCompanyId())));
        imcoCodeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(imcoCodeRequestDTO.getBranchId())));
        imcoCodeMaster.setCode(imcoCodeRequestDTO.getCode().toUpperCase());
        imcoCodeMaster.setName(imcoCodeRequestDTO.getName().toUpperCase());
        log.info("EXIT");
        return imcoCodeMaster;
    }

    /*
     * Method to convert ImcoCodeMaster entity to ImcoCodeResponseDTO
     * @Return ImcoCodeResponseDTO
     */
    public ImcoCodeResponseDTO convertEntityToResponse(ImcoCodeMaster imcoCodeMaster){
        log.info("ENTRY - HsCode Master to HsCodeResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ImcoCodeResponseDTO imcoCodeResponseDTO = modelMapper.map(imcoCodeMaster,ImcoCodeResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO = new DefaultFieldsResponseDTO.BranchDTO();
        if(imcoCodeMaster.getBranchMaster()!=null) {
            modelMapper.map(imcoCodeMaster.getBranchMaster(), branchDTO);
            imcoCodeResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO = new DefaultFieldsResponseDTO.CompanyDTO();
        if(imcoCodeMaster.getCompanyMaster()!=null) {
            modelMapper.map(imcoCodeMaster.getCompanyMaster(), companyDTO);
            imcoCodeResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if(imcoCodeMaster.getGroupCompanyMaster()!=null) {
            modelMapper.map(imcoCodeMaster.getGroupCompanyMaster(), groupCompanyDTO);
            imcoCodeResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        imcoCodeResponseDTO.setCreatedUser(imcoCodeMaster.getCreatedBy());
        imcoCodeResponseDTO.setCreatedDate(imcoCodeMaster.getCreatedDate());
        imcoCodeResponseDTO.setUpdatedUser(imcoCodeMaster.getLastModifiedBy());
        imcoCodeResponseDTO.setUpdatedDate(imcoCodeMaster.getLastModifiedDate());
        log.info("EXIT");
        return imcoCodeResponseDTO;
    }

    /*
     * Method to convert update ImcoCodeRequestDTO to ImcoCodeMaster entity
     */
    public void convertUpdateRequestToEntity(ImcoCodeRequestDTO imcoCodeRequestDTO,ImcoCodeMaster imcoCodeMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - Update ImcoCodeRequest to ImcoCodeMaster");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(imcoCodeRequestDTO,imcoCodeMaster);
        modelMapper.map(dateAndTimeRequestDto,imcoCodeMaster);
        imcoCodeMaster.setCode(imcoCodeRequestDTO.getCode().toUpperCase());
        imcoCodeMaster.setName(imcoCodeRequestDTO.getName().toUpperCase());
    }

}