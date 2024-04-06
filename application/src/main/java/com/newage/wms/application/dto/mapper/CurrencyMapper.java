package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.CurrencyResponseDTO;
import com.newage.wms.application.dto.responsedto.SkuResponseDTO;
import com.newage.wms.entity.CurrencyMaster;
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
public class CurrencyMapper implements GlobalDTOMapper<CurrencyMaster, CurrencyResponseDTO>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CurrencyResponseDTO convertEntityToResponseDTO(CurrencyMaster currencyMaster) {

        CurrencyResponseDTO currencyDTO = modelMapper.map(currencyMaster, CurrencyResponseDTO.class);
        currencyDTO.setCountry(new CurrencyResponseDTO.CountryMasterDTO(currencyMaster.getCountry().getId(), currencyMaster.getCountry().getCode(), currencyMaster.getCountry().getName()));
        return currencyDTO;
    }

    public Iterable<SkuResponseDTO.CurrencyDTO> convertCurrencyIterableToCurrencyDTOIterable(Iterable<CurrencyMaster> currencyMasterIterable) {
        return StreamSupport.stream(currencyMasterIterable.spliterator(), false)
                .map(this::convertEntityToCurrencyAutoCompleteDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Page<CurrencyResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<CurrencyMaster> currencyMasters) {

        List<CurrencyResponseDTO> dtos = new ArrayList<>();
        currencyMasters.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(dtos, pageRequest, currencyMasters.getTotalElements());
    }

    private SkuResponseDTO.CurrencyDTO convertEntityToCurrencyAutoCompleteDTO(CurrencyMaster currencyMaster) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Ensure strict matching
        return modelMapper.map(currencyMaster, SkuResponseDTO.CurrencyDTO.class);
    }

}
