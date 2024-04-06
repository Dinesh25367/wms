package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.QcStatusMaster;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class QcStatusMapper {

    @Autowired
    private ModelMapper modelMapper;

    /*
     * Method to convert QcStatusMaster Iterable to QcStatusMaster AutoCompleteDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO>
     */
    public Iterable<TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<QcStatusMaster> qcStatusMasterIterable){
        log.info("ENTRY-EXIT - QcStatusMaster Iterable to QcStatusMaster AutoCompleteDTO Iterable mapper");
        return StreamSupport.stream(qcStatusMasterIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert QcStatusMaster to QcStatusMaster AutoCompleteDTO
     * @Return TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO
     */
    private TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO convertEntityToAutoCompleteDTO(QcStatusMaster qcStatusMaster) {
        log.info("ENTRY-EXIT - MovementTypeMaster Iterable to MovementType AutoCompleteDTO Iterable mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(qcStatusMaster, TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO.class);
    }
}
