package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.QcStatusMapper;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.QCustomerMaster;
import com.newage.wms.entity.QQcStatusMaster;
import com.newage.wms.entity.QcStatusMaster;
import com.newage.wms.service.QcStatusMasterService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@RestController
@RequestMapping(value = "/qcStatus")
@CrossOrigin("*")
public class QcStatusMasterController {

    @Autowired
    private QcStatusMasterService qcStatusMasterService;

    @Autowired
    private QcStatusMapper qcStatusMapper;


    /*
     * Method to fetch all MovementTypeMaster for autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @RequestParam(name = "branchId",required = false) Long branchId,
                                                            @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all MovementTypeMaster for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QQcStatusMaster.qcStatusMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QQcStatusMaster.qcStatusMaster.name.containsIgnoreCase(query)
                    .or(QQcStatusMaster.qcStatusMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QQcStatusMaster.qcStatusMaster.branchMaster.id.eq(branchId));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<QcStatusMaster> qcStatusMasterIterable = qcStatusMasterService.getAllAutoComplete(predicateAll,pageable);
        Iterable<TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO> qcStatusDTOIterable = qcStatusMapper.convertEntityIterableToAutoCompleteResponseIterable(qcStatusMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,qcStatusDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all MovementTypeMaster for autocomplete
     */
    @GetMapping("/asn/bulkSave")
    public ResponseEntity<ResponseDTO> fetchAllAsnBulkSave(){
        log.info("ENTRY - Fetch all MovementTypeMaster for autocomplete");
        BooleanBuilder combinedPredicate = new BooleanBuilder();
        BooleanExpression nameOrCodeExpression = QQcStatusMaster.qcStatusMaster.id.isNotNull();
        combinedPredicate.and(nameOrCodeExpression);
        Pageable pageable = Pageable.unpaged();
        Iterable<QcStatusMaster> qcStatusMasterIterable = qcStatusMasterService.getAllAutoComplete(combinedPredicate,pageable);
        Iterable<TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO> qcStatusDTOIterable = qcStatusMapper.convertEntityIterableToAutoCompleteResponseIterable(qcStatusMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,qcStatusDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}