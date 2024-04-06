package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.*;
import com.newage.wms.entity.*;
import com.newage.wms.service.CustomerMasterService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Log4j2
public final class CustomerMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerMasterService customerMasterService;

    /*
     * Method to convert CustomerMasterIterable to SkuRequestDTO.CustomerDTO Iterable
     * @Return Iterable<SkuRequestDTO.CustomerDTO>
     */
    public Iterable<SkuRequestDTO.CustomerDTO> convertCustomerMasterIterableToCustomerAutoCompleteDtoIterable(Iterable<CustomerMaster> customerMasterIterable){
        log.info("ENTRY - CustomerMasterIterable to SkuRequestDTO.CustomerDTO mapper");
        return StreamSupport.stream(customerMasterIterable.spliterator(), false)
                .map(this::convertEntitytoAutoCompleteDto)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert Customer entity to CustomerDTO
     * @Return SkuRequestDTO.CustomerDTO
     */
    public SkuRequestDTO.CustomerDTO convertEntitytoAutoCompleteDto(CustomerMaster customerMaster){
        log.info("ENTRY - Customer entity to SkuRequestDTO.CustomerDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        SkuRequestDTO.CustomerDTO customerAutoCompleteDto = modelMapper.map(customerMaster, SkuRequestDTO.CustomerDTO.class);
        log.info("EXIT");
        return customerAutoCompleteDto;
    }

}
