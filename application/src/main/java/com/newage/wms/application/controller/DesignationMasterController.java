package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.DesignationMapper;
import com.newage.wms.application.dto.responsedto.DesignationResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.DesignationMaster;
import com.newage.wms.service.DesignationMasterService;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/masterdata/designation")
@CrossOrigin("*")
public class DesignationMasterController {

    @Autowired
    private DesignationMasterService designationMasterService;

    @Autowired
    private DesignationMapper designationMapper;

    /*
     * Method to fetch all Designation
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getAllDesignation(@QuerydslPredicate(root = DesignationMaster.class) Predicate predicate,
                                                         @PageableDefault(sort = {"name"}, direction = Sort.Direction.ASC, size = 20) Pageable pageRequest) {
        log.info("ENTRY - Get all Designation");
        Page<DesignationMaster> designationMasterPage = designationMasterService.getAllDesignation(predicate, pageRequest);
        Page<DesignationResponseDTO> result = designationMapper.convertEntityPageToResponsePage(pageRequest, designationMasterPage);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

    /*
     * Method to fetch Designation by Id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getDesignationById(@PathVariable("id") Long id) {
        log.info("ENTRY - Get Designation by Id");
        DesignationResponseDTO result = designationMapper.convertEntityToResponseDTO(designationMasterService.getDesignationById(id));
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

    /*
     * Method to fetch all CountryMaster with filter
     */
    @GetMapping("autoSearch")
    public ResponseEntity<ResponseDTO> fetchAllDesignationAutoSearch(@QuerydslPredicate(root = DesignationMaster.class) Predicate predicate){
        log.info("ENTRY - Fetch all Country with filter");
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<DesignationMaster> designationMasterIterable = designationMasterService.getAllDesignationAutoSearch(predicate,pageable);
        Iterable<DesignationResponseDTO> designationResponseDTOIterable = designationMapper.convertDesignationMasterIterableToDesignationResponseDTOIterable(designationMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,designationResponseDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
