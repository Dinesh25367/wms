package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.CompanyMapper;
import com.newage.wms.application.dto.responsedto.CompanyResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/masterdata/companies")
@CrossOrigin("*")
public class CompanyMasterController {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private BranchMasterService branchMasterService;

    /*
     *Method to get Company by Id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getCompanyById(@PathVariable("id") Long id) {
        log.info("ENTRY - get Company by Id");
        CompanyResponseDTO result = companyMapper.convertEntityToResponseDTO(companyMasterService.getCompanyById(id));
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

}
