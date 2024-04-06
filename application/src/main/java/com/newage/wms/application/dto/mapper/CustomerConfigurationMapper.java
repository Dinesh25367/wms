package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.CustomerConfigurationRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.CustomerConfigurationMasterResponseDTO;
import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.entity.CustomerConfigurationMaster;
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
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class CustomerConfigurationMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerConfigurationMasterService customerConfigurationMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private CustomerMasterService customerMasterService;


    public Iterable<CustomerConfigurationMasterResponseDTO> convertEntityIterableToResponseIterable(Iterable<CustomerConfigurationMaster> customerConfigurationMasterIterable){
        return StreamSupport.stream(customerConfigurationMasterIterable.spliterator(), false)
                .map(this::convertEntityToAutoCompleteResponse)
                .collect(Collectors.toList());
    }

    public CustomerConfigurationMasterResponseDTO convertEntityToAutoCompleteResponse(CustomerConfigurationMaster customerConfigurationMaster){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        CustomerConfigurationMasterResponseDTO customerConfigurationMasterResponseDTO = modelMapper.map(customerConfigurationMaster, CustomerConfigurationMasterResponseDTO.class);
        customerConfigurationMasterResponseDTO.setConfigurationId(customerConfigurationMaster.getId());
        customerConfigurationMasterResponseDTO.setDataType(customerConfigurationMaster.getDataType().toLowerCase());
        return customerConfigurationMasterResponseDTO;
    }
    
    public Page<CustomerConfigurationMasterResponseDTO> convertCustomerConfigurationPageToResponsePage(Page<CustomerConfigurationMaster> customerConfigurationMasterPage){
        log.info("ENTRY - CustomerConfigurationMaster Page To CustomerConfigurationMasterResponse Mapper");
        List<CustomerConfigurationMasterResponseDTO>customerConfigurationMasterResponseDTOList=customerConfigurationMasterPage.getContent()
                .stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(customerConfigurationMasterResponseDTOList,customerConfigurationMasterPage.getPageable(),customerConfigurationMasterPage.getTotalElements());
    }

    public CustomerConfigurationMasterResponseDTO convertEntityToResponse(CustomerConfigurationMaster customerConfigurationMaster){
        log.info("ENTRY - CustomerConfigurationMaster to CustomerConfigurationMasterResponseDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        CustomerConfigurationMasterResponseDTO customerConfigurationMasterResponseDTO = modelMapper.map(customerConfigurationMaster,CustomerConfigurationMasterResponseDTO.class);
        DefaultFieldsResponseDTO.BranchDTO branchDTO=new DefaultFieldsResponseDTO.BranchDTO();
        if (customerConfigurationMaster.getBranchMaster()!=null){
            modelMapper.map(customerConfigurationMaster.getBranchMaster(),branchDTO);
            customerConfigurationMasterResponseDTO.setBranch(branchDTO);
        }
        DefaultFieldsResponseDTO.CompanyDTO companyDTO=new DefaultFieldsResponseDTO.CompanyDTO();
        if (customerConfigurationMaster.getCompanyMaster()!=null){
            modelMapper.map(customerConfigurationMaster.getCompanyMaster(),companyDTO);
            customerConfigurationMasterResponseDTO.setCompany(companyDTO);
        }
        DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO=new DefaultFieldsResponseDTO.GroupCompanyDTO();
        if (customerConfigurationMaster.getGroupCompanyMaster()!=null){
            modelMapper.map(customerConfigurationMaster.getGroupCompanyMaster(),groupCompanyDTO);
            customerConfigurationMasterResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        CustomerConfigurationMasterResponseDTO.CustomerMasterDTO customerMasterDTO= new CustomerConfigurationMasterResponseDTO.CustomerMasterDTO();
        if (customerConfigurationMaster.getCustomerMaster() != null) {
            modelMapper.map(customerConfigurationMaster.getCustomerMaster(),customerMasterDTO);
            customerConfigurationMasterResponseDTO.setCustomer(customerMasterDTO);
        }
        customerConfigurationMasterResponseDTO.setModule(customerConfigurationMaster.getModule().toUpperCase());
        customerConfigurationMasterResponseDTO.setCreatedUser(customerConfigurationMaster.getCreatedBy());
        customerConfigurationMasterResponseDTO.setCreatedDate(customerConfigurationMaster.getCreatedDate());
        customerConfigurationMasterResponseDTO.setUpdatedUser(customerConfigurationMaster.getLastModifiedBy());
        customerConfigurationMasterResponseDTO.setUpdatedDate(customerConfigurationMaster.getLastModifiedDate());
        log.info("EXIT");
        return customerConfigurationMasterResponseDTO;
    }

    public CustomerConfigurationMaster convertRequestToEntity(CustomerConfigurationRequestDTO customerConfigurationRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - CustomerConfigurationRequest to Entity mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CustomerConfigurationMaster customerConfigurationMaster=modelMapper.map(customerConfigurationRequestDTO,CustomerConfigurationMaster.class);
        modelMapper.map(dateAndTimeRequestDto,customerConfigurationMaster);
        customerConfigurationMaster.setModule(customerConfigurationRequestDTO.getModule().toLowerCase());
        customerConfigurationMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(customerConfigurationRequestDTO.getGroupCompanyId())));
        customerConfigurationMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(customerConfigurationRequestDTO.getBranchId())));
        customerConfigurationMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(customerConfigurationRequestDTO.getCompanyId())));
        customerConfigurationMaster.setCustomerMaster(customerMasterService.getCustomerById(Long.parseLong(customerConfigurationRequestDTO.getCustomer().getId())));
        log.info("EXIT");
        return customerConfigurationMaster;
    }

    public void convertUpdateRequestToEntity(CustomerConfigurationRequestDTO customerConfigurationRequestDTO,CustomerConfigurationMaster customerConfigurationMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - Update CustomerConfigurationRequest to CustomerConfigurationMaster");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(customerConfigurationRequestDTO,customerConfigurationMaster);
        modelMapper.map(dateAndTimeRequestDto,customerConfigurationMaster);
        customerConfigurationMaster.setModule(customerConfigurationRequestDTO.getModule().toLowerCase());
    }

}
