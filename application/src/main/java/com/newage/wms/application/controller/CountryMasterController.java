package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.CountryMapper;
import com.newage.wms.application.dto.responsedto.CountryResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.CountryMaster;
import com.newage.wms.service.CountryMasterService;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/masterdata/countries")
@CrossOrigin("*")
public class CountryMasterController {

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CountryMasterService countryMasterService;

    /*
     *Method to get Country by Id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getCountryById(@PathVariable("id") Long id) {
        log.info("ENTRY - Fetch Country by Id");
        CountryResponseDTO result = countryMapper.convertEntityToResponseDTO(countryMasterService.getCountryById(id));
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

    /*
     * Method to fetch all CountryMaster with filter
     */
    @GetMapping("autoSearch")
    public ResponseEntity<ResponseDTO> fetchAllCountryAutoSearch(@QuerydslPredicate(root = CountryMaster.class) Predicate predicate){
        log.info("ENTRY - Fetch all Country with filter");
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<CountryMaster> countryMasterIterable = countryMasterService.getAllCountriesAutoSearch(predicate,pageable);
        Iterable<CountryResponseDTO> countryResponseDTOIterable = countryMapper.convertCountryMasterIterableToCountryResponseDTOIterable(countryMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,countryResponseDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}