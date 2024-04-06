package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.CityMapper;
import com.newage.wms.application.dto.responsedto.CityResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.CityMaster;
import com.newage.wms.service.CityMasterService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/city")
@CrossOrigin("*")
public class CityMasterController {

    @Autowired
    private CityMasterService cityMasterService;

    @Autowired
    private CityMapper cityMasterMapper;

    /*
     * Method to fetch all CityMaster with filter
     */
    @ApiOperation(value = "Fetch all City", notes = " Api is used to fetch all City ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllCity(@QuerydslPredicate(root = CityMaster.class) Predicate predicate){
        log.info("ENTRY - Fetch all City with filter");
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<CityMaster> cityMasterIterable = cityMasterService.getAllCities(predicate,pageable);
        Iterable<CityResponseDTO> cityMasterDtoIterable = cityMasterMapper.convertCityMasterIterableToCityMasterDtoIterable(cityMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,cityMasterDtoIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}