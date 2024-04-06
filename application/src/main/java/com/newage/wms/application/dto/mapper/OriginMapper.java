package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.OriginResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.OriginMaster;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class OriginMapper{

    @Autowired
    private ModelMapper modelMapper;

    public OriginResponseDTO convertEntityToResponseDTO(OriginMaster originMaster) {
        OriginResponseDTO originResponseDTO = modelMapper.map(originMaster, OriginResponseDTO.class);
        originResponseDTO.setId(originResponseDTO.getId());
        if(originMaster.getCountry() !=null){
            originResponseDTO.setCountry(new OriginResponseDTO.CountryDTO(originMaster.getCountry().getId(), originMaster.getCountry().getCode(),originMaster.getCountry().getName()));
        }
        return originResponseDTO;
    }

    public Page<OriginResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<OriginMaster> originMasterPage) {
        List<OriginResponseDTO> dtos = new ArrayList<>();
        originMasterPage.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(dtos, pageRequest, originMasterPage.getTotalElements());
    }

    /*
     * Method to convert OriginMasterIterable to Origin AutoCompleteDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnHeaderShippingDetailsDTO.OriginDTO>
     */
    public Iterable<TrnResponseDTO.TrnHeaderFreightShippingDTO.OriginDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<OriginMaster> originMasterIterable){
        log.info("ENTRY-EXIT - OriginMasterIterable to Origin AutoCompleteDTO Iterable mapper");
        return StreamSupport.stream(originMasterIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert OriginMaster to Origin AutoCompleteDTO
     * @Return TrnResponseDTO.TrnHeaderShippingDetailsDTO.OriginDTO
     */
    private TrnResponseDTO.TrnHeaderFreightShippingDTO.OriginDTO convertEntityToAutoCompleteDTO(OriginMaster originMaster) {
        log.info("ENTRY-EXIT - OriginMaster to Origin AutoCompleteDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(originMaster, TrnResponseDTO.TrnHeaderFreightShippingDTO.OriginDTO.class);
    }

}
