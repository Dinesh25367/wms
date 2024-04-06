package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.entity.TrnHeaderTransportation;
import com.newage.wms.service.TrnHeaderTransportationService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class TrnHeaderTransportationMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TrnHeaderTransportationService trnHeaderTransportationService;


    public List<TrnHeaderTransportation> convertRequestListToEntityList(List<TrnRequestDTO.TrnHeaderTransportationDTO> trnHeaderTransportationDTOList, DateAndTimeRequestDto dateAndTimeRequestDto) {
        if (!CollectionUtils.isEmpty(trnHeaderTransportationDTOList)) {
            return trnHeaderTransportationDTOList.stream()
                    .map(trnHeaderTransportationDTO -> convertRequestToEntity(trnHeaderTransportationDTO, dateAndTimeRequestDto))
                    .collect(Collectors.toList());
        }else{
            return Collections.emptyList();
        }
    }

    public void convertUpdateRequestListToEntityListAndDeleteNonExistingIds(List<TrnRequestDTO.TrnHeaderTransportationDTO> trnHeaderTransportationDTOList,TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto){
        List<TrnHeaderTransportation> trnHeaderTransportationListToBeDeleted = new ArrayList<>();
        if (trnHeaderAsnToBeUpdated.getTrnHeaderTransportationList() != null && !trnHeaderAsnToBeUpdated.getTrnHeaderTransportationList().isEmpty()) {
            trnHeaderTransportationListToBeDeleted = trnHeaderAsnToBeUpdated.getTrnHeaderTransportationList();
        }
        if (!CollectionUtils.isEmpty(trnHeaderTransportationDTOList)) {
            if (!CollectionUtils.isEmpty(trnHeaderAsnToBeUpdated.getTrnHeaderTransportationList())) {
                convertUpdateRequestListToEntityList(trnHeaderTransportationDTOList, trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
                identifyAndDeleteNonExistingIds(trnHeaderTransportationDTOList,trnHeaderTransportationListToBeDeleted);
            }else {
                List<TrnHeaderTransportation> trnHeaderTransportationList = convertRequestListToEntityList(trnHeaderTransportationDTOList, dateAndTimeRequestDto);
                trnHeaderAsnToBeUpdated.setTrnHeaderTransportationList(trnHeaderTransportationList);
            }
        }else
        {
            trnHeaderAsnToBeUpdated.setTrnHeaderTransportationList(null);
            if (trnHeaderTransportationListToBeDeleted != null) {
                trnHeaderTransportationService.deleteAllInIterable(trnHeaderTransportationListToBeDeleted);
            }
        }
    }

    public void convertUpdateRequestListToEntityList(List<TrnRequestDTO.TrnHeaderTransportationDTO> trnHeaderTransportationDTOList, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        List<TrnHeaderTransportation> trnHeaderTransportationList = trnHeaderTransportationDTOList.stream()
                .map(trnHeaderTransportationDTO -> convertUpdateRequestToEntity(trnHeaderTransportationDTO, dateAndTimeRequestDto))
                .collect(Collectors.toList());
        trnHeaderAsnToBeUpdated.setTrnHeaderTransportationList(trnHeaderTransportationList);
    }

    public List<TrnResponseDTO.TrnHeaderTransportationDTO> convertEntityListToResponseList(List<TrnHeaderTransportation> trnHeaderTransportationList) {
        if (!CollectionUtils.isEmpty(trnHeaderTransportationList)) {
            return trnHeaderTransportationList.stream()
                    .map(trnHeaderTransportation -> convertEntityToResponse(trnHeaderTransportation))
                    .collect(Collectors.toList());
        }else {return Collections.emptyList();}
    }

    public TrnHeaderTransportation convertRequestToEntity(TrnRequestDTO.TrnHeaderTransportationDTO trnHeaderTransportationDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderTransportation trnHeaderTransportation = modelMapper.map(trnHeaderTransportationDTO,TrnHeaderTransportation.class);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderTransportation);
        trnHeaderTransportation.setIsOurTransport(trnHeaderTransportationDTO.getOurTransPort());
        trnHeaderTransportation.setId(null);
        trnHeaderTransportation.setNullInEmptyString();
        return trnHeaderTransportation;
    }

    private TrnHeaderTransportation convertUpdateRequestToEntity(TrnRequestDTO.TrnHeaderTransportationDTO trnHeaderTransportationDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        TrnHeaderTransportation trnHeaderTransportation = new TrnHeaderTransportation();
        if (trnHeaderTransportationDTO.getId() != null && !trnHeaderTransportationDTO.getId().isBlank() && !trnHeaderTransportationDTO.getId().isEmpty()){
            trnHeaderTransportation = trnHeaderTransportationService.getById(Long.parseLong(trnHeaderTransportationDTO.getId()));
        }
        trnHeaderTransportation.setIsOurTransport(trnHeaderTransportationDTO.getOurTransPort());
        modelMapper.map(trnHeaderTransportationDTO,trnHeaderTransportation);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderTransportation);
        trnHeaderTransportation.setNullInEmptyString();
        return trnHeaderTransportation;
    }

    private TrnResponseDTO.TrnHeaderTransportationDTO convertEntityToResponse(TrnHeaderTransportation trnHeaderTransportation) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnResponseDTO.TrnHeaderTransportationDTO trnHeaderTransportationDTO = modelMapper.map(trnHeaderTransportation,TrnResponseDTO.TrnHeaderTransportationDTO.class);
        trnHeaderTransportationDTO.setOurTransPort(trnHeaderTransportation.getIsOurTransport());
        trnHeaderTransportationDTO.setCreatedUser(trnHeaderTransportation.getCreatedBy());
        trnHeaderTransportationDTO.setUpdatedUser(trnHeaderTransportation.getLastModifiedBy());
        trnHeaderTransportationDTO.setUpdatedDate(trnHeaderTransportation.getLastModifiedDate());
        return trnHeaderTransportationDTO;
    }

    private void identifyAndDeleteNonExistingIds(List<TrnRequestDTO.TrnHeaderTransportationDTO> trnHeaderTransportationDTOList,List<TrnHeaderTransportation> trnHeaderTransportationListToBeDeleted ){
        Set<Long> newIdsList = new HashSet<>();
        for (TrnRequestDTO.TrnHeaderTransportationDTO trnHeaderTransportationDTO : trnHeaderTransportationDTOList) {
            if (trnHeaderTransportationDTO.getId() != null && !trnHeaderTransportationDTO.getId().isEmpty() &&
                    !trnHeaderTransportationDTO.getId().isBlank()) {
                newIdsList.add(Long.parseLong(trnHeaderTransportationDTO.getId()));
            }
        }
        if ( !newIdsList.isEmpty()) {
            Iterator<TrnHeaderTransportation> iterator = trnHeaderTransportationListToBeDeleted.iterator();
            while (iterator.hasNext()) {
                TrnHeaderTransportation trnHeaderTransportation = iterator.next();
                Long id = trnHeaderTransportation.getId();
                // Check if the id exists in firstIds set
                if (newIdsList.contains(id)) {
                    iterator.remove(); // Remove the element from secondList
                }
            }
            trnHeaderTransportationService.deleteAllInIterable(trnHeaderTransportationListToBeDeleted);
        }else {trnHeaderTransportationService.deleteAllInIterable(trnHeaderTransportationListToBeDeleted);}
    }

}
