package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.GroupCompanyMapper;
import com.newage.wms.application.dto.responsedto.GroupCompanyResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.service.GroupCompanyMasterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/masterdata/groupcompanies")
@CrossOrigin("*")
public class GroupCompanyMasterController {

    @Autowired
    private GroupCompanyMapper groupCompanyMapper;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    /*
     *Method to get Group Company by Id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getGroupCompanyById(@PathVariable("id") Long id) {
        log.info("ENTRY - get Group Company by Id");
        GroupCompanyResponseDTO result = groupCompanyMapper.convertEntityToResponseDTO(groupCompanyMasterService.getGroupCompanyById(id));
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

}
