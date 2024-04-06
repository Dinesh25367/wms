package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.TosRequestDTO;
import com.newage.wms.application.dto.responsedto.TosResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.TosMaster;
import com.newage.wms.service.TosMasterService;
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
public final class TosMapper{

    @Autowired
    TosMasterService tosMasterService;

    @Autowired
    private ModelMapper modelMapper;

    public TosResponseDTO convertEntityToResponseDTO(TosMaster tosMaster) {

        TosResponseDTO tosResponseDTO = modelMapper.map(tosMaster, TosResponseDTO.class);
        tosResponseDTO.setId(tosMaster.getId());

        return tosResponseDTO;
    }

    public void convertUpdateRequestToEntity(TosRequestDTO tosRequestDTO, TosMaster tosMaster) {

        modelMapper.map(tosRequestDTO, tosMaster);
    }

    public Page<TosResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<TosMaster> tosMasters) {

        List<TosResponseDTO> dtos = new ArrayList<>();
        tosMasters.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(dtos, pageRequest, tosMasters.getTotalElements());
    }

    /*
     * Method to convert TosMaster Iterable to Tos AutoCompleteDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnHeaderShippingDetailsDTO.TosDTO>
     */
    public Iterable<TrnResponseDTO.TrnHeaderFreightShippingDTO.TosDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<TosMaster> tosMasterIterable){
        log.info("ENTRY-EXIT - TosMaster Iterable to Tos AutoCompleteDTO Iterable mapper");
        return StreamSupport.stream(tosMasterIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert TosMaster to Tos AutoCompleteDTO
     * @Return Iterable<TrnResponseDTO.TrnHeaderShippingDetailsDTO.TosDTO>
     */
    private TrnResponseDTO.TrnHeaderFreightShippingDTO.TosDTO convertEntityToAutoCompleteDTO(TosMaster tosMaster) {
        log.info("ENTRY-EXIT - TosMaster Iterable to Tos AutoCompleteDTO Iterable mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(tosMaster, TrnResponseDTO.TrnHeaderFreightShippingDTO.TosDTO.class);
    }

}
