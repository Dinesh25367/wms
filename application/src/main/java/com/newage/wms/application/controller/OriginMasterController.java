package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.OriginMapper;
import com.newage.wms.application.dto.responsedto.OriginResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.OriginMaster;
import com.newage.wms.entity.QOriginMaster;
import com.newage.wms.entity.QQcStatusMaster;
import com.newage.wms.service.OriginMasterService;
import com.querydsl.core.types.ExpressionUtils;
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
import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/masterdata/origin")
@CrossOrigin("*")
public class OriginMasterController {

    @Autowired
    private OriginMasterService originMasterService;

    @Autowired
    private OriginMapper originMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getOriginById(@PathVariable("id") Long id) {

        OriginResponseDTO result = originMapper.convertEntityToResponseDTO(originMasterService.getOriginById(id));
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }
    
    /*
     * Method to fetch all OriginMaster for autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @QuerydslPredicate(root= OriginMaster.class) Predicate predicate){
        log.info("ENTRY - Fetch all OriginMaster for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QOriginMaster.originMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QOriginMaster.originMaster.name.containsIgnoreCase(query)
                    .or(QOriginMaster.originMaster.code.containsIgnoreCase(query)));
        }
        predicates.add(predicate);
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<OriginMaster> originMasterIterable = originMasterService.getAllAutoComplete(predicateAll,pageable);
        Iterable<TrnResponseDTO.TrnHeaderFreightShippingDTO.OriginDTO> originDTOIterable = originMapper.convertEntityIterableToAutoCompleteResponseIterable(originMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,originDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
