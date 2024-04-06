package com.newage.wms.application.controller;

import com.newage.wms.application.dto.responsedto.CarrierMasterResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.CarrierMaster;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.newage.wms.application.dto.mapper.CarrierMapper;
import com.newage.wms.service.CarrierMasterService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/masterdata/carrier")
@CrossOrigin("*")
public class CarrierMasterController {

    @Autowired
    private CarrierMapper carrierMapper;

    @Autowired
    private CarrierMasterService carrierMasterService;

    /*
     *Method to get all Carrier
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getAllCarrierMaster(@QuerydslPredicate(root = CarrierMaster.class) Predicate predicate,
                                                           @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 20)
                                                           Pageable pageRequest) {
        log.info("ENTRY - get all Carrier");
        Page<CarrierMaster> carrierMasterspPage = carrierMasterService.getAllCarrier(predicate, pageRequest);
        Page<CarrierMasterResponseDTO> result = carrierMapper.convertEntityPageToResponsePage(pageRequest, carrierMasterspPage);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

    /*
     *Method to get Carrier by Id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getCarrierMasterById(@PathVariable("id") Long id) {
        log.info("ENTRY - get Carrier by Id");
        CarrierMasterResponseDTO result = carrierMapper.convertEntityToResponseDTO(carrierMasterService.getCarrierById(id));
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

    /*
     * Method to get filtered Carrier Master
     */
    @GetMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getFilteredAllCarrierMaster(@QuerydslPredicate(root = CarrierMaster.class) Predicate predicate,
                                                                   @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 20)
                                                                   @RequestParam(value = "carrierName", required = false) String carrierName,
                                                                   @RequestParam(value = "carrierCode", required = false) String carrierCode,
                                                                   Pageable pageRequest) {
        log.info("ENTRY - get filtered Carrier Master");
        Page<CarrierMaster> carrierMastersPage = carrierMasterService.getFilteredAllCarrier(predicate, pageRequest, carrierName, carrierCode);
        Page<CarrierMasterResponseDTO> result = carrierMapper.convertEntityPageToResponsePage(pageRequest, carrierMastersPage);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);

    }

    /*
     * Method to find all Carrier for AutoComplete
     */
    @GetMapping(value = "/autoComplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getCarrierAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                              @RequestParam(name = "transportMode",required = false) String transportMode,
                                                              @RequestParam(name = "status",required = false) String status) {
        log.info("ENTRY - find all Carrier for AutoComplete");
        Iterable<CarrierMaster> carrierMasterIterable = carrierMasterService.getAllAutoComplete(query,transportMode,status);
        Iterable<TrnResponseDTO.TrnHeaderFreightShippingDTO.CarrierDTO> carrierDTOIterable = carrierMapper.convertEntityIterableToAutoCompleteResponseIterable(carrierMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,carrierDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
