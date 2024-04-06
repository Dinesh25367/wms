package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.ConfigurationMasterService;
import com.newage.wms.service.TrnHeaderCustomsAddlDetailsService;
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
public class TrnHeaderCustomsAddlDetailsMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConfigurationMasterService configurationMasterService;

    @Autowired
    private TrnHeaderCustomsAddlDetailsService trnHeaderCustomsAddlDetailsService;

    private String number = "number";

    private String date = "date";

    private String textbox = "textbox";


    public List<TrnHeaderCustomsAddlDetails> convertRequestListToEntityList(List<TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsDTOList, DateAndTimeRequestDto dateAndTimeRequestDto) {
        if (!CollectionUtils.isEmpty(trnHeaderCustomsAddlDetailsDTOList)){
            return trnHeaderCustomsAddlDetailsDTOList.stream()
                    .map(trnHeaderCustomsAddlDetailsDTO -> convertRequestToEntity(trnHeaderCustomsAddlDetailsDTO, dateAndTimeRequestDto))
                    .collect(Collectors.toList());
        }else{
            return Collections.emptyList();
        }
    }

    public void convertUpdateRequestListToEntityListAndDeleteNonExistingIds(List<TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsDTOList,TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto){
        List<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsListToBeDeleted = new ArrayList<>();
        if (trnHeaderAsnToBeUpdated.getTrnHeaderCustomsAddlDetailsList() != null && !trnHeaderAsnToBeUpdated.getTrnHeaderCustomsAddlDetailsList().isEmpty()) {
            trnHeaderCustomsAddlDetailsListToBeDeleted = trnHeaderAsnToBeUpdated.getTrnHeaderCustomsAddlDetailsList();
        }
        if (!CollectionUtils.isEmpty(trnHeaderCustomsAddlDetailsDTOList)) {
            if (!CollectionUtils.isEmpty(trnHeaderAsnToBeUpdated.getTrnHeaderCustomsAddlDetailsList())) {
                convertUpdateRequestListToEntityList(trnHeaderCustomsAddlDetailsDTOList, trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
                identifyAndDeleteNonExistingIds(trnHeaderCustomsAddlDetailsDTOList,trnHeaderCustomsAddlDetailsListToBeDeleted);
            }else {
                List<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsList = convertRequestListToEntityList(trnHeaderCustomsAddlDetailsDTOList, dateAndTimeRequestDto);
                trnHeaderAsnToBeUpdated.setTrnHeaderCustomsAddlDetailsList(trnHeaderCustomsAddlDetailsList);
            }
        }else
        {
            trnHeaderAsnToBeUpdated.setTrnHeaderCustomsAddlDetailsList(null);
            if (trnHeaderCustomsAddlDetailsListToBeDeleted != null) {
                trnHeaderCustomsAddlDetailsService.deleteAllInIterable(trnHeaderCustomsAddlDetailsListToBeDeleted);
            }
        }
    }

    public void convertUpdateRequestListToEntityList(List<TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsDTOList, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        List<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsList = trnHeaderCustomsAddlDetailsDTOList.stream()
                .map(trnHeaderCustomsAddlDetailsDTO -> convertUpdateRequestToEntity(trnHeaderCustomsAddlDetailsDTO, dateAndTimeRequestDto))
                .collect(Collectors.toList());
        trnHeaderAsnToBeUpdated.setTrnHeaderCustomsAddlDetailsList(trnHeaderCustomsAddlDetailsList);
    }

    public List<TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO> convertEntityListToResponseList(List<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsList) {
        if (!CollectionUtils.isEmpty(trnHeaderCustomsAddlDetailsList)) {
            // Convert the list of entities to DTOs using streams
            List<TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsDTOList =
                    trnHeaderCustomsAddlDetailsList.stream()
                            .map(this::convertEntityToResponse)
                            .collect(Collectors.toList());
            // Filter and retrieve ConfigurationMaster entities
            List<ConfigurationMaster> configurationMasterList = StreamSupport.stream(
                            configurationMasterService.getAllForWmsAsnCustoms().spliterator(), false)
                    .collect(Collectors.toList());
            // Perform additional processing if needed
            if (configurationMasterList.size() > trnHeaderCustomsAddlDetailsDTOList.size()) {
                List<TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsNewDTOList =
                        getExcessConfigurationList(trnHeaderCustomsAddlDetailsDTOList, configurationMasterList);
                // Add the additional DTOs to the main list
                trnHeaderCustomsAddlDetailsDTOList.addAll(trnHeaderCustomsAddlDetailsNewDTOList);
            }
            return trnHeaderCustomsAddlDetailsDTOList;
        }else {return Collections.emptyList();}
    }

    public TrnHeaderCustomsAddlDetails convertRequestToEntity(TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO trnHeaderCustomsAddlDetailsDTO,
                                                              DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        ConfigurationMaster configurationMaster = configurationMasterService.getById(Long.parseLong(trnHeaderCustomsAddlDetailsDTO.getConfigurationId()));
        TrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetails = new TrnHeaderCustomsAddlDetails();
        TrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetailsNew  = setTrnHeaderCustomsAddlDetailsSaveAndUpdate(
                trnHeaderCustomsAddlDetails,trnHeaderCustomsAddlDetailsDTO,configurationMaster);
        trnHeaderCustomsAddlDetailsNew.setId(null);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderCustomsAddlDetailsNew);
        return trnHeaderCustomsAddlDetailsNew;
    }

    private TrnHeaderCustomsAddlDetails convertUpdateRequestToEntity(TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO trnHeaderCustomsAddlDetailsDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        ConfigurationMaster configurationMaster = configurationMasterService.getById(Long.parseLong(trnHeaderCustomsAddlDetailsDTO.getConfigurationId()));
        TrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetails = new TrnHeaderCustomsAddlDetails();
        if (trnHeaderCustomsAddlDetailsDTO.getId() != null && !trnHeaderCustomsAddlDetailsDTO.getId().isBlank() && !trnHeaderCustomsAddlDetailsDTO.getId().isEmpty()){
            trnHeaderCustomsAddlDetails = trnHeaderCustomsAddlDetailsService.getById(Long.parseLong(trnHeaderCustomsAddlDetailsDTO.getId()));
            TrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetailsExisting = setTrnHeaderCustomsAddlDetailsSaveAndUpdate(trnHeaderCustomsAddlDetails,trnHeaderCustomsAddlDetailsDTO,configurationMaster);
            modelMapper.map(trnHeaderCustomsAddlDetailsExisting,trnHeaderCustomsAddlDetails);
        }
        TrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetailsNew = setTrnHeaderCustomsAddlDetailsSaveAndUpdate(trnHeaderCustomsAddlDetails,trnHeaderCustomsAddlDetailsDTO,configurationMaster);
        modelMapper.map(trnHeaderCustomsAddlDetailsNew,trnHeaderCustomsAddlDetails);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderCustomsAddlDetails);
        return trnHeaderCustomsAddlDetails;
    }

    public TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO convertEntityToResponse(TrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetails) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO trnHeaderCustomsAddlDetailsDTO = modelMapper.map(trnHeaderCustomsAddlDetails, TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO.class);
        ConfigurationMaster configurationMaster = configurationMasterService.getById(trnHeaderCustomsAddlDetails.getConfigurationMaster().getId());
        modelMapper.map(configurationMaster,trnHeaderCustomsAddlDetailsDTO);
        trnHeaderCustomsAddlDetailsDTO.setId(trnHeaderCustomsAddlDetails.getId());
        trnHeaderCustomsAddlDetailsDTO.setConfigurationId(configurationMaster.getId());
        trnHeaderCustomsAddlDetailsDTO.setCreatedUser(trnHeaderCustomsAddlDetails.getCreatedBy());
        trnHeaderCustomsAddlDetailsDTO.setUpdatedUser(trnHeaderCustomsAddlDetails.getLastModifiedBy());
        trnHeaderCustomsAddlDetailsDTO.setUpdatedDate(trnHeaderCustomsAddlDetails.getLastModifiedDate());
        if (configurationMaster.getDataType().toLowerCase().equals(textbox)){
            trnHeaderCustomsAddlDetailsDTO.setTextbox(trnHeaderCustomsAddlDetails.getCharValue());
        }
        if (configurationMaster.getDataType().toLowerCase().equals(date)){
            trnHeaderCustomsAddlDetailsDTO.setDate(trnHeaderCustomsAddlDetails.getDateValue());
        }
        if (configurationMaster.getDataType().toLowerCase().equals(number)){
            trnHeaderCustomsAddlDetailsDTO.setNumber(trnHeaderCustomsAddlDetails.getNumberValue());
        }
        return trnHeaderCustomsAddlDetailsDTO;
    }

    private TrnHeaderCustomsAddlDetails setTrnHeaderCustomsAddlDetailsSaveAndUpdate(TrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetails,
                                                                                    TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO trnHeaderCustomsAddlDetailsDTO,
                                                                                    ConfigurationMaster configurationMaster) {
        trnHeaderCustomsAddlDetails.setFieldName(configurationMaster.getValue());
        trnHeaderCustomsAddlDetails.setFieldDataType(configurationMaster.getDataType());
        trnHeaderCustomsAddlDetails.setConfigurationMaster(configurationMaster);
        if (configurationMaster.getDataType().toLowerCase().equals(date)){
            trnHeaderCustomsAddlDetails.setDateValue(trnHeaderCustomsAddlDetailsDTO.getDate());
            trnHeaderCustomsAddlDetails.setCharValue(null);
            trnHeaderCustomsAddlDetails.setNumberValue(null);
            trnHeaderCustomsAddlDetails.setFieldDataType(date);
        }
        if (configurationMaster.getDataType().toLowerCase().equals(number)){
            trnHeaderCustomsAddlDetails.setNumberValue(trnHeaderCustomsAddlDetailsDTO.getNumber());
            trnHeaderCustomsAddlDetails.setCharValue(null);
            trnHeaderCustomsAddlDetails.setDateValue(null);
            trnHeaderCustomsAddlDetails.setFieldDataType(number);
        }
        if (configurationMaster.getDataType().toLowerCase().equals(textbox)){
            trnHeaderCustomsAddlDetails.setCharValue(trnHeaderCustomsAddlDetailsDTO.getTextbox());
            trnHeaderCustomsAddlDetails.setNumberValue(null);
            trnHeaderCustomsAddlDetails.setDateValue(null);
            trnHeaderCustomsAddlDetails.setFieldDataType(textbox);
        }
        return trnHeaderCustomsAddlDetails;
    }

    private List<TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO> getExcessConfigurationList(
            List<TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsDTOList,
            List<ConfigurationMaster> configurationMasterList) {
        // Create a HashSet of existing IDs from trnHeaderCustomsAddlDetailsDTOList
        Set<Long> existingIds = trnHeaderCustomsAddlDetailsDTOList.stream()
                .map(TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO::getConfigurationId)
                .collect(Collectors.toSet());
        // Use streams to filter and map the configurationMasterList
        return configurationMasterList.stream()
                .filter(configurationMaster -> !existingIds.contains(configurationMaster.getId()))
                .map(configurationMaster -> {
                    // Map ConfigurationMaster to TrnHeaderCustomsAddlDetailsDTO
                    TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO trnHeaderCustomsAddlDetailsDTOnew =
                            modelMapper.map(configurationMaster, TrnResponseDTO.TrnHeaderCustomsAddlDetailsDTO.class);
                    // Set ID to null and set configurationId
                    trnHeaderCustomsAddlDetailsDTOnew.setId(null);
                    trnHeaderCustomsAddlDetailsDTOnew.setConfigurationId(configurationMaster.getId());
                    return trnHeaderCustomsAddlDetailsDTOnew; // Collect mapped objects
                })
                .collect(Collectors.toList()); // Collect the results into a list
    }

    private void identifyAndDeleteNonExistingIds(List<TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO> trnHeaderCustomsAddlDetailsList,List<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsListToBeDeleted ){
        Set<Long> newIdsList = new HashSet<>();
        for (TrnRequestDTO.TrnHeaderCustomsAddlDetailsDTO trnHeaderCustomsAddlDetailsDTO : trnHeaderCustomsAddlDetailsList) {
            if (trnHeaderCustomsAddlDetailsDTO.getId() != null && !trnHeaderCustomsAddlDetailsDTO.getId().isEmpty() &&
                    !trnHeaderCustomsAddlDetailsDTO.getId().isBlank()) {
                newIdsList.add(Long.parseLong(trnHeaderCustomsAddlDetailsDTO.getId()));
            }
        }
        if (!newIdsList.isEmpty()) {
            Iterator<TrnHeaderCustomsAddlDetails> iterator = trnHeaderCustomsAddlDetailsListToBeDeleted.iterator();
            while (iterator.hasNext()) {
                TrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetails = iterator.next();
                Long id = trnHeaderCustomsAddlDetails.getId();
                // Check if the id exists in firstIds set
                if (newIdsList.contains(id)) {
                    iterator.remove(); // Remove the element from secondList
                }
            }
            trnHeaderCustomsAddlDetailsService.deleteAllInIterable(trnHeaderCustomsAddlDetailsListToBeDeleted);
        }else {trnHeaderCustomsAddlDetailsService.deleteAllInIterable(trnHeaderCustomsAddlDetailsListToBeDeleted);}
    }

}
