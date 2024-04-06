package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QCustomerConfigurationMaster;
import com.newage.wms.service.*;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class TrnHeaderAddlDetailsMapper {

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

    @Autowired
    private TrnHeaderAddlDetailsService trnHeaderAddlDetailsService;

    private final String freetext = "freetext";

    private final String character = "character";

    private final String number = "number";

    public List<TrnHeaderAddlDetails> convertRequestListToEntityList(List<TrnRequestDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsDTOList, DateAndTimeRequestDto dateAndTimeRequestDto) {
        if (!CollectionUtils.isEmpty(trnHeaderAddlDetailsDTOList)) {
            return trnHeaderAddlDetailsDTOList.stream()
                    .map(trnHeaderAddlDetailsDTO -> convertRequestToEntity(trnHeaderAddlDetailsDTO, dateAndTimeRequestDto))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public TrnHeaderAddlDetails convertRequestToEntity(TrnRequestDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);// Ensure strict matching
        CustomerConfigurationMaster customerConfigurationMaster = customerConfigurationMasterService.getById(Long.parseLong(trnHeaderAddlDetailsDTO.getConfigurationId()));
        TrnHeaderAddlDetails trnHeaderAddlDetails =new TrnHeaderAddlDetails();
        TrnHeaderAddlDetails trnHeaderAddlDetailsNew  = setTrnHeaderAddlDetailsSaveAndUpdate(
                trnHeaderAddlDetails,trnHeaderAddlDetailsDTO,customerConfigurationMaster);
        trnHeaderAddlDetailsNew.setId(null);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderAddlDetailsNew);
        return trnHeaderAddlDetailsNew;
    }

    private TrnHeaderAddlDetails saveTrnHeaderAdditionalDetails(TrnRequestDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        TrnHeaderAddlDetails trnHeaderAddlDetails = modelMapper.map(dateAndTimeRequestDto,TrnHeaderAddlDetails.class);
        trnHeaderAddlDetails.setFieldName(trnHeaderAddlDetailsDTO.getLabel());
        trnHeaderAddlDetails.setTransactionType("ASN");
        trnHeaderAddlDetails.setIsMandatory("No");
        if (trnHeaderAddlDetailsDTO.getDate() != null){
            trnHeaderAddlDetails.setDateValue(trnHeaderAddlDetailsDTO.getDate());
            trnHeaderAddlDetails.setFieldDataType("date");
        }
        if (trnHeaderAddlDetailsDTO.getFreetext() != null){
            trnHeaderAddlDetails.setCharValue(trnHeaderAddlDetailsDTO.getFreetext());
            trnHeaderAddlDetails.setFieldDataType(freetext);
        }
        if (trnHeaderAddlDetailsDTO.getCharacter() != null){
            trnHeaderAddlDetails.setCharValue(trnHeaderAddlDetailsDTO.getCharacter());
            trnHeaderAddlDetails.setFieldDataType(character);
        }
        if (trnHeaderAddlDetailsDTO.getNumber() != null ){
            trnHeaderAddlDetails.setNumberValue(trnHeaderAddlDetailsDTO.getNumber());
            trnHeaderAddlDetails.setFieldDataType(number);
        }
        return trnHeaderAddlDetails;
    }

    private TrnHeaderAddlDetails setTrnHeaderAddlDetailsSaveAndUpdate(TrnHeaderAddlDetails trnHeaderAddlDetails, TrnRequestDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTO, CustomerConfigurationMaster customerConfigurationMaster) {
        trnHeaderAddlDetails.setTransactionType("ASN");
        trnHeaderAddlDetails.setFieldDataType(customerConfigurationMaster.getDataType());
        trnHeaderAddlDetails.setFieldName(customerConfigurationMaster.getValue());
        trnHeaderAddlDetails.setIsMandatory(customerConfigurationMaster.getIsMandatory());
        trnHeaderAddlDetails.setCustomerConfigurationMaster(customerConfigurationMaster);
        if (customerConfigurationMaster.getDataType().equalsIgnoreCase("date")){
            trnHeaderAddlDetails.setDateValue(trnHeaderAddlDetailsDTO.getDate());
            trnHeaderAddlDetails.setCharValue(null);
            trnHeaderAddlDetails.setNumberValue(null);
        }
        if (customerConfigurationMaster.getDataType().equalsIgnoreCase(number)){
            trnHeaderAddlDetails.setNumberValue(trnHeaderAddlDetailsDTO.getNumber());
            trnHeaderAddlDetails.setCharValue(null);
            trnHeaderAddlDetails.setDateValue(null);
        }
        if (customerConfigurationMaster.getDataType().equalsIgnoreCase(character)){
            trnHeaderAddlDetails.setCharValue(trnHeaderAddlDetailsDTO.getCharacter());
            trnHeaderAddlDetails.setNumberValue(null);
            trnHeaderAddlDetails.setDateValue(null);
        }
        if (customerConfigurationMaster.getDataType().equalsIgnoreCase(freetext)){
            trnHeaderAddlDetails.setCharValue(trnHeaderAddlDetailsDTO.getFreetext());
            trnHeaderAddlDetails.setNumberValue(null);
            trnHeaderAddlDetails.setDateValue(null);
        }
        if (customerConfigurationMaster.getIsMandatory().equals("Yes") && trnHeaderAddlDetails.getCharValue() == null && trnHeaderAddlDetails.getNumberValue() == null && trnHeaderAddlDetails.getDateValue() == null){
            throw new ServiceException(ServiceErrors.ADDL_DETAILS_MANDATORY_FIELD_IS_EMPTY.CODE,
                    ServiceErrors.ADDL_DETAILS_MANDATORY_FIELD_IS_EMPTY.KEY);
        }
        return trnHeaderAddlDetails;
    }

    public void convertUpdateRequestListToEntityListAndDeleteNonExistingIds(List<TrnRequestDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsDTOList,TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto){
        List<TrnHeaderAddlDetails> trnHeaderAddlDetailsListToBeDeleted = new ArrayList<>();
        if (trnHeaderAsnToBeUpdated.getTrnHeaderAddlDetailsList() != null && !trnHeaderAsnToBeUpdated.getTrnHeaderAddlDetailsList().isEmpty()) {
            trnHeaderAddlDetailsListToBeDeleted = trnHeaderAsnToBeUpdated.getTrnHeaderAddlDetailsList();
        }
        if (!CollectionUtils.isEmpty(trnHeaderAddlDetailsDTOList)) {
            if (!CollectionUtils.isEmpty(trnHeaderAsnToBeUpdated.getTrnHeaderAddlDetailsList())) {
                convertUpdateRequestListToEntityList(trnHeaderAddlDetailsDTOList, trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
                identifyAndDeleteNonExistingIds(trnHeaderAddlDetailsDTOList,trnHeaderAddlDetailsListToBeDeleted);
            }else {
                List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList = convertRequestListToEntityList(trnHeaderAddlDetailsDTOList, dateAndTimeRequestDto);
                List<TrnHeaderAddlDetails> trnHeaderAddlDetailsNewFlexiFieldsList = convertRequestListToEntityFlexiList(trnHeaderAddlDetailsDTOList, dateAndTimeRequestDto);
                trnHeaderAddlDetailsList.addAll(trnHeaderAddlDetailsNewFlexiFieldsList);
                trnHeaderAsnToBeUpdated.setTrnHeaderAddlDetailsList(trnHeaderAddlDetailsList);
            }
        }else
        {
            trnHeaderAsnToBeUpdated.setTrnHeaderAddlDetailsList(null);
            if (trnHeaderAddlDetailsListToBeDeleted != null) {
                trnHeaderAddlDetailsService.deleteAllInIterable(trnHeaderAddlDetailsListToBeDeleted);
            }
        }
    }

    private void identifyAndDeleteNonExistingIds(List<TrnRequestDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsDTOList, List<TrnHeaderAddlDetails> trnHeaderAddlDetailsListToBeDeleted){
        Set<Long> newIdsList = new HashSet<>();
        for (TrnRequestDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTO : trnHeaderAddlDetailsDTOList) {
            if (trnHeaderAddlDetailsDTO.getId() != null && !trnHeaderAddlDetailsDTO.getId().isEmpty() &&
                    !trnHeaderAddlDetailsDTO.getId().isBlank()) {
                newIdsList.add(Long.parseLong(trnHeaderAddlDetailsDTO.getId()));
            }
        }
        if (!newIdsList.isEmpty()) {
            Iterator<TrnHeaderAddlDetails> iterator = trnHeaderAddlDetailsListToBeDeleted.iterator();
            while (iterator.hasNext()) {
                TrnHeaderAddlDetails trnHeaderAddlDetails = iterator.next();
                Long id = trnHeaderAddlDetails.getId();
                // Check if the id exists in firstIds set
                if (newIdsList.contains(id)) {
                    iterator.remove(); // Remove the element from secondList
                }
            }
            trnHeaderAddlDetailsService.deleteAllInIterable(trnHeaderAddlDetailsListToBeDeleted);
        }else {trnHeaderAddlDetailsService.deleteAllInIterable(trnHeaderAddlDetailsListToBeDeleted);}
    }

    public void convertUpdateRequestListToEntityList(List<TrnRequestDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsDTOList, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList = trnHeaderAddlDetailsDTOList.stream()
                .map(trnHeaderAddlDetailsDTO -> convertUpdateRequestToEntity(trnHeaderAddlDetailsDTO, dateAndTimeRequestDto))
                .collect(Collectors.toList());
        trnHeaderAsnToBeUpdated.setTrnHeaderAddlDetailsList(trnHeaderAddlDetailsList);
    }

    private TrnHeaderAddlDetails convertUpdateRequestToEntity(TrnRequestDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnHeaderAddlDetailsDTO.getConfigurationId() != null && !trnHeaderAddlDetailsDTO.getConfigurationId().isEmpty()
                && !trnHeaderAddlDetailsDTO.getConfigurationId().isBlank()) {
            CustomerConfigurationMaster customerConfigurationMaster = customerConfigurationMasterService.getById(Long.parseLong(trnHeaderAddlDetailsDTO.getConfigurationId()));
            TrnHeaderAddlDetails trnHeaderAddlDetails = new TrnHeaderAddlDetails();
            if (trnHeaderAddlDetailsDTO.getId() != null && !trnHeaderAddlDetailsDTO.getId().isBlank() && !trnHeaderAddlDetailsDTO.getId().isEmpty()) {
                trnHeaderAddlDetails = trnHeaderAddlDetailsService.getById(Long.parseLong(trnHeaderAddlDetailsDTO.getId()));
                TrnHeaderAddlDetails trnHeaderAddlDetailsExisting = setTrnHeaderAddlDetailsSaveAndUpdate(trnHeaderAddlDetails, trnHeaderAddlDetailsDTO, customerConfigurationMaster);
                modelMapper.map(trnHeaderAddlDetailsExisting, trnHeaderAddlDetails);
            }
            TrnHeaderAddlDetails trnHeaderAddlDetailsNew = setTrnHeaderAddlDetailsSaveAndUpdate(trnHeaderAddlDetails, trnHeaderAddlDetailsDTO, customerConfigurationMaster);
            modelMapper.map(trnHeaderAddlDetailsNew, trnHeaderAddlDetailsNew);
            modelMapper.map(dateAndTimeRequestDto, trnHeaderAddlDetails);
            return trnHeaderAddlDetails;
        }else {
            TrnHeaderAddlDetails trnHeaderAddlDetails1 = new TrnHeaderAddlDetails();
            if (trnHeaderAddlDetailsDTO.getId() != null && !trnHeaderAddlDetailsDTO.getId().isBlank() && !trnHeaderAddlDetailsDTO.getId().isEmpty()) {
                trnHeaderAddlDetails1 = trnHeaderAddlDetailsService.getById(Long.parseLong(trnHeaderAddlDetailsDTO.getId()));
                TrnHeaderAddlDetails trnHeaderAddlDetailsExisting = setTrnHeaderAddlDetailsSaveAndUpdate(trnHeaderAddlDetails1, trnHeaderAddlDetailsDTO);
                modelMapper.map(trnHeaderAddlDetailsExisting, trnHeaderAddlDetails1);
            }
            TrnHeaderAddlDetails trnHeaderAddlDetailsNew1 = setTrnHeaderAddlDetailsSaveAndUpdate(trnHeaderAddlDetails1, trnHeaderAddlDetailsDTO);
            modelMapper.map(trnHeaderAddlDetailsNew1, trnHeaderAddlDetailsNew1);
            modelMapper.map(dateAndTimeRequestDto, trnHeaderAddlDetails1);
            return trnHeaderAddlDetails1;
        }
    }

    private TrnHeaderAddlDetails setTrnHeaderAddlDetailsSaveAndUpdate(TrnHeaderAddlDetails trnHeaderAddlDetails, TrnRequestDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTO) {
        trnHeaderAddlDetails.setTransactionType("ASN");
        trnHeaderAddlDetails.setFieldName(trnHeaderAddlDetailsDTO.getLabel());
        trnHeaderAddlDetails.setIsMandatory("No");
        if (trnHeaderAddlDetailsDTO.getDate() != null){
            trnHeaderAddlDetails.setDateValue(trnHeaderAddlDetailsDTO.getDate());
            trnHeaderAddlDetails.setFieldDataType("date");
            trnHeaderAddlDetails.setCharValue(null);
            trnHeaderAddlDetails.setNumberValue(null);
        }
        if (trnHeaderAddlDetailsDTO.getNumber() != null){
            trnHeaderAddlDetails.setNumberValue(trnHeaderAddlDetailsDTO.getNumber());
            trnHeaderAddlDetails.setFieldDataType(number);
            trnHeaderAddlDetails.setCharValue(null);
            trnHeaderAddlDetails.setDateValue(null);
        }
        if (trnHeaderAddlDetailsDTO.getCharacter() != null){
            trnHeaderAddlDetails.setCharValue(trnHeaderAddlDetailsDTO.getCharacter());
            trnHeaderAddlDetails.setFieldDataType(character);
            trnHeaderAddlDetails.setNumberValue(null);
            trnHeaderAddlDetails.setDateValue(null);
        }
        if (trnHeaderAddlDetailsDTO.getFreetext() != null){
            trnHeaderAddlDetails.setCharValue(trnHeaderAddlDetailsDTO.getFreetext());
            trnHeaderAddlDetails.setFieldDataType(freetext);
            trnHeaderAddlDetails.setNumberValue(null);
            trnHeaderAddlDetails.setDateValue(null);
        }
        return trnHeaderAddlDetails;
    }


    public List<TrnResponseDTO.TrnHeaderAddlDetailsDTO> convertEntityListToResponseList(List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList) {

        if (!CollectionUtils.isEmpty(trnHeaderAddlDetailsList)) {
            // Convert the list of entities to DTOs using streams
            List<TrnResponseDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsDTOList =
                    trnHeaderAddlDetailsList.stream()
                            .map(this::convertEntityToResponse)
                            .collect(Collectors.toList());
            trnHeaderAddlDetailsDTOList.removeIf(Objects::isNull);
            QCustomerConfigurationMaster qCustomerConfigurationMaster = QCustomerConfigurationMaster.customerConfigurationMaster;
            BooleanBuilder predicate = new BooleanBuilder();
            OrderSpecifier<String> orderSpecifier = qCustomerConfigurationMaster.configurationFlag.asc();
            predicate.and(qCustomerConfigurationMaster.module.eq("wms"));
            predicate.and(qCustomerConfigurationMaster.configurationFlag.eq("asnheaderadditional"));
            Long customerId = getCustomerId(trnHeaderAddlDetailsList);
            predicate.and(qCustomerConfigurationMaster.customerMaster.id.eq(customerId));
            List<CustomerConfigurationMaster> customerConfigurationMasterList = StreamSupport.stream(
                            customerConfigurationMasterService.getAll(predicate, orderSpecifier).spliterator(), false)
                    .collect(Collectors.toList());
            // Perform additional processing if needed
            if (customerConfigurationMasterList.size() > trnHeaderAddlDetailsDTOList.size()) {
                List<TrnResponseDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsDTOListNew =
                        getExcessCustomerConfigurationList(trnHeaderAddlDetailsDTOList, customerConfigurationMasterList);
                // Add the additional DTOs to the main list
                trnHeaderAddlDetailsDTOList.addAll(trnHeaderAddlDetailsDTOListNew);
            }
            return trnHeaderAddlDetailsDTOList;
        }else {return Collections.emptyList();}
    }

    private Long getCustomerId(List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList) {
        Long customerId = 0L;
        for (TrnHeaderAddlDetails trnHeaderAddlDetails : trnHeaderAddlDetailsList) {
            if (trnHeaderAddlDetails.getCustomerConfigurationMaster() != null) {
                customerId=trnHeaderAddlDetails.getCustomerConfigurationMaster().getCustomerMaster().getId();
            }
        }
        return customerId;
    }

    public TrnResponseDTO.TrnHeaderAddlDetailsDTO convertEntityToResponse(TrnHeaderAddlDetails trnHeaderAddlDetails) {
        if(trnHeaderAddlDetails.getCustomerConfigurationMaster()!=null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
            TrnResponseDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTO = modelMapper.map(trnHeaderAddlDetails, TrnResponseDTO.TrnHeaderAddlDetailsDTO.class);
            CustomerConfigurationMaster customerConfigurationMaster = customerConfigurationMasterService.getById(trnHeaderAddlDetails.getCustomerConfigurationMaster().getId());
            modelMapper.map(customerConfigurationMaster, trnHeaderAddlDetailsDTO);
            trnHeaderAddlDetailsDTO.setId(trnHeaderAddlDetails.getId());
            trnHeaderAddlDetailsDTO.setLabel(trnHeaderAddlDetails.getFieldName());
            trnHeaderAddlDetailsDTO.setConfigurationId(customerConfigurationMaster.getId());
            trnHeaderAddlDetailsDTO.setIsMandatory(trnHeaderAddlDetails.getIsMandatory());
            trnHeaderAddlDetailsDTO.setCreatedUser(trnHeaderAddlDetails.getCreatedBy());
            trnHeaderAddlDetailsDTO.setUpdatedUser(trnHeaderAddlDetails.getLastModifiedBy());
            trnHeaderAddlDetailsDTO.setUpdatedDate(trnHeaderAddlDetails.getLastModifiedDate());
            if (customerConfigurationMaster.getDataType().equalsIgnoreCase(character)) {
                trnHeaderAddlDetailsDTO.setCharacter(trnHeaderAddlDetails.getCharValue());
                trnHeaderAddlDetailsDTO.setDataType(character);
            }
            if (customerConfigurationMaster.getDataType().equalsIgnoreCase(freetext)) {
                trnHeaderAddlDetailsDTO.setFreetext(trnHeaderAddlDetails.getCharValue());
                trnHeaderAddlDetailsDTO.setDataType(freetext);
            }
            if (customerConfigurationMaster.getDataType().equalsIgnoreCase("date")) {
                trnHeaderAddlDetailsDTO.setDate(trnHeaderAddlDetails.getDateValue());
                trnHeaderAddlDetailsDTO.setDataType("date");
            }
            if (customerConfigurationMaster.getDataType().equalsIgnoreCase(number)) {
                trnHeaderAddlDetailsDTO.setNumber(trnHeaderAddlDetails.getNumberValue());
                trnHeaderAddlDetailsDTO.setDataType(number);
            }
            return trnHeaderAddlDetailsDTO;
        }
        else{
            return null;
        }
    }

    private List<TrnResponseDTO.TrnHeaderAddlDetailsDTO> getExcessCustomerConfigurationList(
            List<TrnResponseDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsDTOList,
            List<CustomerConfigurationMaster> customerConfigurationMasterList) {
        // Create a HashSet of existing IDs from trnHeaderAddlDetailsDTOList
        Set<Long> existingIds = trnHeaderAddlDetailsDTOList.stream()
                .map(TrnResponseDTO.TrnHeaderAddlDetailsDTO::getConfigurationId)
                .collect(Collectors.toSet());
        // Use streams to filter and map the customerConfigurationMasterList
        return customerConfigurationMasterList.stream()
                .filter(customerConfigurationMaster -> !existingIds.contains(customerConfigurationMaster.getId()))
                .map(customerConfigurationMaster -> {
                    // Map customerConfigurationMaster to TrnHeaderCustomsAddlDetailsDTO
                    TrnResponseDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTOnew =
                            modelMapper.map(customerConfigurationMaster, TrnResponseDTO.TrnHeaderAddlDetailsDTO.class);
                    // Set ID to null and set configurationId
                    trnHeaderAddlDetailsDTOnew.setId(null);
                    trnHeaderAddlDetailsDTOnew.setConfigurationId(customerConfigurationMaster.getId());
                    return trnHeaderAddlDetailsDTOnew; // Collect mapped objects
                })
                .collect(Collectors.toList()); // Collect the results into a list
    }


    public List<TrnHeaderAddlDetails> convertRequestListToEntityFlexiList(List<TrnRequestDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsNewFlexiFieldsList, DateAndTimeRequestDto dateAndTimeRequestDto) {
        if (!CollectionUtils.isEmpty(trnHeaderAddlDetailsNewFlexiFieldsList)) {
            return trnHeaderAddlDetailsNewFlexiFieldsList.stream()
                    .map(trnHeaderAddlDetailsDTO -> saveTrnHeaderAdditionalDetails(trnHeaderAddlDetailsDTO, dateAndTimeRequestDto))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


    public List<TrnResponseDTO.TrnHeaderAddlDetailsDTO> convertEntityListToResponseFlexiList(List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList) {
        if (!CollectionUtils.isEmpty(trnHeaderAddlDetailsList)) {
            // Convert the list of entities to DTOs using streams
            List<TrnResponseDTO.TrnHeaderAddlDetailsDTO> trnHeaderAddlDetailsDTOList =
                    trnHeaderAddlDetailsList.stream()
                            .map(this::convertEntityToFlexiResponse)
                            .collect(Collectors.toList());
            trnHeaderAddlDetailsDTOList.removeIf(Objects::isNull);
            return trnHeaderAddlDetailsDTOList;
        }else {return Collections.emptyList();}
    }

    private TrnResponseDTO.TrnHeaderAddlDetailsDTO convertEntityToFlexiResponse(TrnHeaderAddlDetails trnHeaderAddlDetails) {
        if(trnHeaderAddlDetails.getCustomerConfigurationMaster()==null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
            TrnResponseDTO.TrnHeaderAddlDetailsDTO trnHeaderAddlDetailsDTO = modelMapper.map(trnHeaderAddlDetails, TrnResponseDTO.TrnHeaderAddlDetailsDTO.class);
            trnHeaderAddlDetailsDTO.setId(trnHeaderAddlDetails.getId());
            trnHeaderAddlDetailsDTO.setIsMandatory("No");
            trnHeaderAddlDetailsDTO.setCreatedUser(trnHeaderAddlDetails.getCreatedBy());
            trnHeaderAddlDetailsDTO.setUpdatedUser(trnHeaderAddlDetails.getLastModifiedBy());
            trnHeaderAddlDetailsDTO.setUpdatedDate(trnHeaderAddlDetails.getLastModifiedDate());
            trnHeaderAddlDetailsDTO.setLabel(trnHeaderAddlDetails.getFieldName());
            trnHeaderAddlDetailsDTO.setValue(trnHeaderAddlDetails.getFieldName());
            if (trnHeaderAddlDetails.getFieldDataType()!=null&& trnHeaderAddlDetails.getFieldDataType().equals(character)) {
                trnHeaderAddlDetailsDTO.setCharacter(trnHeaderAddlDetails.getCharValue());
                trnHeaderAddlDetailsDTO.setDataType(character);
            }
            if (trnHeaderAddlDetails.getFieldDataType()!=null&& trnHeaderAddlDetails.getFieldDataType().equals(freetext)) {
                trnHeaderAddlDetailsDTO.setFreetext(trnHeaderAddlDetails.getCharValue());
                trnHeaderAddlDetailsDTO.setDataType(freetext);
            }
            if (trnHeaderAddlDetails.getFieldDataType()!=null&&trnHeaderAddlDetails.getFieldDataType().equals("date")) {
                trnHeaderAddlDetailsDTO.setDate(trnHeaderAddlDetails.getDateValue());
                trnHeaderAddlDetailsDTO.setDataType("date");
            }
            if (trnHeaderAddlDetails.getFieldDataType()!=null&&trnHeaderAddlDetails.getFieldDataType().equals(number)) {
                trnHeaderAddlDetailsDTO.setNumber(trnHeaderAddlDetails.getNumberValue());
                trnHeaderAddlDetailsDTO.setDataType(number);
            }
            return trnHeaderAddlDetailsDTO;
        }
        return null;
    }
}
