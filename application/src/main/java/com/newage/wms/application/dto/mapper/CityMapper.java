package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.CityResponseDTO;
import com.newage.wms.entity.CityMaster;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Log4j2
public final class CityMapper implements GlobalDTOMapper<CityMaster, CityResponseDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CityResponseDTO convertEntityToResponseDTO(CityMaster cityMaster) {
        log.info("ENTRY-EXIT - Entity to Response mapper");
        CityResponseDTO cityResponseDTO = modelMapper.map(cityMaster, CityResponseDTO.class);
        cityResponseDTO.setId(cityResponseDTO.getId());
        if (cityMaster.getCountry() != null) {
            cityResponseDTO.setCountry(new CityResponseDTO.CountryMasterDTO(cityMaster.getCountry().getId(), cityMaster.getCountry().getCode(), cityMaster.getCountry().getName()));
        }
        if (cityMaster.getState() != null) {
            cityResponseDTO.setState(new CityResponseDTO.StateMasterDTO(cityMaster.getState().getId(), cityMaster.getState().getCode(), cityMaster.getState().getName()));
        }
        return cityResponseDTO;
    }

    /*
     *Method to Convert Entity Page To Response Page
     * Return City Master Page
     */
    @Override
    public Page<CityResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<CityMaster> cityMasters) {
        log.info("ENTRY-EXIT - Entity Page to Response Page mapper");
        List<CityResponseDTO> dtos = new ArrayList<>();
        cityMasters.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(dtos, pageRequest, cityMasters.getTotalElements());
    }


    /*
     * Method to convert CityMasterIterable to CityMasterDtoIterable
     * @Return Iterable<CityMasterDto>
     */
    public Iterable<CityResponseDTO> convertCityMasterIterableToCityMasterDtoIterable(Iterable<CityMaster> cityMasterIterable){
        log.info("ENTRY - CityMasterIterable to CityMasterDtoIterable mapper");
        log.info("EXIT");
        return StreamSupport.stream(cityMasterIterable.spliterator(), false)
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }

}
