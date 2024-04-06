package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.BranchMapper;
import com.newage.wms.application.dto.mapper.CountryMapper;
import com.newage.wms.application.dto.responsedto.CountryResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.CountryMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CountryMasterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/masterdata/branch")
@CrossOrigin("*")
public class BranchMasterController {

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CountryMasterService countryMasterService;

    @Autowired
    private CountryMapper countryMasterMapper;

    @Autowired
    private final BranchMapper branchMapper;

    public BranchMasterController(BranchMapper branchMapper) {
        this.branchMapper = branchMapper;
    }

    @Autowired
    public BranchMasterController(BranchMasterService branchMasterService, BranchMapper branchMapper) {
        this.branchMasterService = branchMasterService;
        this.branchMapper = branchMapper;
    }

    /*
     * Method to fetch Country by BranchId
     */
    @ApiOperation(value = "Fetch Country by BranchId", notes = " Api is used to fetch Country by BranchId ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/getCountry/{id}")
    public ResponseEntity<ResponseDTO> fetchCountryByBranchMasterId(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Country by BranchId");
        Long countryId =  branchMasterService.getCountryIdByBranchMasterId(id);
        CountryMaster countryMaster = countryMasterService.getCountryById(countryId);
        CountryResponseDTO countryMasterDto = countryMasterMapper.convertEntityToResponseDTO(countryMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,countryMasterDto,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
