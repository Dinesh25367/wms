package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.entity.HsCodeMaster;
import com.newage.wms.service.HsCodeMasterService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class HsCodeMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HsCodeMasterService hsCodeMasterService;

    /*
     * Method to convert HsCodeMasterIterable to SkuRequestDTO.HsCodeDTO Iterable
     * @Return Iterable<SkuRequestDTO.HsCodeDTO>
     */
    public Iterable<SkuRequestDTO.HsCodeDTO> convertEntityIterableToHsCodeAutoCompleteDtoIterable(Iterable<HsCodeMaster> hsCodeMasterIterable){
        log.info("ENTRY - HsCodeMasterIterable to SkuRequestDTO.HsCodeDTO mapper");
        return StreamSupport.stream(hsCodeMasterIterable.spliterator(), false)
                .map(this::convertEntitytoAutoCompleteDto)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert HsCode entity to SkuRequestDTO.HsCodeDTO
     * @Return SkuRequestDTO.HsCodeDTO
     */
    public SkuRequestDTO.HsCodeDTO convertEntitytoAutoCompleteDto(HsCodeMaster hsCodeMaster){
        log.info("ENTRY - Customer entity to SkuRequestDTO.HsCodeDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        SkuRequestDTO.HsCodeDTO hsCodeDTO = modelMapper.map(hsCodeMaster, SkuRequestDTO.HsCodeDTO.class);
        log.info("EXIT");
        return hsCodeDTO;
    }

}
