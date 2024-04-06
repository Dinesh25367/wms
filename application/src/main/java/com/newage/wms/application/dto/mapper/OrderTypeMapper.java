package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.OrderTypeRequestDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.OrderTypeResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnSoResponseDTO;
import com.newage.wms.entity.OrderTypeMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import com.newage.wms.service.OrderTypeMasterService;
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
public class OrderTypeMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderTypeMasterService orderTypeMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;


    /*
     * Method to convert OrderTypeRequestDTO to OrderTypeMaster Entity
     * @Return OrderTypeMaster Entity
     */
    public OrderTypeMaster convertRequestToEntity(OrderTypeRequestDTO orderTypeRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - OrderTypeRequestDTO to OrderTypeMaster Mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                           //Ensure Strict Matching
        OrderTypeMaster orderTypeMaster = modelMapper.map(orderTypeRequestDTO,OrderTypeMaster.class);

        modelMapper.map(dateAndTimeRequestDto,orderTypeMaster);
        orderTypeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(orderTypeRequestDTO.getGroupCompanyId())));
        orderTypeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(orderTypeRequestDTO.getCompanyId())));
        orderTypeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(orderTypeRequestDTO.getBranchId())));
        orderTypeMaster.setCode(orderTypeRequestDTO.getCode().toUpperCase());
        log.info("EXIT");
        return orderTypeMaster;
    }

    /*
     * Method to convert MovementTypeMaster entity to MovementTypeResponseDTO
     * @Return MovementTypeResponseDTO
     */
    public OrderTypeResponseDTO convertEntityToResponse(OrderTypeMaster orderTypeMaster){
        log.info("ENTRY - MovementTypeMaster to MovementTypeResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                   //Ensure Strict Matching
        OrderTypeResponseDTO orderTypeResponseDTO = modelMapper.map(orderTypeMaster,OrderTypeResponseDTO.class);
        orderTypeResponseDTO.setGroupCompany(mapToDTOIfNotNull(orderTypeMaster.getGroupCompanyMaster(), DefaultFieldsResponseDTO.GroupCompanyDTO.class));
        orderTypeResponseDTO.setCompany(mapToDTOIfNotNull(orderTypeMaster.getCompanyMaster(),DefaultFieldsResponseDTO.CompanyDTO.class));
        orderTypeResponseDTO.setBranch(mapToDTOIfNotNull(orderTypeMaster.getBranchMaster(),DefaultFieldsResponseDTO.BranchDTO.class));
        orderTypeResponseDTO.setCreatedUser(orderTypeMaster.getCreatedBy());
        orderTypeResponseDTO.setCreatedDate(orderTypeMaster.getCreatedDate());
        orderTypeResponseDTO.setUpdatedUser(orderTypeMaster.getLastModifiedBy());
        orderTypeResponseDTO.setUpdatedDate(orderTypeMaster.getLastModifiedDate());
        log.info("EXIT");
        return orderTypeResponseDTO;
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

    /*
     * Method to convert OrderTypeMaster Iterable to OrderType AutoCompleteDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnHeaderDTO.OrderTypeDTO>
     */
    public Iterable<TrnSoResponseDTO.TrnHeaderSoDTO.OrderTypeDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<OrderTypeMaster> orderTypeMasterIterable){
        log.info("ENTRY-EXIT - OrderTypeMaster Iterable to OrderType AutoCompleteDTO Iterable mapper");
        return StreamSupport.stream(orderTypeMasterIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert OrderTypeMaster to OrderType AutoCompleteDTO
     * @Return Iterable<TrnResponseDTO.TrnHeaderDTO.OrderType>
     */
    private TrnSoResponseDTO.TrnHeaderSoDTO.OrderTypeDTO convertEntityToAutoCompleteDTO(OrderTypeMaster orderTypeMaster) {
        log.info("ENTRY-EXIT - OrderTypeMaster Iterable to OrderType AutoCompleteDTO Iterable mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(orderTypeMaster, TrnSoResponseDTO.TrnHeaderSoDTO.OrderTypeDTO.class);
    }

    /*
     * Method to convert OrderTypeMaster Page to OrderTypeMaster Response Page
     * @Return OrderTypeMaster response Page
     */
    public Page<OrderTypeResponseDTO> convertMovementTypePageToResponsePage(Page<OrderTypeMaster> orderTypeMasterPage){
        log.info("ENTRY - OrderTypeMaster Page to OrderTypeMasterResponse Mapper");
        List<OrderTypeResponseDTO> orderTypeResponseDTOList = orderTypeMasterPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(orderTypeResponseDTOList,orderTypeMasterPage.getPageable(),orderTypeMasterPage.getTotalElements());
    }

    /*
     * Method to convert update OrderTypeRequestDTO to OrderType Master entity
     */
    public void convertUpdateRequestToEntity(OrderTypeRequestDTO orderTypeRequestDTO,OrderTypeMaster orderTypeMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - Update OrderTypeRequestDTO to OrderTypeMaster");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                       //Ensure Strict Matching
        modelMapper.map(orderTypeRequestDTO,orderTypeMaster);
        modelMapper.map(dateAndTimeRequestDto,orderTypeMaster);
        orderTypeMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(orderTypeRequestDTO.getGroupCompanyId())));
        orderTypeMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(orderTypeRequestDTO.getCompanyId())));
        orderTypeMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(orderTypeRequestDTO.getBranchId())));
        orderTypeMaster.setCode(orderTypeRequestDTO.getCode().toUpperCase());
        log.info("EXIT");
    }

}
