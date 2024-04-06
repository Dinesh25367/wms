package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.CountryResponseDTO;
import com.newage.wms.entity.CountryMaster;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Log4j2
public final class CountryMapper implements GlobalDTOMapper<CountryMaster, CountryResponseDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CountryResponseDTO convertEntityToResponseDTO(CountryMaster countryMaster) {
        log.info("ENTRY-EXIT - Entity to Response mapper");
        CountryResponseDTO countryResponseDTO = modelMapper.map(countryMaster, CountryResponseDTO.class);
        countryResponseDTO.setId(countryResponseDTO.getId());
        if (countryMaster.getCurrency() != null) {
            countryResponseDTO.setCurrency(new CountryResponseDTO.CurrencyMasterDTO(countryMaster.getCurrency().getId(), countryMaster.getCurrency().getCode(), countryMaster.getCurrency().getName()));
        }
        if (countryMaster.getRegion() != null) {
            countryResponseDTO.setRegion(new CountryResponseDTO.RegionMasterDTO(countryMaster.getRegion().getId(), countryMaster.getRegion().getCode(), countryMaster.getRegion().getName()));
        }
        return countryResponseDTO;
    }

    /*
     *Method to Convert Entity Page to Response Page
     * Return Page<CountryResponseDTO>
     */
    @Override
    public Page<CountryResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<CountryMaster> countryMasters) {
        log.info("ENTRY-EXIT - Entity Page to Response Page mapper");
        return null;
    }

    /*
     *Method to convert CountryList to CountryResponseDTO List All Country List
     * Return List<CountryResponseDTO>
     */
    public List<CountryResponseDTO> getAllCountryList(List<CountryMaster> countryMasters){
        log.info("ENTRY-EXIT - CountryList to CountryResponseDTO List mapper");
        List<CountryResponseDTO> dtos = new ArrayList<>();
        countryMasters.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        return dtos;
    }

    /*
     * Method to convert CountryMasterIterable to CountryMasterDtoIterable
     * @Return Iterable<CountryResponseDTO>
     */
    public Iterable<CountryResponseDTO> convertCountryMasterIterableToCountryResponseDTOIterable(Iterable<CountryMaster> countryMasterIterable){
        log.info("ENTRY-EXIT - CountryMasterIterable to CountryMasterDtoIterable mapper");
        return StreamSupport.stream(countryMasterIterable.spliterator(), false)
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }

}

