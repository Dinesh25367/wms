package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.VesselMasterRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.application.dto.responsedto.VesselMasterResponseDTO;
import com.newage.wms.entity.CountryMaster;
import com.newage.wms.entity.VesselMaster;
import com.newage.wms.service.CountryMasterService;
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
public class VesselMasterMapper{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CountryMasterService countryMasterService;

    public VesselMasterResponseDTO convertEntityToResponseDTO(VesselMaster vesselMaster) {

        VesselMasterResponseDTO vesselMasterResponseDTO = modelMapper.map(vesselMaster, VesselMasterResponseDTO.class);
        vesselMasterResponseDTO.setId(vesselMasterResponseDTO.getId());

        if(vesselMaster.getVesselCountry() != null) {
            vesselMasterResponseDTO.setVesselCountry(new VesselMasterResponseDTO.VesselCountryDTO(vesselMaster.getVesselCountry().getId(),vesselMaster.getVesselCountry().getCode(),vesselMaster.getVesselCountry().getName(),vesselMaster.getVesselCountry().getFlag()));
        }
        return vesselMasterResponseDTO;
    }

    public void convertUpdateRequestToEntity(VesselMasterRequestDTO vesselMasterRequestDTO, VesselMaster vesselMaster) {

        if (vesselMasterRequestDTO.getVesselCountry() != null) {
            CountryMaster countryMaster = countryMasterService.getCountryById(vesselMasterRequestDTO.getVesselCountry());
            vesselMaster.setVesselCountry(countryMaster);
        }
        modelMapper.map(vesselMasterRequestDTO, vesselMaster);

    }

    public Page<VesselMasterResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<VesselMaster> vesselMasters) {

        List<VesselMasterResponseDTO> vesselMasterResponseDTOList = new ArrayList<>();
        vesselMasters.forEach(e -> vesselMasterResponseDTOList.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(vesselMasterResponseDTOList, pageRequest, vesselMasters.getTotalElements());
    }

    /*
     * Method to convert VesselMaster Iterable to Vessel AutoCompleteDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnHeaderShippingDetailsDTO.VesselDTO>
     */
    public Iterable<TrnResponseDTO.TrnHeaderFreightShippingDTO.VesselDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<VesselMaster> vesselMasterIterable){
        log.info("ENTRY-EXIT - VesselMaster Iterable to Vessel AutoCompleteDTO Iterable mapper");
        return StreamSupport.stream(vesselMasterIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert Vessel Master to Vessel AutoCompleteDTO
     * @Return TrnResponseDTO.TrnHeaderShippingDetailsDTO.VesselDTO
     */
    private TrnResponseDTO.TrnHeaderFreightShippingDTO.VesselDTO convertEntityToAutoCompleteDTO(VesselMaster vesselMaster) {
        log.info("ENTRY-EXIT - Vessel Master to Vessel AutoCompleteDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(vesselMaster, TrnResponseDTO.TrnHeaderFreightShippingDTO.VesselDTO.class);
    }

}
